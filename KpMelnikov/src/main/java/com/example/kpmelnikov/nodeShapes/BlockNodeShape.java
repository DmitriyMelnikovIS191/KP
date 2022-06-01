package com.example.kpmelnikov.nodeShapes;

import com.example.kpmelnikov.DragListener;
import com.example.kpmelnikov.parser.BlockNode;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class BlockNodeShape extends Group {
    /**
     * Массив функций, которые подписаны на событие drag
     */
    public ArrayList<DragListener> listeners = new ArrayList<>();
    public BlockNode blockNode;
    /**
     * Текст блока
     */
    protected Label tokensLabel;
    /**
     * Ширина
     */
    protected double width;
    /**
     * Высота
     */
    protected double height;
    protected Point2D point;

    /**
     * Стартовая точка при перемещении блока
     */
    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     * @param block Информация о блоке
     * @param point Стартовая точка
     * @param onDrag Подписка на drag
     * Создает данный shape
     */
    public BlockNodeShape(BlockNode block, Point2D point, DragListener onDrag) {
        setTranslateX(point.getX());
        setTranslateY(point.getY());

        tokensLabel = new Label(getTextFromTokens(block.tokens));
        tokensLabel.setFont(Font.font("Verdana", 12));

        this.point = point;
        this.width = computePrefWidth(-1);
        this.height = computePrefHeight(-1);
        this.blockNode = block;

        getChildren().add(tokensLabel);

        listeners.add(onDrag);
        setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        setOnMouseDragged(mouseEvent -> {
            setTranslateX(Math.max(getTranslateX() + mouseEvent.getX() - mouseAnchorX, 0));
            setTranslateY(Math.max(getTranslateY() + mouseEvent.getY() - mouseAnchorY, 0));

            this.point = new Point2D(getTranslateX(), getTranslateY());
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onDrag();
            }

        });
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public Point2D getPosition() {
        return point;
    }

    /**
     * @return Минимальный y фигуры в пространстве
     */
    public double minY() {
        return getPosition().getY();
    }

    public double minX() {
        return getPosition().getX();
    }

    public double maxY() {
        return getPosition().getY() + getHeight();
    }

    public double maxX() {
        return getPosition().getX() + getWidth();
    }

    /**
     * Получить массив точек фигуры
     * @return Массив точек
     */
    public ArrayList<Point2D> getArrayOfMinMaxPoints() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        fromPoints.add(new Point2D(maxX(), maxY() - getHeight() * 0.5f));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5f, minY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5f, maxY()));
        fromPoints.add(new Point2D(minX(), maxY() - getHeight() * 0.5f));

        return fromPoints;
    }

    /**
     * @param tokens строки
     * Получение строки из списка строк
     * @return строка
     */
    private String getTextFromTokens(ArrayList<String> tokens) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < tokens.size(); i++) {
            builder.append(tokens.get(i));

            if (i != tokens.size() - 1)
                builder.append(System.getProperty("line.separator"));
        }

        return builder.toString();
    }

    public void draw() {
        tokensLabel.applyCss();
        tokensLabel.layout();
    }
}
