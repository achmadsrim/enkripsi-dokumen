package kkpridwan;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
/**
 * Created by havi on 07/11/2016.
 */
    public static byte[] readFile(String filename) throws  Exception {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        return data;
    }
    public static void writeFile(byte[] data, String filename) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(data);
        fileOutputStream.close();
    }

}
