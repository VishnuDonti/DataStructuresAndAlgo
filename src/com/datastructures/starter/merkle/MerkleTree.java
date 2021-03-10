package com.datastructures.starter.merkle;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.security.MessageDigest.*;

public class MerkleTree implements Serializable {


    private Node root;
    private Node start;
    private Node end;
    private boolean isOdd;
    private int leavesSize;

    public Node getRoot() {
        return root;
    }

    public int getLeavesSize() {
        return leavesSize;
    }

    public void setLeavesSize(int leavesSize) {
        this.leavesSize = leavesSize;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public boolean isOdd() {
        return isOdd;
    }

    public void setOdd(boolean odd) {
        isOdd = odd;
    }
}
