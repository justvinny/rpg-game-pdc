package com.group.pdc_assignment_rpg.logic;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.model.Inventory;

/**
 * Creatures are a physical thing on the map with a stat block, allowing
 * them to participate in combat and other actions.
 * 
 * @author Macauley Cunningham - 19072621
 */
public abstract class Creature extends Entity {

	private StatBlock stats;
	private Inventory inventory;
	
	public Creature(int x, int y, char s, TextColor c, StatBlock stats) {
		super(x, y, s, c);
		this.setStats(stats);
	}

	/**
	 * Getter methods
	 * 
	 */
	public StatBlock getStats() {
		return stats;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Setter methods
	 * 
	 */
	public void setStats(StatBlock stats) {
		this.stats = stats;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Utility methods
	 * 
	 */
	
	public abstract void damage();
	
	public abstract void heal();

	
}
