package _554_Pedrinhas;

import java.util.Scanner;

public class HuxleyCode {

	public static class DisjointSet {
		private int[] parent;

		public DisjointSet(int size) {
			this.parent = new int[size];
			for (int i = 0; i < size; i ++) this.parent[i] = -1;
		}

		public int root(int v) {
			if (this.parent[v] < 0) return(v);
			parent[v] = root(parent[v]);
			return(parent[v]);
		}

		public boolean isSameSet(int v, int u) {
			return(root(v) == root(u));
		}

		public void merge(int v, int u) {
			v = root(v);
			u = root(u);
			// if (v == u) return; never gonna happen since I'm verifying this previously
			if (parent[u] < parent[v]) {
				int aux = parent[u];
				parent[u] = parent[v];
				parent[v] = aux;
			}
			parent[v] += parent[u];
			parent[u] = v;
		}

		public int setSize(int v) {
			return(-parent[root(v)]);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int t = 1;
		while (scanner.hasNextInt()) {
			int rocks = scanner.nextInt(), similarities = scanner.nextInt();
			DisjointSet disjointSet = new DisjointSet(rocks);
			int disjointSets = rocks, u, v;
			for (int i = 0; i < similarities; i ++) {
				u = scanner.nextInt(); v = scanner.nextInt();
				if (!disjointSet.isSameSet(u, v)) {
					disjointSets --;
					disjointSet.merge(u, v);
				}
			}

			int biggest = 0; for (int i = 0; i < rocks; i ++) biggest = Math.max(biggest, disjointSet.setSize(i));
			System.out.println("caso " + Integer.toString(t ++) + ": " + disjointSets + " " + biggest);
		}
	}
}
