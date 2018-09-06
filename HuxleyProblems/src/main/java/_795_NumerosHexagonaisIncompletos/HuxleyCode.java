package _795_NumerosHexagonaisIncompletos;

import java.util.Scanner;

public class HuxleyCode {

	private static long[] dp = new long[50001];
	private static long[] six = new long[50001];

	private static void fill() {
		six[1] = 1; six[2] = 6;
		dp[1] = 1; dp[2] = 6;
		for (int i = 3; i <= 50000; i ++) six[i] = 6 * (i - 1);
		for (int i = 3; i <= 50000; i ++) dp[i] = six[i] + dp[i - 2];
	}

	public static void main(String args[]) {
		fill();

		Scanner scanner = new Scanner(System.in);
		int n;
		while (scanner.hasNextInt()) {
			n = scanner.nextInt();
			System.out.println(dp[n]);
		}
	}
}
