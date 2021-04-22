package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.character.Level;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * Holds mob/monster data/actions.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public final class Mob extends Creature {

    private Map<BattleSceneConstants, Double> personality;

    /*
     * Constructor for creating a mob with existing data. All character data
     * must be given to the constructor.
     */
    public Mob(String name, Level level, int x, int y, Inventory inventory, StatBlock statBlock, int hp, int sp, int wp, Map<BattleSceneConstants, Double> personality) {
        super(name, x, y, 'P', TextColor.ANSI.RED, statBlock, inventory, hp, sp, wp, level);
        Random r = new Random();
        this.setPersonality(personality);
    }

    /*
     * Alternate constructor for creating a mob with existing data from a text
     * file. Will be used for boss mob primarily.
     */
    public Mob(String name, char symbol, int level, int x, int y, StatBlock statBlock) {
        super(name, x, y, symbol, TextColor.ANSI.RED, statBlock, new Inventory(), Level.createLvl(level));
        this.personality = new EnumMap<BattleSceneConstants, Double>(BattleSceneConstants.class);
        populatePersonality(0.75, 0.2, 0.05);
    }

    /*
     * Alternate constructor for creating a mob with existing data from a text
     * file.
     */
    public Mob(String name, char symbol, int level, StatBlock statBlock) {
        super(name, 1, 1, symbol, TextColor.ANSI.RED, statBlock, new Inventory(), Level.createLvl(level));
        this.personality = new EnumMap<BattleSceneConstants, Double>(BattleSceneConstants.class);
        populatePersonality(0.75, 0.2, 0.05);
    }

    /*
     * Constructor for creating a new mob for the first time. Takes only a name
     * and sets everything else to default values.
     */
    public Mob(String name) {
        super(name, 81, 10, 'M', TextColor.ANSI.RED);
        this.personality = new EnumMap<BattleSceneConstants, Double>(BattleSceneConstants.class);
        populatePersonality(0.75, 0.2, 0.05);
    }

    /*
     * Constructor for creating a default mob. Takes only name, level and
     * personality values, everything else is default.
     */
    public Mob(String name, int level, double attackVal, double defendVal, double escapeVal) {
        super(name, 81, 10, 'M', TextColor.ANSI.RED);
        setLevel(Level.createLvl(level));
    }

    /*
     * Constructor for creating a new mob for the first time. Takes only a
     * name and level, and sets everything else to default values.
     */
    public Mob(String name, Level level) {
        super(name, 13, 21, 'M', TextColor.ANSI.RED);
        setLevel(level);
        this.personality = new EnumMap<BattleSceneConstants, Double>(BattleSceneConstants.class);
        populatePersonality(0.75, 0.2, 0.05);
    }

    /*
     * Constructor for creating a default mob. Takes only
     * name, level and personality values, everything else is default.
     */
    public Mob(String name, Level level, double attackVal, double defendVal, double escapeVal) {
        super(name, 13, 21, 'M', TextColor.ANSI.RED);
        setLevel(level);
        this.populatePersonality(attackVal, defendVal, escapeVal);
    }

    /*
     * Getter methods
     *
     */
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Map<BattleSceneConstants, Double> getPersonality() {
        return personality;
    }

    public double getPersonalityTrait(BattleSceneConstants trait) {
        return personality.get(trait);
    }


    /*
     * Setter methods
     *
     */
    public void setPersonality(Map<BattleSceneConstants, Double> personality) {
        this.personality = personality;
    }

    /**
     * Utilities
     *
     */
    public void populatePersonality(double a, double d, double e) {
        personality.put(BattleSceneConstants.ATTACK, a);
        personality.put(BattleSceneConstants.DEFEND, d);
        personality.put(BattleSceneConstants.ESCAPE, e);
    }

    public BattleSceneConstants chooseAction() {
        double rangeMin = 0.0f;
        double rangeMax = 1.0f;
        Random r = new Random();
        double result = rangeMin + (rangeMax - rangeMin) * r.nextDouble();

        BattleSceneConstants choice;
        double attack = this.getPersonalityTrait(BattleSceneConstants.ATTACK);
        double defend = this.getPersonalityTrait(BattleSceneConstants.DEFEND) + attack;

        if (result <= attack) {
            choice = BattleSceneConstants.ATTACK;
        } else if (result <= defend) {
            choice = BattleSceneConstants.DEFEND;
        } else {
            choice = BattleSceneConstants.ESCAPE;
        }

        return choice;
    }

}
