package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.Killable;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.Stats;
import com.group.pdc_assignment_rpg.logic.character.*;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;

/**
 * Holds basic character/player data and actions.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 */
public final class Player extends Creature implements Killable {

    /*
     * Constructor for creating a new player for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Player(String name) {
        super(name, 5, 23, 'P', TextColor.ANSI.BLUE, new StatBlock(), new Inventory(), Level.L1);
        this.setXP(0);
        this.loadDefaultEquipment();
    }

    /*
     * Constructor for creating a player with existing data. All character data
     * must be given to the constructor. XP is set to minimum for player's
     * current level.
     */
    public Player(String name, Level level, Inventory inventory, int x, int y, StatBlock statBlock) {
        super(name, x, y, 'P', TextColor.ANSI.BLUE, statBlock, inventory, level);
        if (level != Level.L1) {
            this.setXP(level.previous().getThreshold());
        } else {
            this.setXP(0);
        }
    }

    /*
     * Getters
     */
    @Override
    public int getXP() {
        if (xp >= getLevel().getThreshold()) {
            levelUp();
        }
        
        return xp;
    }


    /**
     * Utility methods
     *
     */
    @Override
    public void die() {
        // TODO: Add stuff here
    }

    public void levelUp() {
        setLevel(getLevel().next());
        incrementStats();
        setHP(getMaxHP()); // Heal player for level up.
    }

    @Override
    public boolean equals(Object obj) {
        return getName().equals(((Player) obj).getName());
    }

    public void incrementStats() {
        this.getStats().writeStat(Stats.STRENGTH, this.getStats().getValue(Stats.STRENGTH) + 1);
        this.getStats().writeStat(Stats.DEXTERITY, this.getStats().getValue(Stats.DEXTERITY) + 1);
        this.getStats().writeStat(Stats.INTELLECT, this.getStats().getValue(Stats.INTELLECT) + 1);
        this.getStats().populateConsumables();
    }

    @Override
    public String toString() {
        String str = "Name: " + getName() + " (Lvl: " + getLevel() + ")\n";
        return str += super.toString();
    }

    private void loadDefaultEquipment() {
        // Add default items for new character.
        getInventory().addMultiple(
                ResourceLoaderUtility.itemLoaderFactory("Broken Sword"),
                ResourceLoaderUtility.itemLoaderFactory("Tattered Clothing"),
                ResourceLoaderUtility.itemLoaderFactory("Potion of Healing"));
    }

    /**
     * Method to convert object into comma separated data to store in text/CSV
     * files.
     *
     * @return a comma separated string representing our player.
     */
    public String toCommaSeparatedString() {
        return String.format("%s,%s,%d,%d,%d,%,d,%d",
                getName(), getLevel(), x, y, getStats().getStrength(),
                getStats().getDexterity(), getStats().getIntellect());
    }

    /**
     * Alternate way to initialise a player. This is particularly used for
     * loading player data from the database.
     *
     * @param playerName player name used to query the db.
     * @return a player object based on the data loaded.
     */
    public static Player loadPlayerFactory(String playerName) {
        return ResourceLoaderUtility.loadPlayerFromDB(playerName);
    }

}
