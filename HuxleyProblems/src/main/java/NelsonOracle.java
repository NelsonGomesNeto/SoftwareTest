import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NelsonOracle {

    public static String getAnswer(String codePath, String inputPath) throws IOException {
        Runtime.getRuntime().exec("g++17" + codePath + "-o ./resources/run");
        Process process = Runtime.getRuntime().exec("./resources/run");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        for (String br: bufferedReader.lines()) {
            // TODO
        }
        return("");
    }
}
