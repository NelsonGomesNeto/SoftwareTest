package _151_SimCity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuxleyCode {

    public static class Node implements Comparable {
        Integer vertex;
        Double cost;

        private Node(double cost, int vertex) {
            this.cost = cost;
            this.vertex = vertex;
        }

        @Override
        public int compareTo(Object o) {
            return this.cost.compareTo(((Node) o).cost);
        }
    }

    private static double prim(ArrayList<ArrayList<Node>> graph, int source, int cities) {
        double totalCost = 0;
        boolean[] visited = new boolean[cities];
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Node(0, source));
        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();

            if (visited[node.vertex]) continue;
            visited[node.vertex] = true;

            totalCost += node.cost;
            priorityQueue.addAll(graph.get(node.vertex));
        }
        return(totalCost);
    }


    public static double distance(double[] a, double[] b) {
        return(Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2)));
    }

    public static void main(String args[]) {
	    Locale.setDefault(Locale.forLanguageTag("en-us"));
        Scanner scanner = new Scanner(System.in);

        int n;
        while ((n = scanner.nextInt()) != 0) {
			double[][] point = new double[n][2];
			for (int i = 0; i < n; i ++) {
				point[i][0] = scanner.nextDouble();
				point[i][1] = scanner.nextDouble();
			}

			ArrayList<ArrayList<Node>> graph = new ArrayList<>(n);
			for (int i = 0; i < n; i ++) graph.add(new ArrayList<>());
			for (int i = 0; i < n; i ++)
				for (int j =  i + 1; j < n; j ++) {
					graph.get(i).add(new Node(distance(point[i], point[j]), j));
					graph.get(j).add(new Node(distance(point[i], point[j]), i));
				}

			System.out.printf("%.2f" + System.lineSeparator(), prim(graph, 0, n));
		}
    }
}
