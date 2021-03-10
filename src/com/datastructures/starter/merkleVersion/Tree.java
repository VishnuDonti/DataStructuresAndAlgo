package com.datastructures.starter.merkleVersion;

import java.io.Serializable;
import java.util.List;

public class Tree implements Serializable {

    List<Node> roots;
    Node currentVersion;
    List<List<Node>> leaves;
    boolean isOdd;

    public boolean isOdd() {
        return isOdd;
    }

    public void setOdd(boolean odd) {
        isOdd = odd;
    }

    public List<Node> getRoots() {
        return roots;
    }

    public void setRoots(List<Node> roots) {
        this.roots = roots;
    }

    public Node getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Node currentVersion) {
        this.currentVersion = currentVersion;
    }

    public List<List<Node>> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<List<Node>> leaves) {
        this.leaves = leaves;
    }
}
