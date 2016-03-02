package Objects;

import java.io.Serializable;

/**
 * SHARED OBJECTS
 * Used to represent a single task: what item is needed and how many are needed.
 */
public class SingleTask implements Serializable {
	
	private int itemID;
	private int quantity;

    /**
     * Create a new single task.
     * @param itemID The item id.
     * @param quantity The quantity of the item.
     */
	public SingleTask(int itemID, int quantity) {
		
		this.itemID = itemID;
		this.quantity = quantity;
		
	}
	
	/**
	 * Get the item id.
	 * @return The item id.
	 */
	public Item getItemID() {
		return itemID;
	}
	
	/**
	 * Get the quantity.
	 * @return The quantity.
	 */
	public int getQuantity() {
		return quantity;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "SingleTask [item=" + itemID + ", quantity=" + quantity + "]";
	}
	
	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return (itemID+","+quantity);
	}

}