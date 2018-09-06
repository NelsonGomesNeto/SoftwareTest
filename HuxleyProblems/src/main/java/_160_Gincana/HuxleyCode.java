package _160_Gincana;

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
			if (v == u) return;
			if (parent[u] < parent[v]) {
				int aux = parent[u];
				parent[u] = parent[v];
				parent[v] = aux;
			}
			disjointSets --;
			parent[v] += parent[u];
			parent[u] = v;
		}

		public int setSize(int v) {
			return(-parent[root(v)]);
		}
	}

	static int disjointSets;

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int friends = scanner.nextInt(), relations = scanner.nextInt();
		DisjointSet disjointSet = new DisjointSet(friends + 1);
		disjointSets = friends;

		int u, v;
		for (int i = 0; i < relations; i ++) {
			u = scanner.nextInt(); v = scanner.nextInt();
			disjointSet.merge(u, v);
		}

		System.out.println(disjointSets);
	}
}
