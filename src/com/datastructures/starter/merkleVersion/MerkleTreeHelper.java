package com.datastructures.starter.merkleVersion;

import com.datastructures.starter.merkle.MerkleTreeTestor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MerkleTreeHelper {

    public static Tree createMerkleTree(List<Node> leafs) {
        Tree tree = new Tree();
        if (leafs == null || leafs.size() == 0) {
            return tree;
        }
        List<List<Node>> leavesVersionList = new ArrayList<>();
        leavesVersionList.add(leafs);
        tree.setLeaves(leavesVersionList);
        if (leafs.size() % 2 != 0) {
            tree.setOdd(true);
        }
        constructParents(leafs, tree);
        tree.setCurrentVersion(tree.getRoots().get(0));
        return tree;
    }

    public static Tree addLeaf(Node leaf, Tree tree) {
        if (leaf == null || tree == null) {
            return tree;
        }
        int indexOfLeafInCurrentVersion = findIndexOfLeafInCurrentVersion(leaf, tree);
        if (indexOfLeafInCurrentVersion != -1) {
            return updateLeaf(leaf, tree, indexOfLeafInCurrentVersion);
        }
        List<Node> currentVersionLeaves = new ArrayList<>(tree.getLeaves().get(tree.getRoots().size() - 1));
        currentVersionLeaves.add(leaf);
        tree.getLeaves().add(currentVersionLeaves);
        if (tree.isOdd()) {
            tree.setOdd(false);
        } else {
            tree.setOdd(true);
        }
        Node newRootRightChild = increaseHeight(tree.getCurrentVersion().getLevel(), leaf);
        Node leftChild = tree.getCurrentVersion();
        Node newRoot = new Node();
        newRoot.setRightChild(newRootRightChild);
        newRoot.setLeftChild(leftChild);
        newRoot.setHash(getSha256(leftChild.getHash(), newRootRightChild.getHash()));
        newRoot.setLevel((short) (leftChild.getLevel() + 1));
        List<Node> listOfParents = new ArrayList<>();
        listOfParents.add(newRoot);
        leftChild.setParents(listOfParents);
        newRootRightChild.setParents(listOfParents);
        tree.getRoots().add(newRoot);
        tree.setCurrentVersion(newRoot);
        return tree;
    }

    private static int findIndexOfLeafInCurrentVersion(Node leaf, Tree tree) {
        int numberOfRoots = tree.getRoots().size() - 1;
        List<Node> currentVersionLeaves = tree.getLeaves().get(numberOfRoots);
        return IntStream.range(0, currentVersionLeaves.size()).filter(lfIndex -> currentVersionLeaves.get(lfIndex).getResourceId().equals(leaf.getResourceId())).findFirst().orElse(-1);
    }

    public static Tree updateLeaf(Node leaf, Tree tree) {
        if (leaf == null || tree == null) {
            return tree;
        }
        int indexOfLeafInCurrentVersion = findIndexOfLeafInCurrentVersion(leaf, tree);
        return updateLeaf(leaf, tree, indexOfLeafInCurrentVersion);
    }

    private static Tree updateLeaf(Node leaf, Tree tree, int indexOfLeafInCurrentVersion) {
        if (leaf == null || tree == null) {
            return tree;
        }
        List<Node> currentVersionLeaves = new ArrayList<>(tree.getLeaves().get(tree.getRoots().size() - 1));
        Node previousNode = currentVersionLeaves.get(indexOfLeafInCurrentVersion);
        Node changedNode = previousNode.copy();
        if(changedNode.getHash().equalsIgnoreCase(leaf.getHash())) {
            return tree;
        }
        changedNode.setData(leaf.getData());
        changedNode.setHash(leaf.getHash());
        Node newRoot = reconstructTree(changedNode);
        currentVersionLeaves.set(indexOfLeafInCurrentVersion, changedNode);
        tree.getRoots().add(newRoot);
        tree.setCurrentVersion(newRoot);
        tree.getLeaves().add(currentVersionLeaves);
        return tree;
    }

    private static Node reconstructTree(Node leaf) {
        Node intermediateLeaf = leaf;
        Node intermediateLeafParent = null;
        while (intermediateLeaf.getParents() != null) {
            boolean isLeft = isLeft(intermediateLeaf);
            intermediateLeafParent = intermediateLeaf.getParents().get(leaf.getParents().size() - 1).copy();
            if(isLeft) {
                intermediateLeafParent.setLeftChild(intermediateLeaf);
            } else {
                intermediateLeafParent.setRightChild(intermediateLeaf);
            }
            intermediateLeafParent.setHash(getSha256(intermediateLeafParent.getLeftChild().getHash(), intermediateLeafParent.getRightChild().getHash()));
            intermediateLeaf = intermediateLeafParent;
        }
        return intermediateLeaf;
    }


    private static boolean isParentLeft(Node sibling) {
        Node latestParent = sibling.getParents().get(sibling.getParents().size() - 1);
        if (latestParent != null && latestParent.getParents() != null && latestParent.getParents().size() > 0) {
            return latestParent.getParents().get(latestParent.getParents().size() - 1).getLeftChild().getHash().equalsIgnoreCase(sibling.getParents().get(sibling.getParents().size() - 1).getHash());
        }
        return false;
    }

    private static boolean isLeft(Node previousNode) {
        int parentsSize = previousNode.getParents().size() - 1;
        if(previousNode.getResourceId() != null) {
            return previousNode.getParents().get(parentsSize).getLeftChild().getResourceId().equalsIgnoreCase(previousNode.getResourceId());
        }
        return previousNode.getParents().get(parentsSize).getLeftChild().getHash().equalsIgnoreCase(previousNode.getHash());
    }

    public static Node getNode(MerkleTreeTestor.InputNode data) {
        Node node = new Node();
        node.setLeaf(true);
        node.setResourceId(data.getId());
//        node.setData(data.getData());
        node.setLevel((short) 0);
        node.setDeleted(data.isDeleted());
        try {
            node.setHash(getSha256(new ObjectMapper().writeValueAsString(node)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node;
    }

    private static void constructParents(List<Node> leafs, Tree tree) {
        List<Node> intermediateNodes = new ArrayList<>(leafs.size() / 2);
        if (leafs.size() == 1) {
            tree.setRoots(leafs);
            return;
        }
        if (leafs.size() % 2 != 0) {
            leafs.add(leafs.get(leafs.size() - 1));
        }
        for (int i = 0; i < leafs.size(); i += 2) {
            String hashOfChildren = getSha256(leafs.get(i).getHash(), leafs.get(i + 1).getHash());
            Node node = new Node();
            node.setLevel((short) (leafs.get(i).getLevel() + 1));
            node.setHash(hashOfChildren);
            List<Node> listOfParents = new ArrayList<>(2);
            listOfParents.add(node);
            leafs.get(i).setParents(listOfParents);
            leafs.get(i + 1).setParents(listOfParents);
            node.setLeftChild(leafs.get(i));
            node.setRightChild(leafs.get(i + 1));
            intermediateNodes.add(node);
        }
        constructParents(intermediateNodes, tree);
    }

    public static String getSha256(String hash1, String hash2) {
        return getSha256(hash1 + hash2);
    }

    public static String getSha256(String originalString) {
        return DigestUtils.sha256Hex(originalString);
    }

    private static Node increaseHeight(short level, Node node) {
        Node mockNode = node.copy();
        while (node.getLevel() < level) {
            Node parentNode = new Node();
            parentNode.setHash(getSha256(node.getData(), node.getData()));
            parentNode.setLevel((short) (node.getLevel() + 1));
            parentNode.setLeftChild(node);
            parentNode.setRightChild(mockNode);
            List<Node> listOfParents = new ArrayList<>(2);
            listOfParents.add(parentNode);
            node.setParents(listOfParents);
            mockNode.setParents(listOfParents);
            node = parentNode;
            mockNode = parentNode.copy();
        }
        return node;
    }
}
