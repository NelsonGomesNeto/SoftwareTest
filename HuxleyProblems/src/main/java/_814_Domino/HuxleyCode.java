package _814_Domino;

import java.util.Scanner;

public class HuxleyCode {

	static int[][] graph = new int[7][7];
	static int[] pieces = new int[7];

	private static boolean conex() {
		int i = 0;
		// for (int i = 0; /*i < 7*/; i ++) // since there's always gonna be a connection
		while (pieces[i] == 0) i ++;
		boolean[] visited = new boolean[7]; for (int j = 0; j < 7; j ++) visited[j] = false;
		conexAux(i, visited);
		for (int j = 0; j < 7; j ++)
			if (pieces[j] > 0 && !visited[j])
				return(false);
		return(true);
		// return(false); it's always gonna have at least on connection, since it's a undirected graph
	}

	private static void conexAux(int u, boolean[] visited) {
		if (visited[u]) return;
		visited[u] = true;
		for (int v = 0; v < 7; v ++)
			if (graph[u][v] > 0)
				conexAux(v, visited);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int n, r = 1;
		while ((n = scanner.nextInt()) != 0) {
			for (int i = 0; i < 7; i ++) for (int j = 0; j < 7; j ++) graph[i][j] = 0;
			for (int i = 0; i < 7; i ++) pieces[i] = 0;
			int u, v;
			for (int i = 0; i < n; i ++) {
				u = scanner.nextInt(); v = scanner.nextInt();
				graph[u][v] = graph[v][u] = 1;
				pieces[u] ++; pieces[v] ++;
			}
			int odd = 0; for (int i = 0; i < 7; i ++) odd += pieces[i] & 1;

			System.out.printf("Teste %d\n%s\n\n", r ++, (odd > 2 || !conex()) ? "nao" : "sim");
		}
	}
}
