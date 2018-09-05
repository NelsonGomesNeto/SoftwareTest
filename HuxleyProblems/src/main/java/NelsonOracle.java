import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class NelsonOracle {

    public static String getAnswer(String oraclePath, String input) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("g++ " + oraclePath + " -o ./src/main/resources/run.exe");
        while (p.waitFor()!=0);
        ProcessBuilder processBuilder = new ProcessBuilder("./src/main/resources/run.exe");
        Process process = processBuilder.start();
//        Process process = Runtime.getRuntime().exec("./src/main/resources/run.exe");
        process.getOutputStream().write(input.getBytes());
	    while (process.waitFor()!=0);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        bufferedReader.lines().forEach(stringBuilder::append);
        return(stringBuilder.toString());
    }
}
