package _171_ReduzindoMapas;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuxleyCode { // 171

	public static class Node implements Comparable {
		Integer cost, vertex;

		private Node(int cost, int vertex) {
			this.cost = cost;
			this.vertex = vertex;
		}

		@Override
		public int compareTo(Object o) {
			return this.cost.compareTo(((Node) o).cost);
		}
	}

	public static void testingPriorityQueue() {
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(1, 2));
		priorityQueue.add(new Node(0, 3));
		System.out.println(priorityQueue.poll().vertex);
		System.out.println(priorityQueue.poll().vertex);
	}

	private static int prim(ArrayList<ArrayList<Node>> graph, int source, int cities) {
		int totalCost = 0;
		boolean[] visited = new boolean[cities];
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(0, source));
		while (!priorityQueue.isEmpty()) {
			Node node = priorityQueue.poll();

			if (visited[node.vertex]) continue;
			visited[node.vertex] = true;

			totalCost += node.cost;
			for (Node v: graph.get(node.vertex))
				priorityQueue.add(v);
		}
		return(totalCost);
	}

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int cities, streets;
		cities = scanner.nextInt(); streets = scanner.nextInt();

		ArrayList<ArrayList<Node>> graph = new ArrayList<>();
		for (int i = 0; i < cities; i ++) graph.add(new ArrayList<>());

		int u, v, c;
		for (int i = 0; i < streets; i ++) {
			u = scanner.nextInt(); v = scanner.nextInt(); c = scanner.nextInt();
			u --; v --;
			graph.get(u).add(new Node(c, v));
			graph.get(v).add(new Node(c, u));
		}

		int totalCost = prim(graph, 0, cities);
		System.out.println(totalCost);
	}
}
