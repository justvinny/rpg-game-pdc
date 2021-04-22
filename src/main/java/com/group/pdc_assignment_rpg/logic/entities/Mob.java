package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import java.lang.*;

/**
 * Holds data and information of a mob in the game. Based on player design from
 * Macauley / Jess. Stat Block is stored in the Creature object.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Mob extends Creature {

    private int level;

    /**
     * Constructor for creating a mob with existing data. All character data
     * must be given to the constructor.
     */
    public Mob(String name, int level, int x, int y, Inventory inventory, StatBlock statBlock, int hp, int sp, int wp) {
        super(name, x, y, 'P', TextColor.ANSI.RED, statBlock, inventory, hp, sp, wp);
        setLevel(level);
    }

    /**
     * Constructor for creating a new mob for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Mob(String name) {
        super(name, 13, 21, 'M', TextColor.ANSI.RED);
        setLevel(1);
    }
    
    /**
     * Constructor for creating a default mob. Takes only
     * name and level, everything else is default.
     */
    public Mob(String name, int level) {
    	super(name, 13, 21, 'M', TextColor.ANSI.RED);
    	setLevel(level);
    }
    
    public int getLevel() {
            return level;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Setter methods
     *
     */
    public void setLevel(int level) throws IllegalArgumentException {
        if (level >= 1) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be greater than or equal to 1.");
        }
    }

}
