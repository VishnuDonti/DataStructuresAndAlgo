package com.datastructures.starter.merkleVersion2;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MerleTreeTester {

    public static void main(String[] args)  {
        List<Node> leafs = IntStream.rangeClosed(1, 30).mapToObj(MerleTreeTester::applyAsInt).collect(Collectors.toList()).stream().map(MerkleTreeHelper::getNode).collect(Collectors.toList());
        MerkleTree merkleTree = MerkleTreeHelper.constructTree(leafs);
        IntStream.rangeClosed(51, 16800).forEach(x -> MerkleTreeHelper.updateTree(MerkleTreeHelper.getNode(applyAsInt(x)),merkleTree));
        MerkleTreeHelper.updateTree(MerkleTreeHelper.getNode(applyAsInt(30,true)),merkleTree);
        MerkleTreeHelper.updateTree(MerkleTreeHelper.getNode(applyAsInt(3,true)),merkleTree);
        MerkleTreeHelper.updateTree(MerkleTreeHelper.getNode(applyAsInt(3,false)),merkleTree);
        Difference difference = MerkleTreeHelper.getUpdates(merkleTree,merkleTree.getRoot().get(1).getHashList().stream().filter(x -> x.getRootIndex() == 1).findFirst().orElse(null).getHashValue());
    }

    private static InputNode applyAsInt(int x) {
        InputNode inputNode = new InputNode();
        inputNode.setData(UUID.randomUUID().toString());
        inputNode.setId(String.valueOf(x));
        inputNode.setDeleted(false);
        return inputNode;
    }
    private static InputNode applyAsInt(int x, boolean isDeleted) {
        InputNode inputNode = new InputNode();
        inputNode.setData(UUID.randomUUID().toString());
        inputNode.setId(String.valueOf(x));
        inputNode.setDeleted(isDeleted);
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
