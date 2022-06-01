package com.example.kpmelnikov.nodeShapes;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Arrow extends Group {

    private final Line line;
    private final Text text = new Text();

    /**
     * Создает пустую линию
     */
    public Arrow() {
        this(new Line(), new Line(), new Line(), "", Color.BLACK);
    }

    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;

    /**
     * @param line Линия основная
     * @param arrow1 Первая линия для наконечника
     * @param arrow2 Вторая линия для наконечника
     * @param textString Текст линии
     * @param color Цвет
     * Создает линию с заданными параметрами
     */
    public Arrow(Line line, Line arrow1, Line arrow2, String textString, Color color) {
        super(line, arrow1, arrow2);
        this.line = line;
        text.setFont(Font.font("Verdana", 13));
        text.setText(textString);
        text.setFill(color);
        getChildren().add(text);

        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            text.setY((ey + sy) * 0.5f);
            text.setX((ex + sx) * 0.5f);
            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    // start/end properties

    /**
     * @param value Значение X
     * Назначение координаты X
     */
    public final void setStartX(double value) {
        line.setStartX(value);
    }

    /**
     * Получение координаты X
     * @return Координата X
     */
    public final double getStartX() {
        return line.getStartX();
    }

    /**
     * Установка свойства X
     * @return
     */
    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public final void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public final void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }

}
