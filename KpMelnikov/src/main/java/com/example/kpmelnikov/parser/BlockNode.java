package com.example.kpmelnikov.parser;

import java.util.ArrayList;

public class BlockNode {
    public BlockType blockType;
    public ArrayList<String> tokens;

    /**
     * Дочерние узлы
     */
    // TO nodes
    public ArrayList<BlockNode> children;

    /**
     * Родительские блоки
     */
    // FROM nodes
    public ArrayList<BlockNode> parents;

    public BlockNode(BlockType blockType) {
        this(blockType, null);
    }

    public BlockNode(BlockType blockType, BlockNode parent)
    {
        tokens = new ArrayList<String>();
        children = new ArrayList<BlockNode>();
        parents = new ArrayList<BlockNode>();

        this.blockType = blockType;
        if (parent != null) {
            addParent(parent);
        }

    }

    /**
     * @param node Ребенок
     * Добавление "ребенка"
     */
    public void addChild(BlockNode node) {
        children.add(node);
        node.parents.add(this);
    }

    /**
     * @param node Родитель
     * Добавление родителя
     */
    public void addParent(BlockNode node) {
        parents.add(node);
        node.children.add(this);
    }

    /**
     * @param node Ребенок
     * Удалеине ребенка
     */
    public void removeChild(BlockNode node) {
        if (children.contains(node)) {
            children.remove(node);
            node.parents.remove(this);
        }
    }

    /**
     * @param node Родитель
     * Удаление родителя
     */
    public void removeParent(BlockNode node) {
        if (parents.contains(node)) {
            parents.remove(node);
            node.children.remove(this);
        }
    }
}
