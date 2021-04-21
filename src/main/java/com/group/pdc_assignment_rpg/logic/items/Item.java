package com.group.pdc_assignment_rpg.logic.items;

import java.io.IOException;

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
	
	private static int MAX_NAME_LENGTH = 23;
	
	public Item(String n, ItemList i){
		try {
			this.setName(n);
		} catch (IOException e) {
			this.name = i.toString();
		}
		this.setItem(i);
	}
        
	public String getName() {
		return name;
	}
	public ItemList getItem() {
		return item;
	}
	
	private void setName(String name) throws IOException {
		if(name.length() < MAX_NAME_LENGTH ) {
			this.name = name;
		} else {
			throw(new IOException("Name too long, exceeds character limit"));
		}
	}
	private void setItem(ItemList item) {
		this.item = item;
	}
	
	public String toString() {
		return this.getName();
	}
}
