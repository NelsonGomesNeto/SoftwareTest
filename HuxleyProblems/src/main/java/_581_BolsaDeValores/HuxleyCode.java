package _581_BolsaDeValores;

import java.util.Scanner;

public class HuxleyCode {

	private static int n, c;
	private static int[] value;
	private static long[][] dp;

	private static long maxProfit(int i, int bought) {
		if (i == n) return(0);

		if (dp[i][bought] == -1) {
			long ans = maxProfit(i + 1, bought);
			if (bought == 0) ans = Math.max(ans, maxProfit(i + 1, 1) - value[i] - c);
			else ans = Math.max(ans, maxProfit(i + 1, 0) + value[i]);
			dp[i][bought] = ans;
		}
		return(dp[i][bought]);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		n = scanner.nextInt(); c = scanner.nextInt();
		dp = new long[n][2];
		value = new int[n];
		for (int i = 0; i < n; i ++) {
			value[i] = scanner.nextInt();
			dp[i][0] = dp[i][1] = -1;
		}

		System.out.println(maxProfit( 0, 0));
	}
}
