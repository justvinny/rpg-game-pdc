package com.group.pdc_assignment_rpg.logic;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.Inventory;

/**
 * Creatures are a physical thing on the map with a stat block, allowing them to
 * participate in combat and other actions.
 *
 * @author Macauley Cunningham - 19072621
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public abstract class Creature extends Entity {

    private StatBlock stats;
    private Inventory inventory;
    private int maxHP;
    private int hp;

    /**
     * Constructor for creating a creature with all values known.
     */
    public Creature(int x, int y, char s, TextColor c, StatBlock stats, Inventory inventory, int hp) {
        super(x, y, s, c);
        this.setStats(stats);
        this.inventory = inventory;
        this.maxHP = 20;
        this.setHP(hp);
    }

    /**
     * Constructor for creating a creature with default (full) hit-points.
     */
    public Creature(int x, int y, char s, TextColor c, StatBlock stats, Inventory inventory) {
        super(x, y, s, c);
        this.setStats(stats);
        this.inventory = inventory;
        this.maxHP = 20;
        this.setHP(getMaxHP());
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

    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return hp;
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
        if (hp <= getMaxHP() && hp >= 0) {
            this.hp = hp;
        }
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
        str += "Coords: (" + getX() + ", " + getY() + ")";
        return super.toString();
    }
}
