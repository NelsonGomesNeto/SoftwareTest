package _546_ArvoreDeBuscaBinaria;

import java.util.*;

public class HuxleyCode {

	public static class Tree {
		int data;
		Tree left, right;

		Tree(int data) {
			this.data = data;
		}

		boolean isBinarySearchTree() {
			if (this.left != null && this.left.data > this.data) return (false);
			if (this.right != null && this.right.data < this.data) return (false);
			if ((this.left == null || this.left.isBinarySearchTree()) && (this.right == null || this.right.isBinarySearchTree()))
				return (true);
			return (false);
		}

		public static Tree buildTree(String treeString) {
			ArrayDeque<Character> treeQueue = new ArrayDeque<>();
			for (int i = 0; i < treeString.length(); i++) {
				if (treeString.charAt(i) == ' ') continue;
				treeQueue.add(treeString.charAt(i));
			}
			return (buildTree(treeQueue));
		}

		private static int getNumber(ArrayDeque<Character> treeQueue) {
			int number = 0;
			while (treeQueue.peek() >= '0' && treeQueue.peek() <= '9') {
				number = number * 10 + (treeQueue.pop() - '0');
			}
			return(number);
		}

		private static Tree buildTree(ArrayDeque<Character> treeQueue) {
			Tree tree = null;
			if (treeQueue.isEmpty()) return (tree);

			if (treeQueue.pop() == '(') {
				if (treeQueue.peek() == ')') {
					treeQueue.pop();
					return (tree);
				}
				int number = getNumber(treeQueue);
				tree = new Tree(number);
				tree.left = buildTree(treeQueue);
				tree.right = buildTree(treeQueue);
				if (!treeQueue.isEmpty()) treeQueue.pop();
			}

			return (tree);
		}

		public static void printTree(Tree tree, int depth) {
			System.out.print("(");
			if (tree == null) {
				System.out.print(")");
				return;
			}
			System.out.print(tree.data);
			printTree(tree.left, depth + 1);
			printTree(tree.right, depth + 1);
			System.out.print(")");
		}
	}

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);

		Tree tree = Tree.buildTree(scanner.nextLine());
		System.out.println(tree.isBinarySearchTree() ? "VERDADEIRO" : "FALSO");
	}
}