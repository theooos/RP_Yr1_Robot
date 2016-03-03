package Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a job to be carried out.
 */
public class Job {

	private List<SingleTask> tasks;
    private boolean cancelled;
    private float cancellationProb;

	/**
	 * Create an empty job.
	 */
	public Job() {
		this.tasks = new ArrayList<SingleTask>();
        this.cancelled = false;
        this.cancellationProb = 0.0f;
	}

	/**
	 * Create a job from a list of tasks.
	 * @param tasks The tasks in the job.
	 */
	public Job(List<SingleTask> tasks) {
		this.tasks = tasks;
        this.cancelled = false;
        this.cancellationProb = 0.0f;
	}

	/**
	 * Add an item to the list of job items.
	 * @param item The job item to be added.
	 */
	public void addTask(SingleTask t) {
		tasks.add(t);
	}

	/**
	 * Add an item to the list of job items.
	 * @param item The item to be added.
	 * @param qty The amount of the item needed.
	 */
	public void addTask(String itemID, int qty) {
		tasks.add(new SingleTask(itemID, qty));
	}

    /**
     * Get the task at the given index.
     * @param i The given index.
     * @return The task at the given index if it exists.
     */
    public Optional<SingleTask> getTaskAtIndex(int i) {
        if(i >= 0 && i < tasks.size())
        	return Optional.of(tasks.get(i));
        else
            return Optional.empty();
    }

    /**
     * Get the task with the given item ID.
     * @param itemID The item ID.
     * @return The task with the given item ID if it exists.
     */
    public Optional<SingleTask> getTask(String itemID) {
        for(int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getItemID().equals(itemID)) {
                return Optional.of(tasks.get(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Set the cancellation probability.
     * @param p The cancellation probability.
     */
    public void setCancellationProb(float p) {
        this.cancellationProb = p;
    }

    /**
     * Get the cancellation probability.
     * @return The cancellation probability.
     */
    public float getCancellationProb() {
        return cancellationProb;
    }

    /**
     * Cancel this job.
     */
    public void cancelled() {
        cancelled = true;
    }

	/**
	 * Calculate the reward for this job per item.
	 * @return The reward per item.
	 */
    /* ADD BACK LATER
	public double rewardPerItem() {

		int numOfItems = 0;
		double reward = 0f;

		for(int i = 0; i < tasks.size(); i++) {
			Item item = tasks.get(i).getItem();
			numOfItems += tasks.get(i).getQuantity();
			reward += item.getReward() * tasks.get(i).getQuantity(); 
		}

		return (reward / (double) numOfItems);

	}
	*/

	/**
	 * Calculate the reward for this job per weight.
	 * @return The reward per weight.
	 */
	/* WILL ADD BACK LATER
	public double rewardPerWeight() {

		double reward = 0f;
		double weight = 0f;

		for(int i = 0; i < tasks.size(); i++) {
			Item item = tasks.get(i).getItem();
			reward += item.getReward() * tasks.get(i).getQuantity();
			weight += item.getWeight() * tasks.get(i).getQuantity();
		}

		return (reward / weight);
	}
	*/

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Job [tasks=" + tasks + "]";
	}   

}