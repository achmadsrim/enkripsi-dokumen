package kkpridwan;

public class HuffmanDecoder {
/**
 * Created by havi on 07/11/2016.
 */
    public static void main(String[] args) {

        try {

            String inputFile = args[0];
            String outputFile = args[1];

            byte[] input = FileHandler.readFile(inputFile);

            byte[] decompressed = Huffman.decompress(input);

            FileHandler.writeFile(decompressed, outputFile);

            System.out.println("\rFULLFØRT!");
            System.out.println("Fil dekomprimert til " + outputFile);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}
