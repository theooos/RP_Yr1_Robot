package Objects;

/**
 * 
 * @author theo
 *
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
