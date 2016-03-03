package Objects;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * SHARED OBJECTS
 * Used to represent a customer order: the currentJobs, the JobID and the items
 */

public class CustomerOrder implements Serializable {
	
	public List<Job> currentJobs;
	private UUID JobID;
	private List<SingleTask> tasks;

	public CustomerOrder(UUID JobID) {
		this.JobID = JobID;
	}
	
	public CustomerOrder(UUID JobID, List<SingleTask> tasks) {
		this.JobID = JobID;
		this.tasks = tasks;
	}
	
	/**
	 * Add a new task to the list of tasks
	 * @param item Item to add
	 */
	public void addTask(SingleTask task) {
		tasks.add(task);
	}
	
	/**
	 * Add a new abstract item to the list of SingleTasks
	 * @param item Item to add
	 * @param quanity Quantity to add
	 */
	public void addTask(String item, int quantity) {
		tasks.add(new SingleTask(item, quantity));
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "CustomerOrder [currentJobs=" + currentJobs + ", JobID=" + JobID + ", tasks=" + tasks + "]";
	}

}
