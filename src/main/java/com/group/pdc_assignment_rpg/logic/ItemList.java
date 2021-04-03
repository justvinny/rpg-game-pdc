package com.group.pdc_assignment_rpg.logic;

import static com.group.pdc_assignment_rpg.logic.ItemType.*;

public enum ItemList {

	COIN(0, 1, NORMAL), 
	SWORD(0, 50, EQUIP), 
	HANDAXE(0, 30, EQUIP), 
	SPEAR(0, 40, EQUIP), 
	ARMOUR(0, 100, EQUIP), 	
	RED_POTION(0, 25, CONSUME);
	
	private int weight;
	private int value;
	private ItemType type;
	
	private ItemList(int w, int v, ItemType t) {
		this.setWeight(w);
		this.setValue(v);
		this.setType(t);
	}
	
	/**
	 * Utility methods
	 * 
	 */
	public Item initialiseItem() {
		Item item = null;
		
		return item;
	}
	
	
	
	/**
	 * Getters
	 * 
	 */
	public ItemType getType() {
		return type;
	}
	public int getValue() {
		return value;
	}
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Setters
	 * 
	 */
	private void setType(ItemType type) {
		this.type = type;
	}
	private void setValue(int value) {
		this.value = value;
	}
	private void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	
}