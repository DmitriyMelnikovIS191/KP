package com.example.kpmelnikov.nodeShapes;

import com.example.kpmelnikov.DragListener;
import com.example.kpmelnikov.parser.BlockNode;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ConditionNodeShape extends BlockNodeShape {

    private static float offset = 20f;
    private static float lineWidth = 3f;

    /**
     * @param block  Блок
     * @param point Точки
     * @param onDrag Ссылка на метод при перемещении блока
     */
    public ConditionNodeShape(BlockNode block, Point2D point, DragListener onDrag) {
        super(block, point, onDrag);;
    }

    /**
     * Отрисовка блока
     */
    @Override public void draw() {
        tokensLabel.applyCss();
        tokensLabel.layout();

        getChildren().clear();
        var textWidth = tokensLabel.prefWidth(-1) ;
        var textHeight = tokensLabel.prefHeight(-1);

        var widthOffset = offset + textWidth * 0.5f;
        var heightOffset = offset + textHeight * 0.5f;

        Group group = new Group();
        Polygon poly = new Polygon();
        poly.getPoints().addAll(
            0d, (textHeight + heightOffset) * 0.5f,
                (textWidth  + widthOffset) * 0.5f, 0d,
                textWidth + widthOffset, (textHeight + heightOffset) * 0.5f ,
                (textWidth + widthOffset) * 0.5f, textHeight + heightOffset);

        Polygon clip = new Polygon(
                lineWidth, (textHeight + heightOffset) * 0.5f,
                (textWidth +  widthOffset) * 0.5f, lineWidth,
                textWidth + widthOffset - lineWidth * 2, (textHeight + heightOffset) * 0.5f,
                (textWidth + widthOffset) * 0.5f, textHeight + heightOffset - lineWidth);

        poly.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        point = new Point2D(getTranslateX(), getTranslateY());
        width = poly.prefWidth(-1);
        height = poly.prefHeight(-1);

        getChildren().add(poly);
        getChildren().add(clip);
        getChildren().add(tokensLabel);

        tokensLabel.setTranslateX(widthOffset * 0.5f);
        tokensLabel.setTranslateY(heightOffset * 0.5f);
    }
}
