import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

    FileInputStream fileInputStream;

    public Reader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public static String readAll(FileInputStream in) throws IOException {

        ArrayList<Byte> bytes = new ArrayList<>();

        int size;
        while ((size = in.available()) != 0) {
            byte[] buff = new byte[size];

            while (size > 0) {
                int read = in.read(buff);
                for (byte b: buff)
                    bytes.add(b);
                buff = new byte[size - read];
                size -= read;
            }
        }

        byte[] buff = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i ++) {
            buff[i] = bytes.get(i);
        }

        return(new String(buff));
    }

    public static String prevReadAll(FileInputStream in) throws IOException {
        byte[] buff = new byte[in.available()];

        in.read(buff);

        return(new String(buff));
    }

    public static class MockFileInputStream extends FileInputStream {

        long size;

        public MockFileInputStream(File file) throws FileNotFoundException {
            super(file);
            size  = file.length();
        }

        @Override
        public int read(byte[] buff) throws IOException {
            int size = buff.length < 2 ? buff.length : buff.length - 1;

            byte[] myBytes = new byte[size];
            int read = super.read(myBytes);
            for (int i = 0; i < myBytes.length; i ++) {
                buff[i] = myBytes[i];
            }
            return(read);
        }

        @Override
        public int available() {
            if (size == 0) return(0);
            int result = (int) Math.min(3, size);
            size -= result;
            return(result);
        }
    }
}
