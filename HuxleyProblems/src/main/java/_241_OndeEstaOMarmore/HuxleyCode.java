package _241_OndeEstaOMarmore;

import java.util.Arrays;
import java.util.Scanner;

public class HuxleyCode {

	private static int binarySearch(int[] array, int lo, int hi, int target) {
		if (lo >= hi) return(array[lo] == target ? lo : -1);
		int mid = (lo + hi) / 2;
		if (target <= array[mid]) return(binarySearch(array, lo, mid, target));
		return(binarySearch(array, mid + 1, hi, target));
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int n, queries, r = 1;
		while (true) {
			n = scanner.nextInt(); queries = scanner.nextInt();
			if (n == 0 && queries == 0) break;
			int[] array = new int[n]; for (int i = 0; i < n; i ++) array[i] = scanner.nextInt();
			Arrays.sort(array);

			System.out.println("CASE# " + Integer.toString(r ++) + ":");
			for (int i = 0; i < queries; i ++) {
				int query = scanner.nextInt();
				int pos = binarySearch(array, 0, array.length - 1, query);
				if (pos == -1) System.out.println(Integer.toString(query) + " not found");
				else System.out.println(Integer.toString(query) + " found at " + Integer.toString(pos + 1));
			}
		}
	}
}
