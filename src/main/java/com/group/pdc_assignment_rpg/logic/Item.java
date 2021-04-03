package com.group.pdc_assignment_rpg.logic;

/**
 * These items get directly added into a creature's inventory.
 * They are an extension of the StaticObject object which holds
 * their position in the world.
 * @author Macauley Cunningham - 19072621
 *
 */
public class Item {
	private String name;
	private ItemList item;
	
	public Item(String n, ItemList i){
		this.setName(n);
		this.setItem(i);
	}

	public String getName() {
		return name;
	}
	public ItemList getItem() {
		return item;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	private void setItem(ItemList item) {
		this.item = item;
	}
	
	public String toString() {
		return this.getName();
	}
}
