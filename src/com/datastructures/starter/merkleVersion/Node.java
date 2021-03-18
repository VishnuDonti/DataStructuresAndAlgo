package com.datastructures.starter.merkleVersion;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {

    List<Node> parents;
    Node leftChild;
    Node rightChild;
    boolean isDeleted;
    String data;
    String hash;
    boolean isLeaf;
    String resourceId;
    short level;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public List<Node> getParents() {
        return parents;
    }

    public void setParents(List<Node> parents) {
        this.parents = parents;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public Node copy()  {
        Node copyNode = new Node();
        copyNode.setParents(this.parents);
        copyNode.setLeftChild(this.leftChild);
        copyNode.setRightChild(this.rightChild);
        copyNode.setDeleted(this.isDeleted);
        copyNode.setData(this.data);
        copyNode.setHash(this.hash);
        copyNode.setLeaf(this.isLeaf);
        copyNode.setResourceId(this.resourceId);
        copyNode.setLevel(this.level);
        return copyNode;
    }
}
