package com.example.kpmelnikov.nodeShapes;

import com.example.kpmelnikov.DragListener;
import com.example.kpmelnikov.parser.BlockNode;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class InputOutputNodeShape extends BlockNodeShape {
    private static float offset = 15f;
    private static float lineWidth = 3f;
    /**
     * Наклон линии
     */
    private static float leaning = 10f;

    /**
     * @param block Блок
     * @param point Точка
     * @param onDrag Ссылка на метод при движении
     */
    public InputOutputNodeShape(BlockNode block, Point2D point, DragListener onDrag) {
        super(block, point, onDrag);
    }

    /**
     * Отрисовка блока
     */
    @Override
    public void draw() {
        tokensLabel.applyCss();
        tokensLabel.layout();

        getChildren().clear();

        var textWidth = tokensLabel.prefWidth(-1) + offset;
        var textHeight = tokensLabel.prefHeight(-1) + offset;

        var widthOffset = leaning;
        var heightOffset = leaning;

        Polygon poly = new Polygon(
                0, textHeight + heightOffset,
                widthOffset, 0,
                textWidth + widthOffset, 0,
                textWidth, textHeight + heightOffset);

        Polygon clip = new Polygon(
                lineWidth, textHeight + heightOffset - lineWidth,
                widthOffset + lineWidth * 0.5f, lineWidth,
                textWidth + widthOffset - lineWidth * 1.5f, lineWidth,
                textWidth - lineWidth, textHeight + heightOffset - lineWidth);


        point = new Point2D(getTranslateX(), getTranslateY());
        width = poly.prefWidth(-1);
        height = poly.prefHeight(-1);

        poly.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        getChildren().add(poly);
        getChildren().add(clip);
        getChildren().add(tokensLabel);

        tokensLabel.setTranslateX((widthOffset + offset) * 0.5f);
        tokensLabel.setTranslateY((heightOffset + offset) * 0.5f);
    }
}
