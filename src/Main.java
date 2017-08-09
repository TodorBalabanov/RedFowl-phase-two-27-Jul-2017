import java.util.List;
import java.util.Random;

import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {

	/**
	 * Pseudo-random number generator instance.
	 */
	private static final Random PRNG = new Random();

	/**
	 * Bingo balls data representation.
	 */
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

	/**
	 * All bingo balls as list for better shuffling.
	 */
	private static List<Ball> balls = null;

	/**
	 * Balls drawn in the single game.
	 */
	private static Ball drawn[] = null;

	/**
	 * Tickets marked in the single game.
	 */
	private static List<Ball[]> tickets[] = null;

	/**
	 * Combinations for each ticket.
	 */
	private static Combinations combinations[] = null;

	/**
	 * Winnings counters.
	 */
	private static long counters[][] = null;

	/**
	 * Shuffle balls and draw specified number out.
	 */
	private static void deal() {
		Collections.shuffle(balls);

		for (int i = 0; i < drawn.length; i++) {
			drawn[i] = balls.get(i);
		}
	}

	/**
	 * Random selection of numbers in the tickets.
	 * 
	 * @param numberOfBallsToGuess
	 *            How many balls to guess in a single game.
	 * @param maxTicketBalls
	 *            Maximum numbers which can be marked in a specific ticket.
	 */
	private static void mark(int numberOfBallsToGuess, int maxTicketBalls) {
		/*
		 * Fill numbers used for the tickets.
		 */
		Ball numbers[][] = new Ball[maxTicketBalls - numberOfBallsToGuess + 1][];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = new Ball[numberOfBallsToGuess + i];
			for (int j = 0; j < numbers[i].length; j++) {
				Ball ball = null;

				/*
				 * Select random ball which is not part of the ticket.
				 */
				do {
					ball = balls.get(PRNG.nextInt(balls.size()));

					for (int k = 0; k < j; k++) {
						if (numbers[i][k] == ball) {
							ball = null;
							break;
						}
					}
				} while (ball == null);

				numbers[i][j] = ball;
			}

			// TODO May be it is not needed.
			Arrays.sort(numbers[i]);
		}

		/*
		 * Fill tickets with numbers.
		 */
		for (int i = 0; i < numbers.length; i++) {
			tickets[i].clear();
			for (Iterator<int[]> it = combinations[i].iterator(); it.hasNext();) {
				int indexes[] = it.next();
				Ball[] combination = new Ball[numberOfBallsToGuess];
				for (int j = 0; j < combination.length; j++) {
					combination[j] = numbers[i][indexes[j]];
				}
				tickets[i].add(combination);
			}
		}
	}

	/**
	 * Locate final position of the combination guess.
	 * 
	 * @param combination
	 *            Combination to guess.
	 * @return The position of the final ball or minus one if the combination is
	 *         not presented in the list of the drawn numbers.
	 */
	private static int locate(Ball[] combination) {
		int count = 0;
		int position = -1;

		for (int i = 0; i < combination.length; i++) {
			for (int j = 0; j < drawn.length; j++) {
				if (drawn[j] == combination[i]) {
					/*
					 * Keep the farthest position.
					 */
					if (position < j) {
						position = j;
					}

					count++;
					break;
				}
			}
		}

		/*
		 * If all numbers are not found there is no win.
		 */
		if (count != combination.length) {
			position = -1;
		}

		return position;
	}

	/**
	 * Check out wining situations.
	 * 
	 * @param numberOfBallsToGuess
	 *            How many balls should be guessed.
	 * @param numberOfSameColor
	 *            How many balls should come out in order to count particular
	 *            color as complete.
	 */
	private static void count(int numberOfBallsToGuess, int numberOfSameColor) {
		for (int i = 0; i < tickets.length; i++) {
			boolean found = false;
			for (Ball[] combination : tickets[i]) {
				int position = locate(combination);

				/*
				 * If there is no enough numbers guessed do not count win.
				 */
				if (position == -1) {
					continue;
				}

				/*
				 * If win found mark this in order to keep track of tickets
				 * without win.
				 */
				found = true;

				/*
				 * Mark win at the particular place. Zero index is kept for
				 * tickets without win.
				 */
				counters[i][position + 1]++;
			}

			/*
			 * Keep track of the tickets without win.
			 */
			if (found == false) {
				counters[i][0]++;
			}
		}

		/*
		 * Count how many balls are drawn from each color.
		 */
		int colors[] = new int[counters[tickets.length].length];
		for (int k = 0; k < drawn.length; k++) {
			colors[drawn[k].color]++;
		}

		/*
		 * Count how many colors are complete.
		 */
		int sum = 0;
		for (int i = 0; i < colors.length; i++) {
			/*
			 * Count if more than six balls from the same color are drawn.
			 */
			if (colors[i] >= numberOfSameColor) {
				sum++;
			}
		}

		/*
		 * Colors counters are at the last index in the two dimensional array.
		 */
		counters[tickets.length][sum]++;
	}

	/**
	 * Game simulation.
	 * 
	 * @param runs
	 *            How many games to run.
	 * @param numberOfCloros
	 *            Number of different ball colors.
	 * @param drawnBallsNumber
	 *            How many balls to draw in a single game.
	 * @param numberOfBallsToGuess
	 *            How many balls to guess in a single game.
	 * @param maxTicketBalls
	 *            Maximum numbers which can be marked in a specific ticket.
	 * @param numberOfSameColor
	 *            How many balls from the same color should be drawn in order
	 *            the color to be counted as complete.
	 */
	private static void simulate(long runs, int numberOfCloros, int drawnBallsNumber, int numberOfBallsToGuess,
			int maxTicketBalls, int numberOfSameColor) {
		drawn = new Ball[drawnBallsNumber];

		balls = Arrays.asList(BALLS);

		/*
		 * Allocate tickets for all possible combinations.
		 */
		tickets = new ArrayList[maxTicketBalls - numberOfBallsToGuess + 1];
		combinations = new Combinations[maxTicketBalls - numberOfBallsToGuess + 1];
		for (int i = 0; i < tickets.length; i++) {
			tickets[i] = new ArrayList<Ball[]>();
			combinations[i] = new Combinations(numberOfBallsToGuess + i, numberOfBallsToGuess);
		}

		/*
		 * Counters for each ticket. Plus two is needed because the counters of
		 * the complete colors are also included in this two dimensional array.
		 */
		counters = new long[maxTicketBalls - numberOfBallsToGuess + 1 + 1][];
		for (int i = 0; i < counters.length - 1; i++) {
			/*
			 * At which ball win is achieved. Plus one is needed because it is
			 * possible to have situations without win.
			 */
			counters[i] = new long[drawnBallsNumber + 1];
		}

		/*
		 * How many colors are completed. Plus one is needed because it is
		 * possible no color to be completed.
		 */
		counters[counters.length - 1] = new long[numberOfCloros + 1];

		/*
		 * Simulation.
		 */
		for (long r = 0, interval = (runs / 10000) == 0 ? 1 : (runs / 10000); r < runs; r++) {
			/*
			 * Report progress.
			 */
			if (r % interval == 0) {
				// System.err.println(String.format("%5.2f", (100D * r / runs))
				// + "%");
			}

			/*
			 * Single run.
			 */
			deal();
			mark(numberOfBallsToGuess, maxTicketBalls);
			count(numberOfBallsToGuess, numberOfSameColor);
		}

		/*
		 * Report drawn balls number.
		 */
		System.out.print("Drawn Balls:");
		System.out.print("\t");
		System.out.println(drawnBallsNumber);
		System.out.println();

		/*
		 * Report number of balls to guess.
		 */
		System.out.print("Guess Balls:");
		System.out.print("\t");
		System.out.println(numberOfBallsToGuess);
		System.out.println();

		/*
		 * Report number of balls to guess.
		 */
		System.out.print("Color Complete:");
		System.out.print("\t");
		System.out.println(numberOfSameColor);
		System.out.println();

		/*
		 * Report experiments number.
		 */
		System.out.print("Experiments:");
		System.out.print("\t");
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

	/**
	 * Application single entry point method.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		/*
		 * Classical Lucky Six game.
		 */
		simulate(10000L, 10, 35, 6, 10, 6);

		// simulate(10000L, 10, 35, 6, 6, 6);
	}

}
