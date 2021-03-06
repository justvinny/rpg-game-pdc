package com.group.pdc_assignment_rpg.logic.entities;

import java.util.*;
import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.CStats;
import com.group.pdc_assignment_rpg.logic.Killable;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.Stats;
import com.group.pdc_assignment_rpg.logic.items.Armour;
import com.group.pdc_assignment_rpg.logic.items.Weapon;
import com.group.pdc_assignment_rpg.logic.character.Level;

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
@SuppressWarnings("OverridableMethodCallInConstructor")
public abstract class Creature extends Entity implements Killable {

    private StatBlock stats;
    private String name;
    private Map<CStats, Integer> consumables;
    private Inventory inventory;
    private boolean defending, alive;
    private int damage, protection;
    private Level level;
    protected int xp;

    /*
     * Constructor for creating a creature with all values known.
     */
    public Creature(String name, int x, int y, char s, TextColor c, StatBlock stats, Inventory inventory, int hp, int sp, int wp, Level level) {
        super(x, y, s, c);
        this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.setName(name);
        this.setStats(stats);
        this.setInventory(inventory);
        this.setHP(hp);
        this.setSP(sp);
        this.setWP(wp);
        this.setDefending(false);
        this.setLevel(level);
        alive = true;
    }

    /*
     * Constructor for creating a creature with default (full) hit-points.
     */
    public Creature(String name, int x, int y, char s, TextColor c, StatBlock stats, Inventory inventory, Level level) {
        super(x, y, s, c);
        this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.setName(name);
        this.setStats(stats);
        this.setInventory(inventory);
        this.setHP(getMaxHP());
        this.setSP(getMaxSP());
        this.setWP(getMaxWP());
        this.setLevel(level);
        alive = true;
    }
    
    /*
     * Constructor for creating a creature with a default state (default stats,
     * empty inventory).
     */
    public Creature(String name, int x, int y, char s, TextColor c) {
        super(x, y, s, c);
        this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.setName(name);
        this.setStats(new StatBlock());
        this.setInventory(new Inventory());
        this.setHP(getMaxHP());
        this.setSP(getMaxSP());
        this.setWP(getMaxWP());
        this.setLevel(Level.L1);
        alive = true;
    }

    /*
     * Getter methods
     *
     */
    public String getName() {
        return name;
    }

    public StatBlock getStats() {
        return stats;
    }

    public int getStat(Stats stat) {
        return getStats().getValue(stat);
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

    public boolean isDefending() {
        return this.defending;
    }

    public boolean isAlive() {
        return alive;
    }
    
    public Level getLevel() {
        return level;
    }

    public int getDamage() {
        Weapon weapon = (Weapon) inventory.getEquipment().get(EquipmentSlot.HAND);
        int weaponDamage = (weapon == null) ? 0 : weapon.getDamage();
        return damage + weaponDamage;
    }

    public int getProtection() {
        Armour armour = (Armour) inventory.getEquipment().get(EquipmentSlot.ARMOUR);
        int armourProtection = (armour == null) ? 0 : armour.getProtection();
        return protection + armourProtection;
    }

    public int getXP() {
        int xp = 4 * ((int) Math.round(this.getLevel().getLvl() * 1.8));

        return xp;
    }

    public boolean isWeaponEquipped() {
        return inventory.getEquipment().get(EquipmentSlot.HAND) != null;
    }

    public boolean isArmourEquipped() {
        return inventory.getEquipment().get(EquipmentSlot.HAND) != null;
    }

    public String getWeaponName() {
        return (isWeaponEquipped()) ? inventory.getEquipment().get(EquipmentSlot.HAND).getName() : "None";
    }

    public String getArmourName() {
        return (isArmourEquipped()) ? inventory.getEquipment().get(EquipmentSlot.ARMOUR).getName() : "None";
    }
    
    public String getBattleStatusText() {
        return " " + name + " HP: " + getHP() + " / " + getMaxHP();
    }

    /*
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
        } else if (hp < 0) {
            this.consumables.put(CStats.HEALTH, 0);
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

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    public void setLevel(Level level) throws IllegalArgumentException {
        if (level.ordinal() >= 0) {
            this.level = level;
            setStats();
        } else {
            throw new IllegalArgumentException("Level must be greater than or equal to 1.");
        }
    }

    public void setStats() {
        damage = (this.getStat(Stats.STRENGTH) + (this.getLevel().getLvl() - 1) * 2);
        protection = level.getLvl();
    }
    
    /*
     * Setters
     */
    public void setXP(int xp) {
        this.xp = xp;
    }

    public void addXP(int xp) {
        this.xp += xp;
    }

    @Override
    public void kill() {
        alive = false;
    }
    
    /**
     * Utility methods
     *
     */
    /**
     * Causes a creature to take a specified amount of damage to their HP.
     *
     * @param amount is the amount of damage.
     * @return whether damage is valid or not.
     */
    public boolean damage(int amount) throws IllegalArgumentException {        
        // Validate that damage amount is positive
        if (amount > 0) {
            // Check if the damage will cause the creature to hit 0 hit points or below
            if (getHP() - amount > 0) {
                setHP(getHP() - amount);
            } else {
                setHP(0);
                return true;
            }
        }  else {
            throw new IllegalArgumentException("Damage cannot be less than 0.");
        }
        return false;
    }

    /**
     * Causes a creature to heal a specified amount of HP.
     *
     * @param amount is the amount of HP to heal.
     */
    public void heal(int amount) throws IllegalArgumentException {
        if (getHP() == getMaxHP()) {
            throw new IllegalArgumentException("Already full HP.");
        }
        
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
        }
    }

    @Override
    public String toString() {
        String str = "HP: (" + getHP() + "/" + getMaxHP() + ")\n";
        str += "Coords: (" + getX() + ", " + getY() + ")\n";
        return str += super.toString();
    }

}
