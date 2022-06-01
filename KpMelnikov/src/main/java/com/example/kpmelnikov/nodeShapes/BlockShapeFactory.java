package com.example.kpmelnikov.nodeShapes;

import com.example.kpmelnikov.DragListener;
import com.example.kpmelnikov.parser.BlockNode;
import com.example.kpmelnikov.parser.BlockType;
import javafx.geometry.Point2D;

import java.util.Hashtable;

public class BlockShapeFactory {
    /**
     * Ссылка на метод при перемещении блока
     */
    private DragListener onDrag;
    private Hashtable<BlockType, BaseShapeFactoryMethod> _factories = new Hashtable<>();

    /**
     * @param onDrag Ссылка на метод при перемещении блока
     * Создает конструктор всех фигур
     */
    public BlockShapeFactory(DragListener onDrag) {
        this.onDrag = onDrag;

        _factories = new Hashtable<>();
        _factories.put(BlockType.Start, new StartOrEndNodeShapeFactory());
        _factories.put(BlockType.End, new StartOrEndNodeShapeFactory());
        _factories.put(BlockType.Condition, new ConditionNodeShapeFactory());
        _factories.put(BlockType.Loop, new LoopNodeShapeFactory());
        _factories.put(BlockType.Process, new ProcessNodeShapeFactory());
        _factories.put(BlockType.InputOutput, new InputOutputNodeShapeFactory());
    }

    /**
     * @param blockType Тип блока
     * @param blockNode Ссылка на узел блока
     * @param initialPosition Стартовая точка
     * Создает блок-схемы
     * @return
     */
    public BlockNodeShape Create(BlockType blockType, BlockNode blockNode, Point2D initialPosition) {
        var factory = _factories.getOrDefault(blockType, null);

        return  factory == null ? null : factory.Create(blockNode, initialPosition, onDrag);
    }
}

/**
 * Абстрактный класс фабрики
 */
abstract class BaseShapeFactoryMethod {
    public abstract BlockNodeShape Create(BlockNode blockNode, Point2D initialPosition, DragListener onDrag);
}

/**
 * Метод который создает фигуру для начального и конечного узла
 */
class StartOrEndNodeShapeFactory extends BaseShapeFactoryMethod {
    @Override
    public BlockNodeShape Create(BlockNode blockNode, Point2D initialPosition, DragListener onDrag) {
        return new StartOrEndNodeShape(blockNode, initialPosition, onDrag);
    }
}

/**
 * Создает объект блока процессов
 */
class ProcessNodeShapeFactory extends BaseShapeFactoryMethod {
    @Override
    public BlockNodeShape Create(BlockNode blockNode, Point2D initialPosition, DragListener onDrag) {
        return new ProcessNodeShape(blockNode, initialPosition, onDrag);
    }
}

/**
 * Блок для входных-выходных данных
 */
class InputOutputNodeShapeFactory extends BaseShapeFactoryMethod {
    @Override
    public BlockNodeShape Create(BlockNode blockNode, Point2D initialPosition, DragListener onDrag) {
        return new InputOutputNodeShape(blockNode, initialPosition, onDrag);
    }
}

class ConditionNodeShapeFactory extends BaseShapeFactoryMethod {
    @Override
    public BlockNodeShape Create(BlockNode blockNode, Point2D initialPosition, DragListener onDrag) {
        return new ConditionNodeShape(blockNode, initialPosition, onDrag);
    }
}

class LoopNodeShapeFactory extends BaseShapeFactoryMethod {
    @Override
    public BlockNodeShape Create(BlockNode blockNode, Point2D initialPosition, DragListener onDrag) {
        return new LoopNodeShape(blockNode, initialPosition, onDrag);
    }
}