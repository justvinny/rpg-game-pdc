package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.Killable;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.Stats;
import com.group.pdc_assignment_rpg.logic.character.*;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;

/**
 * Holds basic character/player data and actions.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 */
public final class Player extends Creature implements Killable {
    
    private static Player currentPlayer = null;
    private int nSteps;

    /*
     * Constructor for creating a new player for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Player(String name) {
        super(name, 5, 23, 'P', TextColor.ANSI.BLUE, new StatBlock(), new Inventory(), Level.L1);
        this.setXP(0);
        this.loadDefaultEquipment();
        nSteps = 0;
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
        nSteps = 0;
    }

    /*
     * Getters
     */
    @Override
    public int getXP() {
        return xp;
    }

    @Override
    public void addXP(int xp) {
        super.addXP(xp);

        if (getXP() >= getLevel().getThreshold()) {
            levelUp();
        }
    }
    
    public int getSteps() {
        return nSteps;
    }

    /**
     * String will be used in the user inventory to display detailed player
     * information.
     *
     * @return player information string.
     */
    public String getPlayerInformation() {
        Item weapon = getInventory().getEquipment().get(EquipmentSlot.HAND);
        Item armour = getInventory().getEquipment().get(EquipmentSlot.ARMOUR);
        String weaponString = (weapon == null) ? "None" : weapon.toString();
        String armourString = (armour == null) ? "None" : armour.toString();
        String playerStats = String.format("Name: %s\nLevel: %d\nExperience: %d / %d\n"
                + "HP: %d / %d\nDamage: %d\nProtection: %d\nVitality: %d\n"
                + "Endurance: %d\nWillpower: %d\n\nWeapon: %s\nArmour: %s",
                getName(),
                getLevel().getLvl(),
                getXP(),
                getLevel().getThreshold(),
                getHP(),
                getMaxHP(),
                getDamage(),
                getProtection(),
                getStats().getVitality(),
                getStats().getEndurance(),
                getStats().getWillpower(),
                weaponString,
                armourString);

        return playerStats;
    }

    /**
     * Utility methods
     *
     */
    public void increaseStep() {
        nSteps++;
    }
    
    public void resetSteps() {
        nSteps = 0;
    }
    
    public void savePlayer() {
        ResourceLoaderUtility.writePlayerData(this);
    }

    public void resetCoordinates() {
        getCoordinates().setCoordinates(5, 23);
    }
    
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
                ResourceLoaderUtility.createItem("Broken Sword"),
                ResourceLoaderUtility.createItem("Tattered Clothing"),
                ResourceLoaderUtility.createItem("Potion of Healing"));

        // Equip items.
        getInventory().getEquipment().put(EquipmentSlot.HAND, getInventory().getItem("Broken Sword"));
        getInventory().getEquipment().put(EquipmentSlot.ARMOUR, getInventory().getItem("Tattered Clothing"));
    }

    /**
     * Method to convert object into comma separated data to store in text/CSV
     * files.
     *
     * @return a comma separated string representing our player.
     */
    public String toCommaSeparatedString() {
        return String.format("%s,%s,%d,%d,%d,%,d,%d",
                getName(), getLevel(), getX(), getY(), getStats().getStrength(),
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
    
    
    public static void setPlayerInstance(Player player) {
        if (currentPlayer == null) {
            currentPlayer = player;
        }
    }
    
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

}
