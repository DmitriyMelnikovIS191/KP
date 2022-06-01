package com.example.kpmelnikov;

public class MyVisitor extends CBaseVisitor {
    @Override public Object visitPrimaryExpression(CParser.PrimaryExpressionContext ctx) {
        System.out.println(ctx);
        return visitChildren(ctx); }

    @Override public Object visitGenericSelection(CParser.GenericSelectionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitGenericAssocList(CParser.GenericAssocListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitGenericAssociation(CParser.GenericAssociationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitPostfixExpression(CParser.PostfixExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitArgumentExpressionList(CParser.ArgumentExpressionListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitUnaryExpression(CParser.UnaryExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitUnaryOperator(CParser.UnaryOperatorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitCastExpression(CParser.CastExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitMultiplicativeExpression(CParser.MultiplicativeExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAdditiveExpression(CParser.AdditiveExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitShiftExpression(CParser.ShiftExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitRelationalExpression(CParser.RelationalExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitEqualityExpression(CParser.EqualityExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAndExpression(CParser.AndExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitExclusiveOrExpression(CParser.ExclusiveOrExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitInclusiveOrExpression(CParser.InclusiveOrExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitLogicalAndExpression(CParser.LogicalAndExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitLogicalOrExpression(CParser.LogicalOrExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitConditionalExpression(CParser.ConditionalExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAssignmentExpression(CParser.AssignmentExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAssignmentOperator(CParser.AssignmentOperatorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitExpression(CParser.ExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitConstantExpression(CParser.ConstantExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDeclaration(CParser.DeclarationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDeclarationSpecifiers2(CParser.DeclarationSpecifiers2Context ctx) { return visitChildren(ctx); }

    @Override public Object visitDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitInitDeclaratorList(CParser.InitDeclaratorListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitInitDeclarator(CParser.InitDeclaratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStorageClassSpecifier(CParser.StorageClassSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTypeSpecifier(CParser.TypeSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStructOrUnion(CParser.StructOrUnionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStructDeclarationList(CParser.StructDeclarationListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStructDeclaration(CParser.StructDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStructDeclaratorList(CParser.StructDeclaratorListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStructDeclarator(CParser.StructDeclaratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitEnumSpecifier(CParser.EnumSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitEnumeratorList(CParser.EnumeratorListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitEnumerator(CParser.EnumeratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitEnumerationConstant(CParser.EnumerationConstantContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAtomicTypeSpecifier(CParser.AtomicTypeSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTypeQualifier(CParser.TypeQualifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitFunctionSpecifier(CParser.FunctionSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAlignmentSpecifier(CParser.AlignmentSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDeclarator(CParser.DeclaratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDirectDeclarator(CParser.DirectDeclaratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitVcSpecificModifer(CParser.VcSpecificModiferContext ctx) { return visitChildren(ctx); }

    @Override public Object visitGccDeclaratorExtension(CParser.GccDeclaratorExtensionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitGccAttributeSpecifier(CParser.GccAttributeSpecifierContext ctx) { return visitChildren(ctx); }

    @Override public Object visitGccAttributeList(CParser.GccAttributeListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitGccAttribute(CParser.GccAttributeContext ctx) { return visitChildren(ctx); }

    @Override public Object visitNestedParenthesesBlock(CParser.NestedParenthesesBlockContext ctx) { return visitChildren(ctx); }

    @Override public Object visitPointer(CParser.PointerContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTypeQualifierList(CParser.TypeQualifierListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitParameterTypeList(CParser.ParameterTypeListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitParameterList(CParser.ParameterListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitParameterDeclaration(CParser.ParameterDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitIdentifierList(CParser.IdentifierListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTypeName(CParser.TypeNameContext ctx) { return visitChildren(ctx); }

    @Override public Object visitAbstractDeclarator(CParser.AbstractDeclaratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDirectAbstractDeclarator(CParser.DirectAbstractDeclaratorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTypedefName(CParser.TypedefNameContext ctx) { return visitChildren(ctx); }

    @Override public Object visitInitializer(CParser.InitializerContext ctx) { return visitChildren(ctx); }

    @Override public Object visitInitializerList(CParser.InitializerListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDesignation(CParser.DesignationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDesignatorList(CParser.DesignatorListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDesignator(CParser.DesignatorContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStaticAssertDeclaration(CParser.StaticAssertDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitStatement(CParser.StatementContext ctx) { return visitChildren(ctx); }

    @Override public Object visitLabeledStatement(CParser.LabeledStatementContext ctx) { return visitChildren(ctx); }

    @Override public Object visitCompoundStatement(CParser.CompoundStatementContext ctx) { return visitChildren(ctx); }

    @Override public Object visitBlockItemList(CParser.BlockItemListContext ctx) { return visitChildren(ctx); }

    @Override public Object visitBlockItem(CParser.BlockItemContext ctx) { return visitChildren(ctx); }

    @Override public Object visitExpressionStatement(CParser.ExpressionStatementContext ctx) { return visitChildren(ctx); }

    @Override public Object visitSelectionStatement(CParser.SelectionStatementContext ctx) {
        System.out.println(1);
        return visitChildren(ctx);
    }

    @Override public Object visitIterationStatement(CParser.IterationStatementContext ctx) {
        System.out.println(2);
        return visitChildren(ctx); }

    @Override public Object visitForCondition(CParser.ForConditionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitForDeclaration(CParser.ForDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitForExpression(CParser.ForExpressionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitJumpStatement(CParser.JumpStatementContext ctx) { return visitChildren(ctx); }

    @Override public Object visitCompilationUnit(CParser.CompilationUnitContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTranslationUnit(CParser.TranslationUnitContext ctx) { return visitChildren(ctx); }

    @Override public Object visitExternalDeclaration(CParser.ExternalDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public Object visitFunctionDefinition(CParser.FunctionDefinitionContext ctx) { return visitChildren(ctx); }

    @Override public Object visitDeclarationList(CParser.DeclarationListContext ctx) { return visitChildren(ctx); }

}
