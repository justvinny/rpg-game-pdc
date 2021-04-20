package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.Killable;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.items.Armour;
import com.group.pdc_assignment_rpg.logic.items.Weapon;

/**
 * Holds basic character/player data and actions.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public class Player extends Creature implements Killable {

    private String name;
    private int level;
    private int damage, protection; // Temporary. 

    /**
     * Constructor for creating a new player for the first time. Takes only a
     * name and sets everything else to default values.
     */
    public Player(String name) {
        super(5, 23, 'P', TextColor.ANSI.BLUE, new StatBlock(), new Inventory());
        setName(name);
        setLevel(1);
        this.damage = 5;
        this.protection = 1;
    }

    /**
     * Constructor for creating a player with existing data. All character data
     * must be given to the constructor.
     */
    public Player(String name, int level, Inventory inventory, int x, int y, StatBlock statBlock) {
        super(x, y, 'P', TextColor.ANSI.BLUE, statBlock, inventory);
        this.setName(name);
        this.setLevel(level);
        this.damage = 5;
        this.protection = 1;
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
    
    public int getDamage() {
        Weapon weapon = (Weapon) super.getInventory().getEquipment().get(EquipmentSlot.HAND);
        int weaponDamage = (weapon == null) ? 0 : weapon.getDamage();
        return damage + weaponDamage;
    }
    
    public int getProtection() {
        Armour armour = (Armour) super.getInventory().getEquipment().get(EquipmentSlot.ARMOUR);
        int armourProtection = (armour == null) ? 0 : armour.getProtection();
        return protection + armourProtection;
    }

    /**
     * Setter methods
     *
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot set name to null.");
        } else {
            this.name = name;
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

    public void levelUp() {
        level++;
        damage += 2;
        protection++;
    }
    @Override
    public String toString() {
        String str = "Name: " + getName() + " (Lvl: " + getLevel() + ")\n";
        return str += super.toString();
    }
}
