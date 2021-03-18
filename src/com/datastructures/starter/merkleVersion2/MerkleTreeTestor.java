package com.datastructures.starter.merkleVersion2;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MerkleTreeTestor {

    public static void main(String[] args) throws Exception {
        List<Node> leafs = IntStream.rangeClosed(1, 50).mapToObj(MerkleTreeTestor::applyAsInt).collect(Collectors.toList()).stream().map(MerkleTreeHelper::getNode).collect(Collectors.toList());
        MerkleTree merkleTree = MerkleTreeHelper.constructTree(leafs);
        IntStream.rangeClosed(50, 16800).forEach(x -> MerkleTreeHelper.updateTree(MerkleTreeHelper.getNode(applyAsInt(x)),merkleTree));
        List<Node> diff = MerkleTreeHelper.getUpdates(merkleTree,merkleTree.getRoot().get(0).getHashList().stream().filter(x -> x.getRootIndex() == 0).findFirst().orElse(null).getHashValue());
        byte[] bytes = SerializationUtils.serialize(merkleTree);
        FileUtils.writeByteArrayToFile(new File("aaaa2"),bytes);
        //FileUtils.writeByteArrayToFile(new File("bytes2"),bytes,(bytes.length/2)+1,(bytes.length/2)-1);
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
