package com.example.kpmelnikov;

import com.example.kpmelnikov.nodeShapes.Arrow;
import com.example.kpmelnikov.nodeShapes.BlockNodeShape;
import com.example.kpmelnikov.nodeShapes.BlockShapeFactory;
import com.example.kpmelnikov.parser.BlockNode;
import com.example.kpmelnikov.parser.BlockParser;
import com.example.kpmelnikov.parser.BlockType;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;

public class HelloController {
    private ArrayList<BlockNode> visitedNodes = new ArrayList<BlockNode>();
    private Hashtable<BlockNode, BlockNodeShape> shapes = new Hashtable<>();
    private ArrayList<Arrow> arrows = new ArrayList<>();
    /**
     * Родитель для всех фигур
     */
    private Group root = new Group();
    /**
     * Фабрика для фигур
     */
    private BlockShapeFactory shapeFactory = new BlockShapeFactory(() -> drawArrows(shapes));
    /**
     * Словарь глубины, необходим для отрисовки дерева
     */
    private Hashtable<Integer, ArrayList<BlockNode>> depthDictionary = new Hashtable<>();
    private int depthCounter = -1;
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private Label welcomeText;

    /**
     * Для использования scroll с pane
     */
    @FXML
    private ScrollPane paneScroll;

    /**
     * Текстовая область, куда вписывается код
     */
    @FXML
    private TextArea textArea;

    /**
     * Панель для отрисовки блок-схемы
     */
    @FXML
    private Pane mainPane;

    /**
     * При нажатии на "Старт"
     */
    @FXML
    protected void onHelloButtonClick() {

        root.getChildren().clear();
        root.applyCss();
        root.layout();


        String source = textArea.getText();
        CharStream st = CharStreams.fromString(source);
        CLexer lexer = new CLexer(st);
        CommonTokenStream token = new CommonTokenStream(lexer);
        CParser parser = new CParser(token);
        ParseTree tree= parser.compilationUnit();

        var BlockParser = new BlockParser(tree);
        var rootNode = BlockParser.parse();

        clearListeneres();
        visitedNodes.clear();
        shapes.clear();
        arrows.clear();
        depthDictionary.clear();
        depthCounter = -1;
        RecursiveVisit(rootNode);
        drawNodes();
    }

    @FXML
    public void initialize() {
        fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        mainPane.getChildren().add(root);


    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(new Stage());

        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("c, txt files (*.c, *txt)", "*.c", "*.txt"));

        textArea.clear();
        if (file != null)
            try {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()) {
                    textArea.appendText(scanner.nextLine() + "\n");
                }

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Ошибка в чтении файла");
            }
    }

    @FXML
    private void saveBlockScheme() {
        if (root.getChildren().size() == 0) {
            JOptionPane.showMessageDialog(null, "Блок схема пуста");
            return;
        }

        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage snapShot = root.snapshot(new SnapshotParameters(), null);

                RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapShot, null);

                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }

    /**
     * Очистка всех подписок на движение блоков
     */
    private void clearListeneres() {
        var shapesNodes = Collections.list(shapes.keys());
        for (int i = 0; i < shapesNodes.size(); i++) {
            shapes.get(shapesNodes.get(i)).listeners.clear();
        }
    }

    public void drawElement(MouseEvent mouseEvent) {
    }

    /**
     * @param node
     * Проходит по дереву и записывает все узлы в словарь глубины
     */
    private void RecursiveVisit(BlockNode node) {
        depthCounter++;

        if (visitedNodes.contains(node)) {
            return;
        }

        visitedNodes.add(node);
        if (!depthDictionary.containsKey(depthCounter))
            depthDictionary.put(depthCounter, new ArrayList<BlockNode>());

        depthDictionary.get(depthCounter).add(node);

        for (int i = 0; i < node.children.size(); i++) {
            RecursiveVisit(node.children.get(i));
        }

        depthCounter--;
    }

    /**
     * @param shape Фигура
     *              Отрисовка на полотне
     */
    private void drawShape(BlockNodeShape shape) {
        root.getChildren().add(shape);

        root.applyCss();
        root.layout();

        shape.draw();
    }

    /**
     * Отрисовка всех узлов, зависимо от глубины узлов в словаре
     */
    private void drawNodes() {
        double x = 30;
        double y = 30;
        double lastWidthOffset = 0;
        double minWidthOffset = 50;
        double minHeightOffset = 20;

        double lastHeightOffset = y;

        for (int i = 0; i < depthDictionary.size(); i++) {
            var nodes = depthDictionary.get(i);
            for (int j = 0; j < nodes.size(); j++) {
                var node = nodes.get(j);
                var nodeShape = shapeFactory.Create(node.blockType, node, new Point2D(x + lastWidthOffset, y));
                if (nodeShape == null)
                    continue;
                drawShape(nodeShape);
                shapes.put(node, nodeShape);
                lastWidthOffset = lastWidthOffset + nodeShape.getWidth() + minWidthOffset;
                lastHeightOffset = Math.max(lastHeightOffset, lastHeightOffset + nodeShape.getHeight());
            }

            lastWidthOffset = 0;
            lastHeightOffset = lastHeightOffset + minHeightOffset;
            y = lastHeightOffset;
        }

        drawArrows(shapes);
    }

    /**
     * @param shapes Все узлы
     *               Отрисовка линий
     */
    private void drawArrows(Hashtable<BlockNode, BlockNodeShape> shapes) {
        mainPane.autosize();
        for (int i = 0; i < arrows.size(); i++)
            root.getChildren().remove(arrows.get(i));

        arrows.clear();

        var keys = Collections.list(shapes.keys());
        for (int i = 0; i < keys.size(); i++) {
            var node = keys.get(i);
            var nodeShape = shapes.get(node);
            for (int j = 0; j < node.children.size(); j++) {
                var childNode = node.children.get(j);
                var childNodeShape = shapes.get(childNode);
                drawArrow(nodeShape, childNodeShape);
            }
        }
    }

    /**
     * @param from Узел1
     * @param to Узел2
     *           Отрисовка линии между блоками
     */
    private void drawArrow(BlockNodeShape from, BlockNodeShape to) {
        var points = getArrowPoints(from, to);
        Line line = new Line(points.item1.getX(), points.item1.getY(), points.item2.getX(), points.item2.getY());
        Line arrow1 = new Line(0, 0, 2, 2);
        Line arrow2 = new Line(0, 0, 2, 2);

        String trueFalse = "";
        Color color = Color.BLACK;
        if (from.blockNode.blockType == BlockType.Condition || from.blockNode.blockType == BlockType.Loop) {
            Boolean isTrueStatement = from.blockNode.children.indexOf(to.blockNode) > 0;
            trueFalse = !isTrueStatement ? "Yes" : "No";
            color = !isTrueStatement ? Color.GREEN : Color.RED;
        }
        Arrow arrow = new Arrow(line, arrow1, arrow2, trueFalse, color);

        root.getChildren().add(arrow);
        root.applyCss();
        root.layout();

        arrows.add(arrow);
    }

    private Tuple<Point2D, Point2D> getArrowPoints(BlockNodeShape from, BlockNodeShape to) {
        var fromPoints = from.getArrayOfMinMaxPoints();
        var toPoints = to.getArrayOfMinMaxPoints();

        Point2D pointFromFinal = Point2D.ZERO;
        Point2D pointToFinal = Point2D.ZERO;
        Double lowestDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < fromPoints.size(); i++) {
            var fromPoint = fromPoints.get(i);
            for (int j = 0; j < toPoints.size(); j++) {
                var toPoint = toPoints.get(j);
                var newDistance = fromPoint.distance(toPoint);
                if (newDistance < lowestDistance) {
                    pointFromFinal = fromPoint;
                    pointToFinal = toPoint;
                    lowestDistance = newDistance;
                }
            }
        }

        return new Tuple<>(pointFromFinal, pointToFinal);
    }


}