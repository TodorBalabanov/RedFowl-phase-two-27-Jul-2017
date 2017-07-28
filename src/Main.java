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
			new Ball(5, 4), new Ball(6, 5), new Ball(7, 6), new Ball(8, 7), new Ball(9, 8), new Ball(10, 9),

			new Ball(11, 0), new Ball(12, 1), new Ball(13, 2), new Ball(14, 3), new Ball(15, 4), new Ball(16, 5),
			new Ball(17, 6), new Ball(18, 7), new Ball(19, 8), new Ball(20, 9),

			new Ball(21, 0), new Ball(22, 1), new Ball(23, 2), new Ball(24, 3), new Ball(25, 4), new Ball(26, 5),
			new Ball(27, 6), new Ball(28, 7), new Ball(29, 8), new Ball(30, 9),

			new Ball(31, 0), new Ball(32, 1), new Ball(33, 2), new Ball(34, 3), new Ball(35, 4), new Ball(36, 5),
			new Ball(37, 6), new Ball(38, 7), new Ball(39, 8), new Ball(40, 9),

			new Ball(41, 0), new Ball(42, 1), new Ball(43, 2), new Ball(44, 3), new Ball(45, 4), new Ball(46, 5),
			new Ball(47, 6), new Ball(48, 7), new Ball(49, 8), new Ball(50, 9),

			new Ball(51, 0), new Ball(52, 1), new Ball(53, 2), new Ball(54, 3), new Ball(55, 4), new Ball(56, 5),
			new Ball(57, 6), new Ball(58, 7), new Ball(59, 8), new Ball(60, 9),

			new Ball(61, 0), new Ball(62, 1), new Ball(63, 2), new Ball(64, 3), new Ball(65, 4), new Ball(66, 5),
			new Ball(67, 6), new Ball(68, 7), new Ball(69, 8), new Ball(70, 9),

			new Ball(71, 0), new Ball(72, 1), new Ball(73, 2), new Ball(74, 3), new Ball(75, 4), new Ball(76, 5),
			new Ball(77, 6), new Ball(78, 7), new Ball(79, 8), new Ball(80, 9),

			new Ball(81, 0), new Ball(82, 1), new Ball(83, 2), new Ball(84, 3), new Ball(85, 4), new Ball(86, 5),
			new Ball(87, 6), new Ball(88, 7), new Ball(89, 8), new Ball(90, 9), };

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

			/*
			 * Check all drawn balls.
			 */
			loop: for (int k = 0; k < drawn.length; k++) {

				/*
				 * Check all balls marked in the ticket.
				 */
				for (int j = 0; j < tickets[i].length; j++) {

					/*
					 * When ball is marked in the ticket.
					 */
					if (tickets[i][j] == drawn[k]) {
						count++;

						/*
						 * Mark the position of the latest ball guess.
						 */
						if (max < k) {
							max = k;
						}

						/*
						 * Six balls are known.
						 */
						if (count >= 6) {
							break loop;
						}

						/*
						 * If the number is found in the ticket there is no need
						 * to search the rest of the ticket.
						 */
						break;
					}
				}
			}

			if (count >= 6) {
				counters[i][max + 1]++;
			} else {
				counters[i][0]++;
			}

		}

		/*
		 * Count colors.
		 */
		int colors[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int k = 0; k < drawn.length; k++) {
			colors[drawn[k].color]++;
		}
		int sum = 0;
		for (int i = 0; i < colors.length; i++) {
			/*
			 * Count if more than six balls from the same color are drawn.
			 */
			if (colors[i] >= 6) {
				sum++;
			}
		}
		counters[5][sum]++;
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
						0, 0 },

				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

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
				System.out.print("= " + counters[i][j] + " / " + runs);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		simulate(1000000000L);
	}

}
