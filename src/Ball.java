/**
 * Ball description.
 * 
 * @author Todor Balabanov
 */
class Ball implements Comparable<Ball> {

	/**
	 * Ball value.
	 */
	int value;

	/**
	 * Ball color.
	 */
	int color;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param value Ball value.
	 * @param color Ball color.
	 */
	public Ball(int value, int color) {
		super();
		this.value = value;
		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[" + value + "," + color + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Ball ball) {
		return this.value - ball.value;
	}

}