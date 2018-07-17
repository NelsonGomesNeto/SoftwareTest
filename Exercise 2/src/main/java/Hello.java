import java.io.FileInputStream;
import java.net.Socket;

public class Hello {

    private String text1, text2;

    public void sayHello() {
        text1 = "Hello";
        text2 = "World!";

        System.out.println(String.format("%s %s", text1, text2));
    }
}
