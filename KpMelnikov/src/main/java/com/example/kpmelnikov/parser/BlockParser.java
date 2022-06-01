package com.example.kpmelnikov.parser;

import com.example.kpmelnikov.CParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

class ParseContext {
    /**
     * Начальный узел
     */
    public BlockNode rootNode;
    /**
     * Текущие узел
     */
    public BlockNode currentNode;
    /**
     * Текущий конечный узел
     */
    public BlockNode endNode;

    public ParseContext() {
        rootNode = new BlockNode(BlockType.Root);
        currentNode = rootNode;
        endNode = new BlockNode(BlockType.End);
        endNode.tokens.add("END");
    }
}

public class BlockParser {

    /**
     * Ссылка на ANTLR дерево, которое анализируется
     */
    private ParseTree _tree;

    /**
     * @param tree Входные данные для анализа
     * Создает BlockParser и получает входные данные для анализа
     */
    public BlockParser(ParseTree tree)  {
        _tree = tree;
    }

    /**
     * Провести анализ
     * @return Блок
     */
    public BlockNode parse() {
        var context = new ParseContext();

        recursiveParse(_tree, context);

        return context.rootNode;
    }

    /**
     * @param tree Дерево анализа
     * @param context Контекс
     * Рекурсивный проход по файлу
     */
    private void recursiveParse(ParseTree tree, ParseContext context)  {
        var type = tree.getClass();

        if (type == CParser.FunctionDefinitionContext.class) {
            ParseFunctionDefinition((CParser.FunctionDefinitionContext)tree, context);
            return;
        }
        else if (type == CParser.SelectionStatementContext.class) {
            parseSelectionStatement((CParser.SelectionStatementContext)tree, context);
            return;
        }
        else if (type == CParser.IterationStatementContext.class) {
            parseForCondition((CParser.IterationStatementContext)tree, context);
            return;
        }
        else if (type == CParser.ExpressionStatementContext.class) {
            parseExpessionStatementContext((CParser.ExpressionStatementContext)tree, context);
            return;
        }
        else if (type == CParser.DeclarationContext.class) {
            parseDeclarationContext((CParser.DeclarationContext)tree, context);
            return;
        }
        else if (type == CParser.JumpStatementContext.class) {
            parseReturnStatement((CParser.JumpStatementContext)tree, context);
            return;
        }
        else if (type == TerminalNodeImpl.class) {
            parseEndOfFile((TerminalNodeImpl)tree, context);
        }

        for (int i = 0; i < tree.getChildCount(); i++)
            recursiveParse(tree.getChild(i), context);
    }

    /**
     * @param tree Дерево
     * @param context Контекст
     * Анализ  дерева на конец файла
     */
    private void parseEndOfFile(TerminalNodeImpl tree, ParseContext context) {
        var text = tree.getText();

        if (text == "<EOF>") {
            if (context.currentNode.blockType == BlockType.None) {
                for (int i = 0; i < context.currentNode.parents.size(); i++) {
                    var parent = context.currentNode.parents.get(i);
                    parent.removeChild(context.currentNode);
                    parent.addChild(context.endNode);
                }
                context.currentNode = context.endNode;
            }
            else if (context.currentNode.blockType != BlockType.End){
                context.endNode.addParent(context.currentNode);
                context.currentNode = context.endNode;
            }

        }
    }

    /**
     * @param tree Дерево (контекст правил)
     * @param context Контекст
     * Анализирует наличие "return"
     */
    private void parseReturnStatement(CParser.JumpStatementContext tree, ParseContext context) {
        if (tree.Return() != null) {
            var expression = tree.expression();
            if (expression != null) {
                if (context.currentNode.blockType == BlockType.None)
                    context.currentNode.blockType = BlockType.Process;
                if (context.currentNode.blockType != BlockType.Process)
                    context.currentNode = new BlockNode(BlockType.Process, context.currentNode);
                context.currentNode.tokens.add("return " + getFullText(expression));
            }

            context.endNode.addParent(context.currentNode);

            context.currentNode = context.endNode;
        }
    }

    /**
     * @param tree Дерево
     * @param context Контекст
     * Анализирует участок кода,где объявляются данные
     */
    private void parseDeclarationContext(CParser.DeclarationContext tree, ParseContext context) {
        ParseGeneralExpression(tree, context);
    }

    /**
     * @param tree
     * @param context
     * Анализирует часть дерева где содержится выражение
     */
    private void parseExpessionStatementContext(CParser.ExpressionStatementContext tree, ParseContext context) {
        ParseGeneralExpression(tree, context);
    }

    /**
     * @param tree
     * @param context
     * Анализирует часть кода где цикл
     */
    private void parseForCondition(CParser.IterationStatementContext tree, ParseContext context) {
        var forToken = tree.For();

        if (forToken != null)
        {
            var forConditionContext = tree.forCondition();
            var forDeclaration = forConditionContext.forDeclaration();

            if (forDeclaration != null)
            {
                if (context.currentNode.blockType == BlockType.None)
                    ParseGeneralExpression(forDeclaration, context);
                else {
                    var forDeclarationNode = new BlockNode(BlockType.Process, context.currentNode);
                    context.currentNode = forDeclarationNode;
                    ParseGeneralExpression(forDeclaration, context);
                }
            }

            var forExpression = forConditionContext.forExpression(0);

            BlockNode forNode = null;

            if (context.currentNode.blockType == BlockType.None) {
                context.currentNode.blockType = BlockType.Loop;
                forNode = context.currentNode;
            }
            else
                forNode = new BlockNode(BlockType.Loop, context.currentNode);

            forNode.tokens.add(getFullText(forExpression));

            context.currentNode = forNode;

            recursiveParse(tree.statement(), context);

            var incrementerExpression = forConditionContext.forExpression(1);

            if (incrementerExpression != null) {
                BlockNode incrementerNode = new BlockNode(BlockType.Process);
                if (context.currentNode.blockType == BlockType.None) {
                    while(context.currentNode.parents.size() > 0) {
                        var parent =  context.currentNode.parents.get(0);
                        parent.removeChild(context.currentNode);
                        incrementerNode.addParent(parent);
                    }
                    context.currentNode = incrementerNode;
                }
                else
                    incrementerNode.addParent(context.currentNode);

                incrementerNode.tokens.add(getFullText(incrementerExpression));
                incrementerNode.addChild(forNode);
            }
            else {
                if (context.currentNode.blockType == BlockType.None) {
                    while(context.currentNode.parents.size() > 0) {
                        var parent =  context.currentNode.parents.get(0);
                        parent.removeChild(context.currentNode);
                        forNode.addParent(parent);
                    }
                    context.currentNode = forNode;
                }
                else
                    context.currentNode.addChild(forNode);
            }

            context.currentNode = forNode;
        }
    }

    /**
     * @param tree
     * @param context
     * Анализ на if-условия
     */
    private void parseSelectionStatement(CParser.SelectionStatementContext tree, ParseContext context) {

        var ifRule = tree.If();
        var node = context.currentNode;
        if (ifRule != null) {
            var ifNode = new BlockNode(BlockType.Condition, node);

            // HACK! In some situations we need knowledge about next node, so we create empty one, add is as child and change its type
            if (context.currentNode.blockType == BlockType.None) {
                ifNode.removeParent(node);
                ifNode = node;
                ifNode.blockType = BlockType.Condition;
            }

            ifNode.tokens.add(getFullText(tree.expression()) + " ?");
            context.currentNode = ifNode;

            recursiveParse(tree.statement(0), context);

            // Create empty node, 'true' and 'false' nodes will reference this node
            var newNode = context.currentNode.blockType != BlockType.None ? new BlockNode(BlockType.None) : context.currentNode;

            if (context.currentNode.blockType != BlockType.End && context.currentNode.blockType != BlockType.None)
                context.currentNode.addChild(newNode);

            BlockNode falseNode = null;
            if (tree.Else() != null) {
                context.currentNode = ifNode;
                recursiveParse(tree.statement(1), context);

                if (context.currentNode.blockType != BlockType.End && context.currentNode.blockType != BlockType.None)
                    context.currentNode.addChild(newNode);
                else if (context.currentNode.blockType == BlockType.None) {
                    while(context.currentNode.parents.size() > 0) {
                        var parent =  context.currentNode.parents.get(0);
                        parent.removeChild(context.currentNode);
                        newNode.addParent(parent);
                    }
                    context.currentNode = newNode;
                }
            }
            else
                ifNode.addChild(newNode);

            context.currentNode = newNode;
        }
    }

    /**
     * @param tree Дерево из правил объявления выражений и объялвения переменных
     * @param context
     * Анализирует код на наличие входных-выходных данных
     */
    private void ParseGeneralExpression(ParserRuleContext tree, ParseContext context) {
        var text = getFullText(tree);

        var isInputOutput = false;

        isInputOutput = text.contains("scanf") || text.contains("printf");

        // HACK! In some situations we need knowledge about next node, so we create empty one, add is as child and change its type
        if (context.currentNode.blockType == BlockType.None) {
            context.currentNode.blockType =  isInputOutput ? BlockType.InputOutput : BlockType.Process;
        }

        if (context.currentNode.blockType == BlockType.Process && isInputOutput)
            context.currentNode = new BlockNode(BlockType.InputOutput, context.currentNode);
        else if (context.currentNode.blockType == BlockType.InputOutput && !isInputOutput)
            context.currentNode = new BlockNode(BlockType.Process, context.currentNode);
        else if (context.currentNode.blockType != BlockType.Process && context.currentNode.blockType != BlockType.InputOutput)
            context.currentNode = new BlockNode(isInputOutput ? BlockType.InputOutput : BlockType.Process, context.currentNode);

        context.currentNode.tokens.add(text);
    }

    /**
     * @param tree
     * @param context
     * Анализ входных точек (функций)
     */
    private void ParseFunctionDefinition(CParser.FunctionDefinitionContext tree, ParseContext context) {
        var node = new BlockNode(BlockType.Start, context.rootNode);

        var declarationSpecifiers = tree.declarationSpecifiers();
        String text = "";
        if (declarationSpecifiers != null) {
            text = getFullText(declarationSpecifiers);
        }

        var declarator = tree.declarator();

        if (declarator != null) {
            text += " " + getFullText(declarator);
        }

        node.tokens.add(text);

        context.currentNode = node;
        context.endNode = new BlockNode(BlockType.End);
        context.endNode.tokens.add("END");
        recursiveParse(tree.compoundStatement().getRuleContext(), context);

        if (context.currentNode.blockType == BlockType.None) {
            context.currentNode.blockType = BlockType.End;
            context.currentNode.tokens.add("END");
        }
        else
            context.currentNode.addChild(context.endNode);
    }

    /**
     * @param context
     * Получение полного текста из дерева, для записи в блок
     * @return
     */
    private String getFullText(ParserRuleContext context) {
        if (context.start == null || context.stop == null || context.start.getStartIndex() < 0 || context.stop.getStopIndex() < 0)
            return context.getText();

        return context.start.getInputStream().getText(Interval.of(context.start.getStartIndex(), context.stop.getStopIndex()));
    }
}
