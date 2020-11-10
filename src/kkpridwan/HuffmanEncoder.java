package kkpridwan;

public class HuffmanEncoder {
/**
 * Created by havi on 07/11/2016.
 */
    public static void main(String[] args) {

        try {

            String inputFile = args[0];
            String outputFile = args[1];

            byte[] input = FileHandler.readFile(inputFile);

            byte[] compressed = Huffman.compress(input);

            FileHandler.writeFile(compressed, outputFile);

            System.out.println("\rFULLFØRT!");
            System.out.println("Fil komprimert til " + outputFile);
            System.out.println("Original fil:\t" + input.length + " bytes");
            System.out.println("Komprimert fil:\t" + compressed.length + " bytes");
            double prosent = ((double)compressed.length/(double)input.length) * 100;
            System.out.println("Komprimering:\t" + prosent + "%");


        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}
