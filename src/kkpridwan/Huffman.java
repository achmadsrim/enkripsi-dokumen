package kkpridwan;

import java.util.PriorityQueue;

public class Huffman {
/**
 * Created by havi on 07/11/2016.
 */
    private static final int R = 256;

    private Huffman() {}

    public static byte[] compress(byte[] input) {
        byte[] data = input;
        BitCollection bits = new BitCollection();

        // lag frekvenstabell
        int[] freq = new int[R];
        for (int i = 0; i < data.length; i++) {
            freq[data[i] & 0xff]++;
        }

        // lag huffmantreet fra frekvenstabellen
        Node root = createTree(freq);

        // lager kodetabellen
        String[] codeTable = new String[R];
        buildCode(codeTable, root, "");

        // skriv treet til output
        writeTree(root, bits);

        // skriv antall bytes i ukomprimert fil til output
        bits.pushBits(input.length, 32);

        // komprimer input
        int inputLength = input.length;
        int percentage = 0;
        for (int i = 0; i < inputLength; i++) {

            String code = codeTable[data[i] & 0xFF];

            for (char ch : code.toCharArray()) {

                if (ch == '0')
                    bits.pushBit(false);
                else if (ch == '1')
                    bits.pushBit(true);

            }

            // print framgang til konsollen
            int newPercentage = (int) (((double)i/(double)inputLength)*100) + 1;
            if (newPercentage > percentage) {
                percentage = newPercentage;
                System.out.print("\r"+newPercentage+"%");
            }

        }

        bits.close();
        return bits.getBytes();
    }

    public static byte[] decompress(byte[] input) {
        BitCollection bits = new BitCollection(input);

        // lag huffmantre fra input
        Node root = readTree(bits);

        // hent antall bytes til ukomprimert fil
        int length = bits.readBits(32);

        byte[] output = new byte[length];
        int percentage = 0;
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = bits.readBit();
                if (bit) x = x.right;
                else x = x.left;
            }
            output[i] = x.value;

            // print framgang til konsollen
            int newPercentage = (int) (((double)i/(double)length)*100) + 1;
            if (newPercentage > percentage) {
                percentage = newPercentage;
                System.out.print("\r"+newPercentage+"%");
            }

        }
        bits.close();

        return output;
    }

    // lag tre fra frekvenstabellen
    private static Node createTree(int[] freq) {

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < R; i++) {
            if (freq[i] > 0) {
                pq.add(new Node((byte)(i & 0xFF), freq[i], null, null));
            }
        }

        while (pq.size() > 1) {
            Node left = pq.remove();
            Node right = pq.remove();
            Node parent = new Node((byte)0, left.freq+right.freq, left, right);
            pq.add(parent);
        }

        return pq.remove();
    }

    // rekursiv metode for å skrive treet
    private static void writeTree(Node node, BitCollection bits) {
        if (node.isLeaf()) {
            bits.pushBit(true);
            bits.pushBits(node.value & 0xff, 8);
            return;
        }
        bits.pushBit(false);
        writeTree(node.left, bits);
        writeTree(node.right, bits);
    }

    // rekursiv metode for å lese treet
    private static Node readTree(BitCollection bits) {
        boolean isLeaf = bits.readBit();
        if (isLeaf) {
            return new Node((byte)bits.readBits(8), -1, null, null);
        } else {
            return new Node((byte)0, -1, readTree(bits), readTree(bits));
        }
    }

    // rekursiv metode for å bygge kodetabellen
    private static void buildCode(String[] codeTable, Node node, String s) {
        if (!node.isLeaf()) {
            buildCode(codeTable, node.left, s+"0");
            buildCode(codeTable, node.right, s+"1");
        } else {
            codeTable[node.value & 0xFF] = s;
        }
    }

}
