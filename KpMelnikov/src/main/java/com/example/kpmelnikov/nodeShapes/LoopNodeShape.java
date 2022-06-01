package com.example.kpmelnikov.nodeShapes;

import com.example.kpmelnikov.DragListener;
import com.example.kpmelnikov.parser.BlockNode;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class LoopNodeShape extends BlockNodeShape {

    public LoopNodeShape(BlockNode block, Point2D point, DragListener onDrag) {
        super(block, point, onDrag);
    }

    private static float offset = 13f;
    private static float lineWidth = 2f;


    /**
     * Отрисовка
     */
    @Override
    public void draw() {
        tokensLabel.applyCss();
        tokensLabel.layout();

        getChildren().clear();

        var textWidth = tokensLabel.prefWidth(-1);
        var textHeight = tokensLabel.prefHeight(-1);

        Polygon poly = new Polygon(
                0, offset + textHeight * 0.5f,
                offset, 0,
                textWidth + offset, 0,
                textWidth + offset * 2, offset + textHeight * 0.5f,
                textWidth + offset, textHeight + offset * 2,
                offset, textHeight + offset * 2);

        Polygon clip = new Polygon(
                lineWidth, offset + textHeight * 0.5f,
                offset, lineWidth,
                textWidth + offset, lineWidth,
                textWidth + offset * 2 - lineWidth, offset + textHeight * 0.5f,
                textWidth + offset, textHeight + offset * 2 - lineWidth,
                offset, textHeight + offset * 2 - lineWidth);


        point = new Point2D(getTranslateX(), getTranslateY());
        width = poly.prefWidth(-1);
        height = poly.prefHeight(-1);

        poly.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        getChildren().add(poly);
        getChildren().add(clip);
        getChildren().add(tokensLabel);

        tokensLabel.setTranslateX(offset);
        tokensLabel.setTranslateY(offset);
    }
}
