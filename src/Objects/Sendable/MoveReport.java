package Objects.Sendable;

/**
 * SHARED OBJECTS
 * Used to represent a move: if the robot has moved.
 */
public class MoveReport {

	private Boolean moved;
	
	public MoveReport(Boolean moved) {
		this.moved = moved;
	}

	/**
	 * Get if has moved.
	 * @return If moved.
	 */
	public Boolean hasMoved() {
		return moved;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "MoveReport [moved=" + moved + "]";
	}
	
	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return ("" + moved);
	}
	
}
