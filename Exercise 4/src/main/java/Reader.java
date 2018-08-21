import java.io.*;
import java.util.Scanner;

public class Reader {

    FileInputStream fileInputStream;

    public Reader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public static String readAll(String path) {
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            StringBuilder string = new StringBuilder();
            int aux;
            try {
                while ((aux = fileInputStream.read()) != -1)
                    string.append((char) aux);
                fileInputStream.close();
                return(string.toString());
            } catch (IOException exception) {
                return(null);
            }
        } catch (FileNotFoundException exception) {
            return(null);
        }
    }
}
