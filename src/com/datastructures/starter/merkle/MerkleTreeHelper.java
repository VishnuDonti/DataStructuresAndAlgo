package com.datastructures.starter.merkle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.security.MessageDigest.getInstance;

public class MerkleTreeHelper {

    public static String getSha256(String hash1, String hash2) {
        return getSha256(hash1 + hash2);
    }

    public static String getSha256(String originalString) {
        return DigestUtils.sha256Hex(originalString);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static Node getNode(MerkleTreeTestor.InputNode data) {
        Node node = new Node();
        node.setIsLeaf(true);
        node.setResourceId(data.getId());
        node.setData(data.getData());
        node.setLevel((short) 0);
        node.setDeleted(data.isDeleted());
        try {
            node.setHash(getSha256(new ObjectMapper().writeValueAsString(node)));
        } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
        }
        return node;
    }

    public static MerkleTree constructTree(List<Node> leafs) {
        MerkleTree merkleTree = new MerkleTree();
        if (leafs != null && leafs.size() > 0) {
            if (merkleTree.getStart() == null) {
                merkleTree.setStart(leafs.get(0));
            }
            if (merkleTree.getEnd() == null) {
                merkleTree.setEnd(leafs.get(leafs.size() - 1));
            }
        }
        constructParents(leafs, merkleTree);
        merkleTree.setLeavesSize(leafs.size());
        return merkleTree;
    }

    private static void constructParents(List<Node> leafs, MerkleTree merkleTree) {
        List<Node> intermediateNodes = new ArrayList<>(leafs.size() / 2);
        if (leafs == null || leafs.size() == 0) {
            return;
        }
        if (leafs.size() == 1) {
            merkleTree.setRoot(leafs.get(0));
            return;
        }
        if (leafs.size() % 2 != 0) {
            merkleTree.setOdd(true);
            leafs.add(leafs.get(leafs.size() - 1));

        }
        for (int i = 0; i < leafs.size(); i += 2) {
            String hashOfChildren = getSha256(leafs.get(i).getHash(), leafs.get(i + 1).getHash());
            Node node = new Node();
            node.setLevel((short) (leafs.get(i).getLevel() + 1));
            node.setHash(hashOfChildren);
//            node.setData(leafs.get(i).getHash() + leafs.get(i + 1).getHash());
            leafs.get(i).setParent(node);
            leafs.get(i + 1).setParent(node);
            node.setLeft(leafs.get(i));
            node.setRight(leafs.get(i + 1));
            intermediateNodes.add(node);
        }
        constructParents(intermediateNodes, merkleTree);
    }


    public static void addLeaf(Node leaf, MerkleTree merkleTree) {
        if (leaf == null || merkleTree == null) {
            return;
        }
        leaf.setIsLeaf(true);
        leaf.setLevel((short) 0);

        if (merkleTree.isOdd()) {
            merkleTree.getEnd().getParent().setRight(leaf);
            leaf.setParent(merkleTree.getEnd().getParent());
            merkleTree.setEnd(leaf);
            recalculateParentsForModification(merkleTree.getEnd());
            merkleTree.setOdd(false);
        } else {
            merkleTree.setOdd(true);

            if ((merkleTree.getLeavesSize() / 2) % 2 != 0) {
                Node level2Parent = merkleTree.getEnd().getParent().getParent();
                Node level1 = increaseHeight((short)1,leaf);
                level1.setParent(level2Parent);
                level2Parent.setRight(level1);
                recalculateParentsForModification(level1);
            } else {
                Node topNode = increaseHeight(merkleTree.getRoot().getLevel(), leaf);
                Node newRootNode = new Node();
                newRootNode.setLevel((short) (merkleTree.getRoot().getLevel() + 1));
                newRootNode.setHash(getSha256(merkleTree.getRoot().getHash(), topNode.getHash()));
//                newRootNode.setData(merkleTree.getRoot().getHash() + topNode.getHash());
                newRootNode.setLeft(merkleTree.getRoot());
                newRootNode.setRight(topNode);
                merkleTree.getRoot().setParent(newRootNode);
                topNode.setParent(newRootNode);
                merkleTree.setRoot(newRootNode);
            }
            merkleTree.setEnd(leaf);
        }
        merkleTree.setLeavesSize(merkleTree.getLeavesSize() + 1);
    }

    private static Node increaseHeight(short level, Node node) {
        while (node.getLevel() < level) {
            Node parentNode = new Node();
//            parentNode.setData(node.getData() + node.getData());
            parentNode.setHash(getSha256(node.getData(), node.getData()));
            parentNode.setLevel((short) (node.getLevel() + 1));
            parentNode.setLeft(node);
            node.setParent(parentNode);
            node = parentNode;
        }
        return node;
    }

    private static void recalculateParentsForModification(Node node) {
        Node parent = node.getParent();
        while (parent != null) {
            parent.setHash(getSha256(parent.getLeft().getHash(), parent.getRight() == null ? parent.getLeft().getHash() : parent.getRight().getHash()));
            parent = parent.getParent();
        }
    }

}
