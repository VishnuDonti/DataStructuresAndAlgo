package com.datastructures.starter.tuesday20200512;

import java.util.List;
import java.util.Objects;

public class MerkleTree {

    public static void main(String[] args) {
        List.of(new Biller()).sort((x1,x2) -> {
            return x1.getFirst().compareTo(x2.getSecond());
        });
    }

    public static Node buildMerkle(){
        return new Node("","");
    }

    static class Node {

        String hash;
        String data;

        public Node(String hash, String data) {
            this.hash = hash;
            this.data = data;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
