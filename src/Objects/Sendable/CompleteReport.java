package Objects.Sendable;

import java.io.Serializable;

public class CompleteReport implements Serializable {
	
	private Boolean isPickup;
	private Boolean wasCompleted;	

	public CompleteReport(Boolean isPickup, Boolean wasCompleted) {
		this.isPickup = isPickup;
		this.wasCompleted = wasCompleted;
	}
	
	/**
	 * Get if item is picked up.
	 * @return If pickup.
	 */
	public Boolean getIsPickup() {
		return isPickup;
	}
	
	/**
	 * Get if completed.
	 * @return If completed.
	 */
	public Boolean wasCompleted() {
		return wasCompleted;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "CompleteReport [isPickup=" + isPickup + ", wasCompleted=" + wasCompleted + "]";
	}
	
	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return (isPickup+","+wasCompleted);
	}
	
}
