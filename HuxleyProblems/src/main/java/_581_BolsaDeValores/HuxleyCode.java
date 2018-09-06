package _581_BolsaDeValores;

import java.util.Scanner;

public class HuxleyCode {

	private static int n, c;
	private static int[] value;
	private static long[][] dp;

	private static long maxProfit() {
		dp[n][0] = dp[n][1] = 0;
		for (int i = n - 1; i >= 0; i --) {
			dp[i][0] = Math.max(dp[i + 1][1] - (value[i] + c), dp[i + 1][0]);
			dp[i][1] = Math.max(dp[i + 1][0] + value[i], dp[i + 1][1]);
		}
		return(dp[0][0]);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		n = scanner.nextInt(); c = scanner.nextInt();
		dp = new long[n + 1][2];
		value = new int[n];
		for (int i = 0; i < n; i ++) {
			value[i] = scanner.nextInt();
			dp[i][0] = dp[i][1] = -1;
		}

		System.out.println(maxProfit());
	}
}
