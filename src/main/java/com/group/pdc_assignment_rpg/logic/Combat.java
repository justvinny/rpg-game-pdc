package com.group.pdc_assignment_rpg.logic;

import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import java.util.Random; 
import com.group.pdc_assignment_rpg.logic.entities.*;

/**
 * Controls combat mechanics and options.
 *
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */

public class Combat {
    private Player player;
    private Mob mob;
    private Combatant currentTurn;
    
    public Combat(Player player, Mob mob) {
        this.player = player;
        this.mob = mob;
        
        setFirstTurn();
        
        boolean run = true;
        while (run) {
            // TODO: Put the choice selected from gui into this variable:
            BattleSceneConstants choice = BattleSceneConstants.ATTACK;
            switch (currentTurn) {
                case PLAYER:
                    run = runAction(choice, player, mob);
                    setCurrentTurn(Combatant.ENEMY);
                    break;
                case ENEMY:
                    run = runAction(choice, mob, player);
                    setCurrentTurn(Combatant.PLAYER);
                    break;
            }
        }
        
        System.out.println("Combat ended!");
        
    }

    /**
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
        
        if (innitiatorCheck > targetCheck) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean statCompete(Stats innitiatorStat, Stats targetStat, Creature innitiator, Creature target) {
        int innitiatorCheck = innitiator.getStat(innitiatorStat) + roll(20);
        int targetCheck = target.getStat(targetStat) + roll(20);
        
        if (innitiatorCheck > targetCheck) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setFirstTurn() {
        if (statCompete(Stats.DEXTERITY, player, mob)) {
            currentTurn = Combatant.PLAYER;
        } else {
            currentTurn = Combatant.ENEMY;
        }
    }
    
    public boolean runAction(BattleSceneConstants action, Creature innitiator, Creature target) throws IllegalArgumentException {
        boolean keepRunning = true;
        
        switch (action) {
            case ATTACK:
                keepRunning = attack(innitiator, target);
                break;
            case DEFEND:
                // TODO
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
    public boolean attack(Creature innitiator, Creature target) {
        boolean attackSuccess = statCompete(Stats.STRENGTH, Stats.DEXTERITY, innitiator, target);
        
        if (attackSuccess) {
            System.out.println(innitiator.getName() + " managed to hit " + target.getName() + ".");
            boolean downed = target.damage(roll(5));
            
            if (downed) {
                System.out.println(target.getName() + " was defeated by " + innitiator.getName() + "!");
                return false;
            }
        } else {
            System.out.println(innitiator.getName() + " tried to attack but missed.");
        }
        return true;
    }
    
    public void defend(Creature innitiator, Creature target) {
        //Todo
    }
    
    public boolean escape(Creature innitiator, Creature target) {
        boolean escapeSuccess = statCompete(Stats.DEXTERITY, innitiator, target);
        
        if (escapeSuccess) {
            System.out.println(innitiator.getName() + " fled the battle!");
            return false;
        } else {
            System.out.println(innitiator.getName() + " made an attempt to flee, but was stopped by " + target.getName());
        }
        return true;
    }
    
    public static void main(String[] args) {
        // Dummy player.
        Player player = new Player("Bob");

        // Dummy mob.
        Mob mob = new Mob("Red Slime");
        
        Combat combat = new Combat(player, mob);
    }
}
