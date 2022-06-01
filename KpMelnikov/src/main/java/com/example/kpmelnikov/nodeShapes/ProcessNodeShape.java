package com.example.kpmelnikov.nodeShapes;

import com.example.kpmelnikov.DragListener;
import com.example.kpmelnikov.parser.BlockNode;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProcessNodeShape extends BlockNodeShape {
    private static float offset = 10f;
    private static float lineWidth = 2f;

    public ProcessNodeShape(BlockNode block, Point2D point, DragListener onDrag) {
        super(block, point, onDrag);
    }

    @Override public void draw() {
        tokensLabel.applyCss();
        tokensLabel.layout();

        getChildren().clear();

        var textWidth = tokensLabel.prefWidth(-1);
        var textHeight = tokensLabel.prefHeight(-1);

        Rectangle rect = new Rectangle(
                0,
                0,
                textWidth + offset,
                textHeight + offset);

        Rectangle clip = new Rectangle(
                 lineWidth,
                 lineWidth,
                textWidth + offset - lineWidth * 2,
                textHeight + offset - lineWidth * 2);


        point = new Point2D(getTranslateX(), getTranslateY());
        width = rect.prefWidth(-1);
        height = rect.prefHeight(-1);

        rect.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        getChildren().add(rect);
        getChildren().add(clip);
        getChildren().add(tokensLabel);

        tokensLabel.setTranslateX(offset * 0.5f);
        tokensLabel.setTranslateY(offset * 0.5f);

    }
}
