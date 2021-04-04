package com.group.pdc_assignment_rpg.logic;

import com.googlecode.lanterna.TextColor;

/**
 * Holds data and information of a mob in the game. Based on player design from
 * Macauley / Jess.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Mob extends Creature {

    private String name;
    private int level;

    /**
     * Constructor for creating a player with existing data. All character data
     * must be given to the constructor.
     */
    public Mob(String name, int level, Inventory inventory, int x, int y, StatBlock statBlock, int hp) {
        super(x, y, 'P', TextColor.ANSI.RED, statBlock, inventory, hp);
        setName(name);
        setLevel(level);
    }

    /**
     * Constructor for creating a new player for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Mob(String name) {
        super(13, 21, 'M', TextColor.ANSI.RED, new StatBlock(), new Inventory());
        setName(name);
        setLevel(1);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
}
