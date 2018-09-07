package _1138_Divisores;

import java.util.Scanner;

public class HuxleyCode {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt(), b = scanner.nextInt(), c = scanner.nextInt(), d = scanner.nextInt();
		double end = Math.sqrt(c);
		for (int i = 1; i <= end; i ++) {
			int n = a * i;
			if (n % b != 0 && c % n == 0 && d % n != 0) { System.out.println(n); return; }
		}
		System.out.println(-1);
	}
}
