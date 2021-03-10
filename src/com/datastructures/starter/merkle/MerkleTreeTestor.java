package com.datastructures.starter.merkle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MerkleTreeTestor {

    public static void main(String[] args) throws Exception {
        List<InputNode> lists = IntStream.rangeClosed(1, 16382).mapToObj(MerkleTreeTestor::applyAsInt).collect(Collectors.toList());
        List<Node> leafs = lists.stream().map(MerkleTreeHelper::getNode).collect(Collectors.toList());
        MerkleTree merkleTree = MerkleTreeHelper.constructTree(leafs);
        lists = null;
        Node m4 =  MerkleTreeHelper.getNode(applyAsInt(16383));
        MerkleTreeHelper.addLeaf(m4,merkleTree);
        Node m5 =  MerkleTreeHelper.getNode(applyAsInt(16384));
        MerkleTreeHelper.addLeaf(m5,merkleTree);

    }

    private static InputNode applyAsInt(int x) {
        InputNode inputNode = new InputNode();
        inputNode.setData(UUID.randomUUID().toString());
        inputNode.setId(String.valueOf(x));
        inputNode.setDeleted(false);
        return inputNode;
    }

    public static class InputNode {
        String id;
        String data;
        boolean isDeleted;

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
