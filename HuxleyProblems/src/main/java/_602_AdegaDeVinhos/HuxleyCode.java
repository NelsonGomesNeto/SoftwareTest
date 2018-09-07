package _602_AdegaDeVinhos;

import java.util.Scanner;

public class HuxleyCode {

	static int[] price;
	static int[][] dp;

	private static int solve(int left, int right, int years) {
		if (left > right) return(0);

		if (dp[left][right] == -1)
			dp[left][right] = Math.max(price[left] * years + solve(left + 1, right, years + 1), price[right] * years + solve(left, right - 1, years + 1));

		return(dp[left][right]);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int wines = scanner.nextInt();
		price = new int[wines]; for (int i = 0; i < wines; i ++) price[i] = scanner.nextInt();
		dp = new int[wines][wines]; for (int i = 0; i < wines; i ++) for (int j = 0; j < wines; j ++) dp[i][j] = -1;

		System.out.println(solve(0, wines - 1, 1));
	}
}
