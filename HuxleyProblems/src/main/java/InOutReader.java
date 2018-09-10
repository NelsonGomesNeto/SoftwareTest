import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InOutReader {

	public static String getStringFromFile(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		StringBuilder stringBuilder = new StringBuilder();
		if (!reader.ready()) return("");
		int r;
		while ((r = reader.read()) != -1)
			stringBuilder.append((char) r);
		reader.close();
		return(uniformString(stringBuilder.toString()));
	}

	public static String uniformString(String string) {
		return(string.replace("\r\n", "\n").replace("\r", "\n"));
	}
}
