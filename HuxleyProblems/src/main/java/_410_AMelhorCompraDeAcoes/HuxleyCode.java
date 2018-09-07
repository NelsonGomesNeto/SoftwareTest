package _410_AMelhorCompraDeAcoes;

import java.util.Scanner;

public class HuxleyCode {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int n = scanner.nextInt();
		int[] values = new int[n]; for (int i = 0; i < n; i ++) values[i] = scanner.nextInt();
		int[] minimum = new int[n], maximum = new int[n];
		minimum[0] = values[0]; maximum[n - 1] = values[n - 1];
		for (int i = 1; i < n; i ++) {
			minimum[i] = Math.min(minimum[i - 1], values[i]);
			maximum[n - i - 1] = Math.max(maximum[n - i], values[n - i - 1]);
		}

		int best = 0;
		for (int i = 0; i < n; i ++) best = Math.max(best, maximum[i] - minimum[i]);
		System.out.println(best);
 	}
}
