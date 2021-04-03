package com.group.pdc_assignment_rpg.logic;

import java.util.*;

/**
 * Where a creature's inventory is stored, holds item and equipment
 * that are used during gameplay.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 */

public class Inventory {
	private final int MAX_INVENTORY_CAPACITY = 21;

	private Map<Item, Integer> inventory;
    private int capacity;
    private boolean visible;
    
    public Inventory() {
        this.setInventory(new HashMap<Item, Integer>());
        this.setCapacity(MAX_INVENTORY_CAPACITY);
        this.setVisible(false);
    }
    /**
     * Future-proofing: An integer will be assumed to be the
     * creature's individual carrying capacity.
     * Always a multiple of 7.
     * @param c = capacity
     */
    public Inventory(int c) {
    	this.setInventory(new HashMap<Item, Integer>());
    	this.setCapacity(c);
    	this.setVisible(false);
    }
    
    /**
     * Getters
     * 
     */
	public Map<Item, Integer> getInventory() {
		return inventory;
	}
	public int getCapacity() {
		return capacity;
	}
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Setters
	 * 
	 */
	public void setInventory(Map<Item, Integer> inventory) {
		this.inventory = inventory;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
    
	/**
	 * Item in inventory methods
	 * 
	 */

	public int getAmount(Item i) {
		return inventory.get(i);
	}
	/**
     * Add an item to the inventory.
     *
     * @param itemName is the name of the item
     * @param amount is the amount of the item
     */
	public void add(Item item) {
		if (inventory.size() <= capacity) {
			if(inventory.put(item, 1) == null) {
				inventory.put(item, inventory.get(item) + 1);
			}
		}
	}
    public void add(Item item, int amount) {
        if (inventory.size() <= capacity) {
            if(inventory.put(item, amount) == null) {
				inventory.put(item, inventory.get(item) + amount);
            }
        }
    }
    
    /**
     * Input should be any number of Items, only able to add one of each at a time
     * @param items
     */
    public void addMultiple(Item...items){
        for(int i=0; i < items.length; i++) {
            this.add(items[i]);
        }
	}
    
    /**
     * Gets an item in the inventory based on index given.
     *
     * @param index of the item in the inventory.
     * @return the item
     */
    public Item get(int index) {
    	Item[] items = inventory.keySet().toArray(new Item[inventory.size()]);
    	
        return items[index];
    }
    
    /**
     * Utility methods
     * 
     */
    
    /**
     * Toggles the visibility of the inventory on or off.
     */
    public void toggle() {
        if (!visible) {
            visible = true;
        } else {
            visible = false;
        }
    }
}
