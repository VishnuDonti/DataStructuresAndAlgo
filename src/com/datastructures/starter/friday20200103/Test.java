package com.datastructures.starter.friday20200103;

public class Test {

    public static void main(String[] args) {
        /*if (merkleTree.isOdd()) {
            merkleTree.setOdd(false);
            if(merkleTree.getEnd().getParent() == null) {
                Node left = merkleTree.getStart();
                Node right =  leaf;
                Node newRoot = getNewRoot(left, right, 1);
                merkleTree.getRoot().add(newRoot);
                merkleTree.setEnd(leaf);
            } else {
                merkleTree.getEnd().getParent().setRight(leaf);
                leaf.setParent(merkleTree.getEnd().getParent());
                merkleTree.setEnd(leaf);
                recalculateParentsForModification(merkleTree.getEnd(), merkleTree.getRoot().size());
                merkleTree.getRoot().add(merkleTree.getRoot().get(merkleTree.getRoot().size()-1));
            }
        } else {
            merkleTree.setOdd(true);
            *//*if ((merkleTree.getLeaves().get(merkleTree.getLeaves().size()-1).size() / 2) % 2 != 0) {
                List<Node> newNodesList  = new ArrayList<>(merkleTree.getLeaves().get(merkleTree.getRoot().size()-1));
                newNodesList.add(leaf);
                merkleTree.getLeaves().add(newNodesList);
                Node level2Parent = merkleTree.getEnd().getParent().getParent();
                Node level1 = increaseHeight((short) 1, leaf, merkleTree.getRoot().size());
                if(level2Parent == null) {
                    merkleTree.getRoot().add(getNewRoot(merkleTree.getRoot().get(merkleTree.getRoot().size()-1), level1));
                    return;
                }
                level2Parent.setRight(level1);
                level1.setParent(level2Parent);
                recalculateParentsForModification(level1, merkleTree.getRoot().size() - 2);
            } else {
                Node topNode = increaseHeight(merkleTree.getRoot().get(merkleTree.getRoot().size() - 1).getLevel(), leaf, merkleTree.getRoot().size());
                Node newRootNode = new Node();
                newRootNode.setLevel((short) (merkleTree.getRoot().get(merkleTree.getRoot().size() - 1).getLevel() + 1));
                Hash hash = new Hash();
                hash.setHashValue(getSha256(merkleTree.getRoot().get(merkleTree.getRoot().size() - 1).getHashList().get(merkleTree.getRoot().get(merkleTree.getRoot().size() - 1).getHashList().size() - 1).getHashValue(), topNode.getHashList().get(0).getHashValue()));
                hash.setRootIndex(merkleTree.getRoot().size() + 1);
                List<Hash> hashList = new ArrayList<>(1);
                hashList.add(hash);
                newRootNode.setHashList(hashList);
                newRootNode.setLeft(merkleTree.getRoot().get(merkleTree.getRoot().size() - 1));
                newRootNode.setRight(topNode);
                merkleTree.getRoot().get(merkleTree.getRoot().size() - 1).setParent(newRootNode);
                topNode.setParent(newRootNode);
                List<Node> newNodesList  = new ArrayList<>(merkleTree.getLeaves().get(merkleTree.getRoot().size()-1));
                newNodesList.add(leaf);
                merkleTree.getLeaves().add(newNodesList);
                merkleTree.getRoot().add(newRootNode);
            }*//*
            merkleTree.setEnd(leaf);
        }*/
        double a = (Math.log10(16385)/Math.log10(2));
    }
}
