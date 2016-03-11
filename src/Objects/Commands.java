package Objects;

/**
 * 
 * @author theo
 *
 * @param <Person> The person type, we'll use String.
 * @param <Command> The command type, we'll use Object.
 */
public class Commands {

	private String person;
	private Object command;
	
	public Commands(String person, Object command) {
		this.person = person;
		this.command = command;
	}

	public String getPerson(){
		return person;
	}
	
	public Object getCommand(){
		return command;
	}
}
