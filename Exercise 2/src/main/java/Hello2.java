import java.io.PrintStream;

public class Hello2 {

	private final PrintStream out;

	private String text1, text2;

	public Hello2(PrintStream out) {
		this.out = out;
	}

	public void sayHello() {
		text1 = "Hello";
		text2 = "World!";

		out.println(String.format("%s %s", text1, text2));
	}
}