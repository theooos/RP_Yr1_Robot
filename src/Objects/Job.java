package Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a job to be carried out.
 */
public class Job {

	public static Map<Integer, Job> currentJobs = new HashMap<Integer, Job>();

	private List<SingleTask> tasks;
    private float cancellationProb;

	/**
	 * Create an empty job.
	 */
	public Job() {
		this.tasks = new ArrayList<SingleTask>();
	}

	/**
	 * Create a job from a list of tasks.
	 * @param tasks The tasks in the job.
	 */
	public Job(List<SingleTask> tasks) {
		this.tasks = tasks;

		rewardPerItem();
		rewardPerWeight();
	}

	/**
	 * Add an item to the list of job items.
	 * @param item The job item to be added.
	 */
	public void addTask(SingleTask t) {
		tasks.add(t);

		rewardPerItem();
		rewardPerWeight();
	}

	/**
	 * Add an item to the list of job items.
	 * @param item The item to be added.
	 * @param qty The amount of the item needed.
	 */
	public void addTask(Item item, int qty) {
		tasks.add(new SingleTask(item, qty));

		rewardPerItem();
		rewardPerWeight();
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
	 * Calculate the reward for this job per item.
	 * @return The reward per item.
	 */
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

	/**
	 * Calculate the reward for this job per weight.
	 * @return The reward per weight.
	 */
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

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Job [tasks=" + tasks + "]";
	}   

}
