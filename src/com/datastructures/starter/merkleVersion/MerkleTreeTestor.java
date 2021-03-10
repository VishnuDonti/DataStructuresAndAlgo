package com.datastructures.starter.merkleVersion;


import com.datastructures.starter.merkle.MerkleTree;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MerkleTreeTestor {

    public static void main(String[] args) throws Exception {
        List<com.datastructures.starter.merkle.MerkleTreeTestor.InputNode> lists = IntStream.rangeClosed(1, 10000).mapToObj(x -> applyAsInt(x,false)).collect(Collectors.toList());
        List<Node> leafs = lists.stream().map(MerkleTreeHelper::getNode).collect(Collectors.toList());
        Tree merkleTree = MerkleTreeHelper.createMerkleTree(leafs);
        List<com.datastructures.starter.merkle.MerkleTreeTestor.InputNode> lists2 = IntStream.rangeClosed(10000,10200).mapToObj(x -> applyAsInt(x,false)).collect(Collectors.toList());
        List<Node> leafs2 = lists2.stream().map(MerkleTreeHelper::getNode).collect(Collectors.toList());
        leafs2.stream().forEach(x -> MerkleTreeHelper.addLeaf(x,merkleTree));
        byte[] b = SerializationUtils.serialize(merkleTree);
        FileUtils.writeByteArrayToFile(new File("MerkleVersionBytes1"), b);
    }

    private static com.datastructures.starter.merkle.MerkleTreeTestor.InputNode applyAsInt(int x,boolean dup) {
        com.datastructures.starter.merkle.MerkleTreeTestor.InputNode inputNode = new com.datastructures.starter.merkle.MerkleTreeTestor.InputNode();
        inputNode.setData(UUID.randomUUID().toString());
        inputNode.setId(String.valueOf(x));
        inputNode.setDeleted(false);
        return inputNode;
    }

}
