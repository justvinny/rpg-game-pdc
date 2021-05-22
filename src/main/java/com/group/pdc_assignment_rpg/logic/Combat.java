package com.group.pdc_assignment_rpg.logic;

import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import java.util.Random;
import com.group.pdc_assignment_rpg.logic.entities.*;
import com.group.pdc_assignment_rpg.logic.items.Item;
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
    
    private static int nStepsToCombat = (int) (Math.random() * 25) + 61;

    final float DEFENSEBONUS = 1.5f;

    private final Player player;
    private final Mob mob;
    private final List<String> battleLog;
    private Combatant currentTurn;
    private boolean fighting;

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
        if (battleLog.size() > BattleSceneConstants.BATTLE_LOG_LINES) {
            for (int i = 0; i < battleLog.size() - BattleSceneConstants.BATTLE_LOG_LINES; i++) {
                battleLog.remove(0);
            }
        }

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

    private boolean statCompete(Stats stat, Creature initiator, Creature target) {
        int initiatorCheck = initiator.getStat(stat) + roll(20);
        int targetCheck = target.getStat(stat) + roll(20);

        return initiatorCheck > targetCheck;
    }

    private boolean statCompete(Stats initiatorStat, Stats targetStat, Creature initiator, Creature target) {
        int initiatorCheck = initiator.getStat(initiatorStat) + roll(20);
        int targetCheck = target.getStat(targetStat) + roll(20);

        return initiatorCheck > targetCheck;
    }

    private boolean statCompete(Stats initiatorStat, Stats targetStat, Creature initiator, Creature target, float targetBonus) {
        int initiatorCheck = initiator.getStat(initiatorStat) + roll(20);
        int targetCheck = target.getStat(targetStat) + roll(20);

        if (targetBonus > 0) {
            targetCheck = (int) (targetCheck * targetBonus);
        }

        return initiatorCheck > targetCheck;
    }

    public final void setFirstTurn() {
        if (statCompete(Stats.DEXTERITY, player, mob)) {
            currentTurn = Combatant.PLAYER;
            battleLog.add(player.getName() + " leaps into action, ready to make a move!");
        } else {
            currentTurn = Combatant.ENEMY;
            battleLog.add(mob.getName() + " catches " + player.getName() + " off guard and makes the first move!");
        }
    }

    public boolean runAction(BattleSceneConstants action, Creature initiator, Creature target) throws IllegalArgumentException {
        boolean keepRunning = true;

        switch (action) {
            case ATTACK:
                keepRunning = attack(initiator, target);
                break;
            case DEFEND:
                defend(initiator);
                break;
            case ESCAPE:
                keepRunning = escape(initiator, target);
                break;
            default:
                throw new IllegalArgumentException("Invalid action given.");
        }

        return keepRunning;
    }

    /*
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
            damage = (damage <= 0) ? 1 : damage;
            battleLog.add(initiator.getName() + " managed to hit " + target.getName()
                    + " for " + damage + " damage.");
            boolean downed = target.damage(damage);

            if (downed) {
                battleLog.add(target.getName() + " was defeated by "
                        + initiator.getName() + "!");
                battleLog.add(initiator.getName() + " gained " + target.getXP()
                        + " XP from " + target.getName() + "!");
                initiator.addXP(target.getXP());
                target.setDefending(false);

                // Get the mob's loot if the dead target is a mob.
                if (target instanceof Mob) {
                    List<Item> loot = ((Mob) target).getLoot();
                    loot.forEach(item -> initiator.getInventory().add(item));

                    if (!loot.isEmpty()) {
                        battleLog.add("Obtained " + loot.toString());
                    }
                }
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

    public void defend(Creature initiator) {
        initiator.setDefending(true);
        battleLog.add(initiator.getName() + " prepares a defense.");
    }

    public boolean escape(Creature initiator, Creature target) {
        boolean escapeSuccess = statCompete(Stats.DEXTERITY, initiator, target);

        if (escapeSuccess) {
            battleLog.add(initiator.getName() + " fled the battle!");
            return false;
        } else {
            battleLog.add(initiator.getName() + " made an attempt to flee, but was stopped by " + target.getName());
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
            fighting = runAction(mobChoice, mob, player);
            setCurrentTurn(Combatant.PLAYER);
        }
    }

    public static int getNStepsToCombat() {
        return nStepsToCombat;
    }
    
    public static void resetNStepsToCombat() {
        nStepsToCombat = (int) (Math.random() * 25) + 61;
    }
}
