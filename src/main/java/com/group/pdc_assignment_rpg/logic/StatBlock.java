package com.group.pdc_assignment_rpg.logic;

import java.util.*;

/**
 * Contains all the necessary stats for a player in the game.
 *
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class StatBlock {

    private Map<Stats, Integer> stats;
    private Map<CStats, Integer> consumables;

    /**
     * Stat values unknown, populates with base stats Consumable stats are
     * calculated based on statblock.
     */
    public StatBlock() {
        this.setStats(new EnumMap<Stats, Integer>(Stats.class));
        this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.populate(5, 5, 5);
        this.populateConsumables();
    }

    /**
     * Stat values known, populate with known stats
     *
     * @param strength - Strength
     * @param dexterity - Dexterity
     * @param intellect - Intellect
     */
    public StatBlock(int strength, int dexterity, int intellect) {
        this.stats = new EnumMap<Stats, Integer>(Stats.class);
        this.consumables = new EnumMap<CStats, Integer>(CStats.class);
        this.populate(strength, dexterity, intellect);
        this.populateConsumables();
    }

    /**
     * Getters
     *
     */
    public Map<Stats, Integer> getStats() {
        return stats;
    }

    public int getStrength() {
        return stats.get(Stats.STRENGTH);
    }

    public int getDexterity() {
        return stats.get(Stats.DEXTERITY);
    }

    public int getIntellect() {
        return stats.get(Stats.INTELLECT);
    }

    public int getValue(Stats stat) {
        return this.stats.get(stat);
    }

    public Map<CStats, Integer> getConsumables() {
        return consumables;
    }

    public int getValue(CStats stat) {
        return this.consumables.get(stat);
    }

    /**
     * Setters
     *
     */
    public void setStats(Map<Stats, Integer> stats) {
        this.stats = stats;
    }

    public void writeStat(Stats s, int v) {
        this.stats.put(s, v);
    }

    public void setConsumables(Map<CStats, Integer> consumables) {
        this.consumables = consumables;
    }

    public void writeStat(CStats s, int v) {
        this.consumables.put(s, v);
    }

    /**
     * Utilities
     *
     */
    public void populate(int s, int d, int i) {
        this.writeStat(Stats.STRENGTH, s);
        this.writeStat(Stats.DEXTERITY, d);
        this.writeStat(Stats.INTELLECT, i);
    }

    public void populateConsumables() {
        this.writeStat(CStats.HEALTH, calculateHealth());
        this.writeStat(CStats.STAMINA, calculateStamina());
        this.writeStat(CStats.WILL, calculateWill());
    }

    /**
     * Game Logic
     *
     */
    /**
     * Vitality, base of Health, buffer of Will Increases by 1 for every point
     * in Strength above 5, with a base value of 1
     *
     * @return creature's vitality score
     */
    public int getVitality() {
        return (1 + (this.getValue(Stats.STRENGTH) - 5));
    }

    /**
     * Endurance, base of Stamina, buffer of Health. Increases by 1 for every
     * point in Dexterity above 5, with a base value of 1
     *
     * @return
     */
    public int getEndurance() {
        return (1 + (this.getValue(Stats.DEXTERITY) - 5));
    }

    /**
     * Endurance, base of Will, buffer of Stamina. Increases by 1 for every
     * point in Intellect above 5, with a base value of 1
     *
     * @return
     */
    public int getWillpower() {
        return (1 + (this.getValue(Stats.INTELLECT) - 5));
    }

    /**
     * Health, sets maximum hitpoints for a creature. Based on 5 + (Vitality x
     * 10) + (Endurance x 5), minimum of 20.
     *
     * @return
     */
    public int calculateHealth() {
        return (5 + (this.getVitality() * 10) + (this.getEndurance() * 5));
    }

    /**
     * Stamina, sets maximum exertion for a creature. Based on 5 + (Endurance x
     * 10) + (Willpower x 5), minimum of 20.
     *
     * @return
     */
    public int calculateStamina() {
        return (5 + (this.getEndurance() * 10) + (this.getWillpower() * 5));
    }

    /**
     * Will, sets maximum force of will for a creature. Based on 5 + (Willpower
     * x 10) + (Vitality x 5), minimum of 20.
     *
     * @return
     */
    public int calculateWill() {
        return (5 + (this.getWillpower() * 10) + (this.getVitality() * 5));
    }
}
