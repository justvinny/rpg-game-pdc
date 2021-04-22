package com.group.pdc_assignment_rpg.logic;

import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import java.util.Random;
import com.group.pdc_assignment_rpg.logic.entities.*;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls combat mechanics and options.
 *
 * @author main Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 * @author modified Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Combat {

    final float DEFENSEBONUS = 1.5f;

    private Player player;
    private Mob mob;
    private Combatant currentTurn;
    private boolean fighting;
    private List<String> battleLog;

    public Combat(Player player, Mob mob) {
        this.player = player;
        this.mob = mob;
        this.fighting = true;
        this.battleLog = new ArrayList<>();

        setFirstTurn();
    }

    /*
     * Getters
     * 
     */
    public boolean isFighting() {
        return fighting;
    }

    public List<String> getLog() {
        return battleLog;
    }

    /*
     * Setters
     *
     */
    public void setCurrentTurn(Combatant currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * Utilities
     *
     */
    private int roll(int dice) {
        Random random = new Random();
        int result = random.nextInt(dice) + 1;
        return result;
    }

    private boolean statCompete(Stats stat, Creature innitiator, Creature target) {
        int innitiatorCheck = innitiator.getStat(stat) + roll(20);
        int targetCheck = target.getStat(stat) + roll(20);

        return innitiatorCheck > targetCheck;
    }

    private boolean statCompete(Stats innitiatorStat, Stats targetStat, Creature innitiator, Creature target) {
        int innitiatorCheck = innitiator.getStat(innitiatorStat) + roll(20);
        int targetCheck = target.getStat(targetStat) + roll(20);

        return innitiatorCheck > targetCheck;
    }

    private boolean statCompete(Stats innitiatorStat, Stats targetStat, Creature innitiator, Creature target, float targetBonus) {
        int innitiatorCheck = innitiator.getStat(innitiatorStat) + roll(20);
        int targetCheck = target.getStat(targetStat) + roll(20);

        if (targetBonus > 0) {
            targetCheck = (int) (targetCheck * targetBonus);
        }

        return innitiatorCheck > targetCheck;
    }

    public void setFirstTurn() {
        if (statCompete(Stats.DEXTERITY, player, mob)) {
            currentTurn = Combatant.PLAYER;
            battleLog.add(player.getName() + " leaps into action, ready to make a move!");
        } else {
            currentTurn = Combatant.ENEMY;
            battleLog.add(mob.getName() + " catches " + player.getName() + " off guard and makes the first move!");
        }
    }

    public boolean runAction(BattleSceneConstants action, Creature innitiator, Creature target) throws IllegalArgumentException {
        boolean keepRunning = true;

        switch (action) {
            case ATTACK:
                keepRunning = attack(innitiator, target);
                break;
            case DEFEND:
                defend(innitiator);
                break;
            case ESCAPE:
                keepRunning = escape(innitiator, target);
                break;
            default:
                throw new IllegalArgumentException("Invalid action given.");
        }

        return keepRunning;
    }

    /**
     * Actions
     *
     */
    public boolean attack(Creature initiator, Creature target) {
        if (target.isDefending()) {
            boolean attackSuccess = statCompete(Stats.STRENGTH, Stats.DEXTERITY, initiator, target, DEFENSEBONUS);
        }

        boolean attackSuccess = statCompete(Stats.STRENGTH, Stats.DEXTERITY, initiator, target);

        if (attackSuccess) {
            if (target.isDefending()) {
                battleLog.add(target.getName() + "'s defence failed!");
            }

            int damage = roll(5) + initiator.getDamage() - target.getProtection();
            battleLog.add(initiator.getName() + " managed to hit " + target.getName()
                    + " for " + damage + " damage.");
            boolean downed = target.damage(damage);

            if (downed) {
                battleLog.add(target.getName() + " was defeated by " + initiator.getName() + "!");
                target.setDefending(false);
                return false;
            }
        } else {
            if (target.isDefending()) {
                battleLog.add(initiator.getName() + "'s attack was thwarted by " + target.getName() + "'s defense!");
            } else {
                battleLog.add(initiator.getName() + " tried to attack but missed.");
            }
        }

        target.setDefending(false);
        return true;
    }

    public void defend(Creature innitiator) {
        innitiator.setDefending(true);
        battleLog.add(innitiator.getName() + " prepares a defense.");
    }

    public boolean escape(Creature innitiator, Creature target) {
        boolean escapeSuccess = statCompete(Stats.DEXTERITY, innitiator, target);

        if (escapeSuccess) {
            battleLog.add(innitiator.getName() + " fled the battle!");
            return false;
        } else {
            battleLog.add(innitiator.getName() + " made an attempt to flee, but was stopped by " + target.getName());
        }
        return true;
    }

    public void battle(BattleSceneConstants choice) {
        switch (currentTurn) {
            case PLAYER:
                playerTurn(choice);
                enemyTurn();
                break;
            case ENEMY:
                enemyTurn();
                playerTurn(choice);
                break;
        }
    }

    private void playerTurn(BattleSceneConstants choice) {
        if (fighting) {
            fighting = runAction(choice, player, mob);
            setCurrentTurn(Combatant.ENEMY);
        }

    }

    private void enemyTurn() {
        if (fighting) {
            BattleSceneConstants mobChoice = mob.chooseAction();
            runAction(mobChoice, mob, player);
            setCurrentTurn(Combatant.PLAYER);
        }
    }

    public static void main(String[] args) {
        // Dummy player.
        Player player = ResourceLoaderUtility.loadPlayerFromDB("Bob");

        // Dummy mob.
        Mob mob = new Mob("Red Slime");

        Combat combat = new Combat(player, mob);

        while (true) {
            combat.battle(BattleSceneConstants.ATTACK);
            System.out.println("Bob: " + player.getHP() + " Mob: " + mob.getHP());

            if (player.getHP() <= 0 || mob.getHP() <= 0) {
                break;
            }
        }
    }
}
