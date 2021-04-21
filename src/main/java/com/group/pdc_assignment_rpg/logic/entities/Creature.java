package com.group.pdc_assignment_rpg.logic.entities;

import java.util.*;
import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.*;
import com.group.pdc_assignment_rpg.logic.items.*;

/**
 * Creatures are a physical thing on the map with a stat block, allowing them to
 * participate in combat and other actions. The bartracking variable is used to
 * keep track of consumable Stats' current values. The maximum of these stats is
 * stored in the StatBlock stats variable.
 *
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com> 
 */
public abstract class Creature extends Entity {

    protected StatBlock stats;
    private Map<CStats, Integer> consumables;
    private Inventory inventory;

    /**
     * Constructor for creating a creature with all values known.
     */
    public Creature(int x, int y, char s, TextColor c, StatBlock stats, Inventory inventory, int hp, int sp, int wp) {
        super(x, y, s, c);
		this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.setStats(stats);
        this.setInventory(inventory);
        this.setHP(hp);
        this.setSP(sp);
        this.setWP(wp);
    }


	/**
     * Constructor for creating a creature with default (full) hit-points.
     */
    public Creature(int x, int y, char s, TextColor c, StatBlock stats, Inventory inventory) {
        super(x, y, s, c);
		this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.setStats(stats);
        this.setInventory(inventory);
        this.setHP(getMaxHP());
        this.setSP(getMaxSP());
        this.setWP(getMaxWP());
    }
    
    /**
     * Constructor for creating a creature with a default state (default stats, empty inventory).
     */
    public Creature(int x, int y, char s, TextColor c) {
    	super(x, y, s, c);
		this.consumables = new EnumMap<CStats, Integer>(CStats.class);
    	this.setStats(new StatBlock());
    	this.setInventory(new Inventory());
    	this.setHP(getMaxHP());
    	this.setSP(getMaxSP());
    	this.setWP(getMaxWP());
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
    public int getHP() {
    	return this.getConsumables().get(CStats.HEALTH);
    }
    public int getSP() {
    	return this.getConsumables().get(CStats.STAMINA);
    }
    public int getWP() {
    	return this.getConsumables().get(CStats.WILL);
    }
    public int getMaxHP() {
        return this.getStats().getValue(CStats.HEALTH);
    }    
    public int getMaxSP() {
		return this.getStats().getValue(CStats.STAMINA);
    }
    public int getMaxWP() {
            return this.getStats().getValue(CStats.WILL);
    }
    public Map<CStats, Integer> getConsumables() {
            return consumables;
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
    public void setHP(int hp) {
        if (hp <= this.getMaxHP() && hp >= 0) {
            this.consumables.put(CStats.HEALTH, hp);
        } else if (hp > this.getMaxHP()) {
            this.consumables.put(CStats.HEALTH, this.getMaxHP());
        }
    }
    private void setSP(int sp) {
    	if (sp <= this.getMaxSP() && sp >= 0) {
    		this.consumables.put(CStats.STAMINA, sp);
    	} else if (sp > this.getMaxSP()) {
            this.consumables.put(CStats.STAMINA, this.getMaxSP());
        }
    }
    private void setWP(int wp) {
    	if (wp <= this.getMaxWP() && wp >= 0) {
            this.consumables.put(CStats.WILL, wp);
        } else if (wp > this.getMaxWP()) {
            this.consumables.put(CStats.WILL, this.getMaxWP());
        }
	}
    public void setConsumables(Map<CStats, Integer> consumables) {
    	this.consumables = consumables;
    }

    
    /**
     * Utility methods
     *
     */
    
    /**
     * Causes a creature to take a specified amount of damage to their HP.
     *
     * @param amount is the amount of damage.
     */
    public void damage(int amount) throws IllegalArgumentException {
        // Validate that damage amount is positive
        if (amount > 0) {
            // Check if the damage will cause the creature to hit 0 hit points or below
            if (getHP() - amount >= 0) {
                setHP(getHP() - amount);
            } else {
                setHP(0);
            }
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Damage amount must be greater than 0.");
        }
    }

    /**
     * Causes a creature to heal a specified amount of HP.
     *
     * @param amount is the amount of HP to heal.
     */
    public void heal(int amount) throws IllegalArgumentException {
        // Validate that heal amount is positive and the creature isn't already on their maxHP
        if (amount > 0 && getHP() < getMaxHP()) {
            // Check if the heal amount will cause hp to exceed maxHP
            if (getHP() + amount >= getMaxHP()) {
                setHP(getMaxHP());
            } else {
                setHP(getHP() + amount);
            }
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Heal amount must be greater than 0.");
        } else if (getHP() >= getMaxHP()) {
            throw new IllegalArgumentException("Cannot heal when max hit points already reached.");
        }
    }

    @Override
    public String toString() {
        String str = "HP: (" + getHP() + "/" + getMaxHP() + ")\n";
        str += "Coords: (" + getX() + ", " + getY() + ")\n";
        return str += super.toString();
    }

	

}
