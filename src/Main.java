import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Ball implements Comparable<Ball> {

	int value;

	int color;

	public Ball(int value, int color) {
		super();
		this.value = value;
		this.color = color;
	}

	@Override
	public String toString() {
		return "[" + value + "," + color + "]";
	}

	@Override
	public int compareTo(Ball ball) {
		return this.value - ball.value;
	}

}

public class Main {

	private static final Ball BALLS[] = { new Ball(1, 0), new Ball(2, 1), new Ball(3, 2), new Ball(4, 3),
			new Ball(5, 4), new Ball(6, 5), new Ball(7, 6), new Ball(8, 7), new Ball(9, 8), new Ball(10, 0),
			new Ball(11, 1), new Ball(12, 2), new Ball(13, 3), new Ball(14, 4), new Ball(15, 5), new Ball(16, 6),
			new Ball(17, 7), new Ball(18, 8), new Ball(19, 0), new Ball(20, 1), new Ball(21, 2), new Ball(22, 3),
			new Ball(23, 4), new Ball(24, 5), new Ball(25, 6), new Ball(26, 7), new Ball(27, 8), new Ball(28, 0),
			new Ball(29, 1), new Ball(30, 2), new Ball(31, 3), new Ball(32, 4), new Ball(33, 5), new Ball(34, 6),
			new Ball(35, 7), new Ball(36, 8), new Ball(37, 0), new Ball(38, 1), new Ball(39, 2), new Ball(40, 3),
			new Ball(41, 4), new Ball(42, 5), new Ball(43, 6), new Ball(44, 7), new Ball(45, 8), new Ball(46, 0),
			new Ball(47, 1), new Ball(48, 2), new Ball(49, 3), new Ball(50, 4), new Ball(51, 5), new Ball(52, 6),
			new Ball(53, 7), new Ball(54, 8), new Ball(55, 0), new Ball(56, 1), new Ball(57, 2), new Ball(58, 3),
			new Ball(59, 4), new Ball(60, 5), new Ball(61, 6), new Ball(62, 7), new Ball(63, 8), new Ball(64, 0),
			new Ball(65, 1), new Ball(66, 2), new Ball(67, 3), new Ball(68, 4), new Ball(69, 5), new Ball(70, 6),
			new Ball(71, 7), new Ball(72, 8), new Ball(73, 0), new Ball(74, 1), new Ball(75, 2), new Ball(76, 3),
			new Ball(77, 4), new Ball(78, 5), new Ball(79, 6), new Ball(80, 7), new Ball(81, 8), new Ball(82, 0),
			new Ball(83, 1), new Ball(84, 2), new Ball(85, 3), new Ball(86, 4), new Ball(87, 5), new Ball(88, 6),
			new Ball(89, 7), new Ball(90, 8),

	};

	private static final Random PRNG = new Random();

	private static void deal(List<Ball> balls, Ball[] drawn) {
		Collections.shuffle(balls);

		for (int i = 0; i < drawn.length; i++) {
			drawn[i] = balls.get(i);
		}
	}

	private static void mark(Ball[][] tickets, List<Ball> balls) {
		for (int i = 0; i < tickets.length; i++) {
			for (int j = 0; j < tickets[i].length; j++) {
				Ball ball = null;

				/*
				 * Select random ball which is not part of the ticket.
				 */
				do {
					ball = balls.get(PRNG.nextInt(balls.size()));

					for (int k = 0; k < j; k++) {
						if (tickets[i][k] == ball) {
							ball = null;
							break;
						}
					}
				} while (ball == null);

				tickets[i][j] = ball;
			}

			// Arrays.sort(tickets[i]);
		}
	}

	private static void count(Ball drawn[], Ball[][] tickets, long[][] counters) {
		for (int i = 0, max, count; i < tickets.length; i++) {
			max = 0;
			count = 0;

			for (int k = 0; k < drawn.length; k++) {
				for (int j = 0; j < tickets[i].length; j++) {
					if (tickets[i][j] == drawn[k]) {
						count++;

						if (max < k) {
							max = k;
						}
					}
				}
			}

			if (count == tickets[i].length) {
				counters[i][max + 1]++;
			} else {
				counters[i][0]++;
			}
		}
	}

	private static void simulate(long runs) {
		Ball drawn[] = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, };

		List<Ball> balls = Arrays.asList(BALLS);

		Ball tickets[][] = {

				{ null, null, null, null, null, null, },

				{ null, null, null, null, null, null, null, },

				{ null, null, null, null, null, null, null, null, },

				{ null, null, null, null, null, null, null, null, null, },

				{ null, null, null, null, null, null, null, null, null, null, },

		};

		long counters[][] = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 }, };

		/*
		 * Simulation.
		 */
		for (long r = 0, interval = (runs / 10000) == 0 ? 1 : (runs / 10000); r < runs; r++) {
			/*
			 * Report progress.
			 */
			if (r % interval == 0) {
				System.err.println(String.format("%5.2f", (100D * r / runs)) + "%");
			}

			/*
			 * Single run.
			 */
			deal(balls, drawn);
			mark(tickets, balls);
			count(drawn, tickets, counters);
		}

		/*
		 * Report drawn balls number.
		 */
		System.out.println(drawn.length);
		System.out.println();

		/*
		 * Report experiments number.
		 */
		System.out.println(runs);
		System.out.println();

		/*
		 * Report absolute numbers.
		 */
		for (int i = 0; i < counters.length; i++) {
			for (int j = 0; j < counters[i].length; j++) {
				System.out.print(String.format("%8d", counters[i][j]));
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println();

		/*
		 * Report probabilities.
		 */
		for (int i = 0; i < counters.length; i++) {
			for (int j = 0; j < counters[i].length; j++) {
				System.out.print(String.format("%8.6f", ((double) counters[i][j]) / (double) runs));
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		simulate(10000000000L);
	}

}
