package Objects;

/**
 * SHARED OBJECTS
 * Used to represent a single commands
 */

public class Commands {

	private String person;
	private Object command;

	/**
	 * Constructor
	 * @param <Person> The person type, we'll use String.
	 * @param <Command> The command type, we'll use Object.
	 */
	public Commands(String person, Object command) {
		this.person = person;
		this.command = command;
	}

	/**
	 * Get the person
	 * @return The person
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * Get the command
	 * @return The command
	 */
	public Object getCommand() {
		return command;
	}
}
