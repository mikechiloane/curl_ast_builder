package com.faboda.curl.ast;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {

    private final NodeType type;
    private String value;
    private ASTNode parent;
    private List<ASTNode> children;
    private String key;

    public ASTNode(NodeType type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public NodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public void addChildren(List<ASTNode> children) {
        this.children.addAll(children);
    }

    public void resetChildren() {
        this.children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ASTNode{" + this.getType() + "}";
    }
}
