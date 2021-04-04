package com.group.pdc_assignment_rpg.logic;

import com.googlecode.lanterna.TextColor;

/**
 * Holds basic character/player data and actions. 
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public class Player extends Creature implements Killable {

    private String name;
    private int level;
    
    /**
     * Constructor for creating a new player for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Player(String name) {
        super(5, 23, 'P', TextColor.ANSI.RED, new StatBlock(), new Inventory());
        setName(name);
        setLevel(1);
    }

    /**
     * Constructor for creating a player with existing data. All character data must be given to the constructor.
     */
    public Player(String name, int level, Inventory inventory, int x, int y, StatBlock statBlock, int hp) {
        super(x, y, 'P', TextColor.ANSI.RED, statBlock, inventory, hp);
        this.name = name;
        this.level = level;
    }
    
    /**
     * Getter methods
     * 
     */
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
    
    /**
     * Setter methods
     * 
     */
    public void setName(String name) throws IllegalArgumentException {
        if (!name.isBlank()) {
          this.name = name;  
        } else {
            throw new IllegalArgumentException("Cannot set name to null.");
        }
    }

    public void setLevel(int level) throws IllegalArgumentException {
        if (level >= 1) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be greater than or equal to 1.");
        }
    }
    
    /**
     * Utility methods
     * 
     */
    public void die() {
        // TODO: Add stuff here
    }

    @Override
    public String toString() {
        String str = "Name: " + getName() + " (Lvl: " + getLevel() + ")\n";
        str += "HP: (" + getHP() + "/" + getMaxHP() +")\n";
        str += "Coords: (" + getX() + ", " + getY() + ")";
        return super.toString();
    }
}