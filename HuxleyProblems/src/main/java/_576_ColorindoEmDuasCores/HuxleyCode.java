package _576_ColorindoEmDuasCores;

import java.util.ArrayList;
import java.util.Scanner;

public class HuxleyCode {

	public static boolean bicolorable(ArrayList<ArrayList<Integer>> graph, int[] color, int u, int now) {
		if (color[u] == 1 - now) return(false);
		color[u] = now;
		for (Integer v: graph.get(u))
			if (!bicolorable(graph, color, v, 1 - now)) return(false);
		return(true);
	}

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int nodes, edges;
		while ((nodes = scanner.nextInt()) != 0) {
			edges = scanner.nextInt();
			ArrayList<ArrayList<Integer>> graph = new ArrayList<>(nodes);
			for (int i = 0; i < nodes; i ++) graph.add(new ArrayList<>());

			int u, v;
			for (int i = 0; i < edges; i ++) {
				u = scanner.nextInt(); v = scanner.nextInt();
				graph.get(u).add(v);
			}
			int[] color = new int[nodes]; for (int i = 0; i < nodes; i ++) color[i] = -1;
			System.out.println((bicolorable(graph, color, 0, 0) ? "" : "NOT ") + "BICOLORABLE.");
		}
	}
}
