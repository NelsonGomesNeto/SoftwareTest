package _200_Dentista;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class HuxleyCode {

	static class Event implements Comparable {
		Integer startingTime, endingTime;

		Event(int startingTime, int endingTime) {
			this.startingTime = startingTime;
			this.endingTime = endingTime;
		}

		@Override
		public int compareTo(Object o) { return this.endingTime.compareTo(((Event) o).endingTime); }
	}

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();

		Event[] events = new Event[n];
		for (int i = 0; i < n; i ++) {
			int x = scanner.nextInt(), y = scanner.nextInt();
			events[i] = new Event(x, y);
		}
		Arrays.sort(events);

		int firstToEnd = events[0].endingTime, canHappen = 1;
		for (int i = 0; i < n; i ++)
			if (events[i].startingTime >= firstToEnd) {
				canHappen ++;
				firstToEnd = events[i].endingTime;
			}

		System.out.println(canHappen);
	}
}
