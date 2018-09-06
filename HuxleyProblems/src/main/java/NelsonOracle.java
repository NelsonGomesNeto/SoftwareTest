import java.io.*;
import java.util.concurrent.TimeUnit;

public class NelsonOracle {

	String getStringFromFile(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		StringBuilder stringBuilder = new StringBuilder();
		if (!reader.ready()) return("");
		int r;
		while ((r = reader.read()) != -1)
			stringBuilder.append((char) r);
		reader.close();
		return(stringBuilder.toString());
	}

	NelsonOracle(String oraclePath) throws InterruptedException, IOException {
		Process compile = Runtime.getRuntime().exec("g++ " + oraclePath + " -o ./src/main/resources/run.exe");
		compile.waitFor();
	}

    String getAnswer() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("./src/main/resources/run.exe");
	    processBuilder = processBuilder.redirectInput(new File("./src/main/resources/in"));
		processBuilder = processBuilder.redirectOutput(new File("./src/main/resources/out"));

	    Process run = processBuilder.start();
	    run.waitFor();

	    return(getStringFromFile("./src/main/resources/out"));
    }
}
