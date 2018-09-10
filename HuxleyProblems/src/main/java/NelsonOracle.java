import java.io.*;

public class NelsonOracle {

	final static String in = "./src/main/resources/in";
	final static String out = "./src/main/resources/out";

	NelsonOracle(String oraclePath) throws InterruptedException, IOException {
		Process compile = Runtime.getRuntime().exec("g++ " + oraclePath + " -o ./src/main/resources/run.exe -std=c++11");
		while (compile.waitFor() != 0);
	}

    String getAnswer() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("./src/main/resources/run.exe");
	    processBuilder = processBuilder.redirectInput(new File("./src/main/resources/in"));
		processBuilder = processBuilder.redirectOutput(new File("./src/main/resources/out"));

	    Process run = processBuilder.start();
	    run.waitFor();

	    return(InOutReader.getStringFromFile("./src/main/resources/out"));
    }
}
