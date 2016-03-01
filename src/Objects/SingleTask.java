package Objects;

import java.io.Serializable;

/**
 * SHARED OBJECTS
 * Used to represent a single task: what item is needed and how many are needed.
 */
public class SingleTask implements Serializable {
	
	private Item item;
	private int quantity;

	public SingleTask(Item item, int quantity) {
		
		this.item = item;
		this.quantity = quantity;
		
	}
	
	/**
	 * Get the item.
	 * @return The item.
	 */
	public Item getItem() {
		return item;
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
		return "SingleTask [item=" + item + ", quantity=" + quantity + "]";
	}
	
	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return (item+","+quantity);
	}

}
