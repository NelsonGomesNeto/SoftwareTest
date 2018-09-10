package _1860_ImpostoReal;

import java.util.ArrayList;
import java.util.Scanner;

public class HuxleyCode {

	public static class Node {
		Integer cost, vertex;

		private Node(int cost, int vertex) {
			this.cost = cost;
			this.vertex = vertex;
		}
	}

	static int[] gold;
	static int cities, load;
	static ArrayList<ArrayList<Node>> graph;
	static boolean[] visited;

	private static int dfs(int u, int prev, int cost) {
		if (visited[u]) return(0);
		visited[u] = true;

		int totalDist = 0;
		for (Node next: graph.get(u)) {
			totalDist += dfs(next.vertex, u, next.cost);
		}
		totalDist += 2 * cost * ((gold[u] / load) + ((gold[u] % load != 0) ? 1 : 0));
		gold[prev] += gold[u];

		return(totalDist);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		cities = scanner.nextInt(); load = scanner.nextInt();

		gold = new int[cities]; for (int i = 0; i < cities; i ++) gold[i] = scanner.nextInt();

		graph = new ArrayList<>(cities);
		for (int i = 0; i < cities; i ++) graph.add(new ArrayList<>());

		int u, v, c;
		for (int i = 0; i < cities - 1; i ++) {
			u = scanner.nextInt(); v = scanner.nextInt(); c = scanner.nextInt();
			u --; v --;
			graph.get(u).add(new Node(c, v));
			graph.get(v).add(new Node(c, u));
		}

		visited = new boolean[cities]; for (int i = 0; i < cities; i ++) visited[i] = false;
		int minDist = dfs(0, 0, 0);
		System.out.println(minDist);
	}
}
