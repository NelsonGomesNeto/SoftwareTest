package _1997_Frete;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuxleyCode {

	public static int inf = 1<<20;

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

	private static int dijkstra(ArrayList<ArrayList<Node>> graph, int source, int destination) {
		int size = graph.size();
		int[] cost = new int[size]; for (int i = 0; i < size; i ++) cost[i] = inf;
		cost[source] = 0;
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(0, source));
		while (!priorityQueue.isEmpty()) {
			Node now = priorityQueue.poll();
			for (Node next: graph.get(now.vertex)) {
				if (now.cost + next.cost < cost[next.vertex]) {
					cost[next.vertex] = now.cost + next.cost;
					priorityQueue.add(new Node(cost[next.vertex], next.vertex));
				}
			}
		}
		return(cost[destination]);
	}

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt(), m = scanner.nextInt();

		ArrayList<ArrayList<Node>> graph = new ArrayList<>();
		for (int i = 0; i <= n; i ++) graph.add(new ArrayList<>());

		for (int i = 0; i < m; i ++) {
			int u = scanner.nextInt(), v = scanner.nextInt(), c = scanner.nextInt();
			graph.get(u).add(new Node(c, v));
			graph.get(v).add(new Node(c, u));
		}

		System.out.println(dijkstra(graph, 1, n));
	}
}
