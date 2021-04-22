package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.Killable;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;

/**
 * Holds basic character/player data and actions.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public class Player extends Creature implements Killable {

    /**
     * Constructor for creating a new player for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Player(String name) {
        super(name, 5, 23, 'P', TextColor.ANSI.BLUE, new StatBlock(), new Inventory(), 1);
    }

    /**
     * Constructor for creating a player with existing data. All character data
     * must be given to the constructor.
     */
    public Player(String name, int level, Inventory inventory, int x, int y, StatBlock statBlock) {
        super(name, x, y, 'P', TextColor.ANSI.BLUE, statBlock, inventory, level);
    }

    /**
     * Utility methods
     *
     */
    public void die() {
        // TODO: Add stuff here
    }

    public void levelUp() {
        setLevel(getLevel() + 1);
    }
    
    @Override
    public boolean equals(Object obj) {
        return getName().equals(((Player) obj).getName());
    }

    
    @Override
    public String toString() {
        String str = "Name: " + getName() + " (Lvl: " + getLevel() + ")\n";
        return str += super.toString();
    }

    /**
     * Method to convert object into comma separated data to store in text/CSV
     * files.
     * @return a comma separated string representing our player. 
     */
    public String toCommaSeparatedString() {
        return String.format("%s,%d,%d,%d,%d,%,d,%d",
                getName(), getLevel(), x, y, getStats().getStrength(),
                getStats().getDexterity(), getStats().getIntellect());
    }
    
    /**
     * Alternate way to initialise a player. 
     * This is particularly used for loading player data from the database.
     * @param playerName player name used to query the db.
     * @return a player object based on the data loaded.
     */
    public static Player loadPlayerFactory(String playerName) {
        return ResourceLoaderUtility.loadPlayerFromDB(playerName);
    }
}
