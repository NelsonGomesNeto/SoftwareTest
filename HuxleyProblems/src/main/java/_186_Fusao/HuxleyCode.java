package _186_Fusao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
			parent[v] += parent[u];
			parent[u] = v;
		}

		public int setSize(int v) {
			return(-parent[root(v)]);
		}
	}

	public static void main(String args[]) {
//		try {
//			System.setIn(new FileInputStream("./src/test/resources/_186_Fusao/example.in"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		Scanner scanner = new Scanner(System.in);
		int banks = scanner.nextInt(), operations = scanner.nextInt();
		DisjointSet disjointSet = new DisjointSet(banks + 1);
		while (operations > 0) {
			char operation = scanner.next().charAt(0); int u = scanner.nextInt(), v = scanner.nextInt();
			if (operation == 'C')
				System.out.println(disjointSet.isSameSet(u, v) ? "S" : "N");
			else
				disjointSet.merge(u, v);
			operations --;
		}
	}
}
