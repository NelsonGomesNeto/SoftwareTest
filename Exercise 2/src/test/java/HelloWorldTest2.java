import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldTest2 {

	@Test
	void testHello() throws IOException {

		//	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		File f = new File("test.txt");
		f.createNewFile();
		FileOutputStream outContent = new FileOutputStream(f);

		Hello2 hello = new Hello2(new PrintStream(outContent));
		assertNotNull(hello, "Hello is null");

		assertAll("text1 and text2 aren't both null",
			() -> assertNull(getField("text1", hello), "text1 isn't null"),
			() -> assertNull(getField("text2", hello), "text2 isn't null"));

		String expected = "Hello World!" + System.lineSeparator();
		hello.sayHello();

		assertAll("text1 and text2 aren't both null",
			() -> assertEquals("Hello", getField("text1", hello), "text1 isn't null"),
			() -> assertEquals("World!", getField("text2", hello), "text2 isn't null"));

		assertEquals(expected, outContent.toString(), "Didn't output \"Hello World!\"");
	}

	private String getField(String name, Hello2 obj) {
		try {
			Field field = Hello2.class.getDeclaredField(name);
			field.setAccessible(true);

			return((String) field.get(obj));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
