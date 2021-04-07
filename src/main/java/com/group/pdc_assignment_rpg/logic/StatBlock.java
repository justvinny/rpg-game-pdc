package com.group.pdc_assignment_rpg.logic;

import java.util.*;

public class StatBlock {

	private Map<Stats, Integer> stats;
	private Map<CStats, Integer> consumables;
	
	/**
	 * Stat values unknown, populates with base stats
	 * Consumable stats are calculated based on statblock.
	 */
	public StatBlock() {
		this.setStats(new EnumMap<Stats, Integer>(Stats.class));
		this.populate(5, 5, 5);
		this.populateConsumables(
				this.calculateHealth(),
				this.calculateStamina(), 
				this.calculateWill());
	}
	
	/**
	 * Stat values known, populate with known stats
	 * @param s - Strength
	 * @param d - Dexterity
	 * @param i - Intellect
	 */
	public StatBlock(int s, int d, int i) {
		this.stats = new EnumMap<Stats, Integer>(Stats.class);
		this.populate(s, d, i);
		this.populateConsumables(
				this.calculateHealth(),
				this.calculateStamina(), 
				this.calculateWill());
	}

	/**
	 * Getters
	 * 
	 */
	public Map<Stats, Integer> getStats() {
		return stats;
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
	
	public void populateConsumables(int s, int d, int i) {
		this.writeStat(CStats.HEALTH, s);
		this.writeStat(CStats.STAMINA, d);
		this.writeStat(CStats.WILL, i);
	}
	
	/**
	 * Game Logic
	 * 
	 */
	
	/**
	 * Vitality, base of Health, buffer of Will
	 * Increases by 1 for every point in Strength above 5, with a base value of 1
	 * @return creature's vitality score
	 */
	public int getVitality() {
		return (1 + (this.getValue(Stats.STRENGTH) - 5));
	}
	
	/**
	 * Endurance, base of Stamina, buffer of Health.
	 * Increases by 1 for every point in Dexterity above 5, with a base value of 1
	 * @return
	 */
	public int getEndurance() {
		return (1 + (this.getValue(Stats.DEXTERITY) - 5));
	}
	
	/**
	 * Endurance, base of Will, buffer of Stamina.
	 * Increases by 1 for every point in Intellect above 5, with a base value of 1
	 * @return
	 */
	public int getWillpower() {
		return (1 + (this.getValue(Stats.INTELLECT) - 5));
	}
	
	/** 
	 * Health, sets maximum hitpoints for a creature.
	 * Based on 5 + (Vitality x 10) + (Endurance x 5), minimum of 20.
	 * @return
	 */
	public int calculateHealth() {
		return (5 + (this.getVitality() * 10) + (this.getEndurance() * 5));
	}

	/** 
	 * Stamina, sets maximum exertion for a creature.
	 * Based on 5 + (Endurance x 10) + (Willpower x 5), minimum of 20.
	 * @return
	 */
	public int calculateStamina() {
		return (5 + (this.getEndurance() * 10) + (this.getWillpower() * 5));
	}

	/** 
	 * Will, sets maximum force of will for a creature.
	 * Based on 5 + (Willpower x 10) + (Vitality x 5), minimum of 20.
	 * @return
	 */
	public int calculateWill() {
		return (5 + (this.getWillpower() * 10) + (this.getVitality() * 5));
	}
}
