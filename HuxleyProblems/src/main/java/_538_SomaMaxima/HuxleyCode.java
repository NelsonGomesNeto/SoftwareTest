package _538_SomaMaxima;

import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HuxleyCode {

//	static void printMatrix(int[][] mat) {
//		for (int i = 0; i < mat.length; i ++) {
//			for (int j = 0; j < mat.length; j ++)
//				System.out.printf("%d ", mat[i][j]);
//			System.out.println();
//		}
//	}

	public static int[][] buildAccumulatedSum(int[][] mat) {
		int size = mat.length;
		int[][] accSum = new int[size][size];
		for (int i = 0; i < size; i ++) {
			accSum[i][0] = mat[i][0] + (i>0 ? accSum[i - 1][0] : 0); // Needs this (i>0)
			int left = mat[i][0];
			for (int j = 1; j < size; j ++) {
				left += mat[i][j];
				accSum[i][j] = left + (i>0 ? accSum[i - 1][j] : 0);
			}
		}
		return(accSum);
	}

	public static int getSum(int[][] accSum, int li, int lj, int hi, int hj) {
		int sum = accSum[hi][hj]; //accSum[li][lj] + (!(li==lj&&hi==hj)? accSum[hi][hj] : 0);
		if (lj > 0) sum -= accSum[hi][lj - 1]; // "hi" was accSum.length
		if (li > 0) sum -= accSum[li - 1][hj];
		if (lj > 0 && li > 0) sum += accSum[li - 1][lj - 1];
		return(sum);
	}

	public static int kadane(int[] array) {
		int size = array.length, sum = 0, maxSum = array[0];
		for (int i = 0; i < size; i ++) {
			sum += array[i];
			maxSum = Math.max(maxSum, sum);
			if (sum < 0) sum = 0;
		}
		return(maxSum); // was sum
	}

	public static void main(String args[]) {
//		try {
//			System.setIn(new FileInputStream("./src/test/resources/_538_SomaMaxima/example.in"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		int[][] mat = new int[size][size];
		for (int i = 0; i < size; i ++)
			for (int j = 0; j < size; j ++)
				mat[i][j] = scanner.nextInt();

		int[][] accSum = buildAccumulatedSum(mat);
		int maxSum = mat[0][0];
		for (int li = 0; li < size; li ++)
			for (int hi = li; hi < size; hi ++) {
				int[] auxArray = new int[size];
				for (int j = 0; j < size; j ++) auxArray[j] = getSum(accSum, li, j, hi, j);
				maxSum = Math.max(maxSum, kadane(auxArray));
			}

		System.out.println(maxSum);
	}
}
