package _817_Chuva;

import java.util.Scanner;

public class HuxleyCode {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] heights = new int[n]; for (int i = 0; i < n; i ++) heights[i] = scanner.nextInt();

		int[] left = new int[n], right = new int[n];
		left[0] = heights[0];
		for (int i = 1; i < n; i ++) left[i] = Math.max(left[i - 1], heights[i]);
		right[n - 1] = heights[n - 1];
		for (int i = n - 2; i >= 0; i --) right[i] = Math.max(right[i + 1], heights[i]);

		int filled = 0;
		for (int i = 1; i < n - 1; i ++)
			if (heights[i] < Math.min(left[i - 1], right[i + 1]))
				filled ++;
		System.out.println(filled);
	}
}
