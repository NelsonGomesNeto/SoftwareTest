package _1125_ParesDeParenteses;

import java.util.ArrayList;
import java.util.Scanner;

public class HuxleyCode {

	private static void backtracking(int open, int i, int n, String aux, ArrayList<String> answer) {
		if (i == 2 * n) {
			if (open == 0) answer.add(aux);
			return;
		}

		if (open < n)
			backtracking(open + 1, i + 1, n, aux + '(', answer);
		if (open > 0)
			backtracking(open - 1, i + 1, n, aux + ')', answer);
	}

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);

		int n = scanner.nextInt();
		ArrayList<String> answer = new ArrayList<>();
		StringBuilder aux = new StringBuilder();
		backtracking(0, 0, n, "", answer);
		System.out.print("[");
		for (int i = 0; i < answer.size(); i ++) {
			if (i > 0) System.out.print(", ");
			System.out.print(answer.get(i));
		}
		System.out.print("]\n");
	}
}
