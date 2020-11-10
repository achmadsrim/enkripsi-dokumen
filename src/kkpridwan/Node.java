package kkpridwan;

import java.util.Comparator;

public class Node implements Comparable<Node> {
/**
 * Created by havi on 07/11/2016.
 */
    byte value;
    int freq;
    Node left, right;

    Node(byte value, int freq, Node left, Node right) {
        this.value = value;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null;
    }

    public int compareTo(Node node) {
        return this.freq - node.freq;
    }

}
