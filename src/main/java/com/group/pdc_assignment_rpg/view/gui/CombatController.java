/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import com.group.pdc_assignment_rpg.logic.Combat;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controller class for the Combat View that handles the events in combat such
 * as attacking, defending, and escaping.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class CombatController {

    /*
        Fields
     */
    private final ScreenManager screenManager;
    private final MainFrameView mainFrame;
    private final CombatView combat;
    private final GameView game;
    private final InventoryView inventory;

    /*
        Constructor
     */
    public CombatController() {
        screenManager = ScreenManager.getInstance();
        mainFrame = screenManager.getMainFrameView();
        combat = screenManager.getCombat();
        game = screenManager.getGame();
        inventory = screenManager.getInventory();
        initListeners();
    }
    
    /**
     * Initialises the listeners that interact with the Combat View.
     */
    private void initListeners() {
        initBtnAttackListener();
        initBtnDefendListener();
        initBtnEscapeListener();
    }

    /**
     * Handles attack event logic in combat.
     */
    private void initBtnAttackListener() {
        combat.addBtnAttackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle(BattleSceneConstants.ATTACK);

            }
        });
    }

    /**
     * Handles defend event logic in combat.
     */
    private void initBtnDefendListener() {
        combat.addBtnDefendListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle(BattleSceneConstants.DEFEND);
            }
        });
    }

    /**
     * Handles escape event logic in combat.
     */
    private void initBtnEscapeListener() {
        combat.addBtnEscapeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle(BattleSceneConstants.ESCAPE);
            }
        });
    }

    /**
     * Helper method for all combat actions that contains responses the combat
     * events.
     * @param action 
     */
    private void battle(BattleSceneConstants action) {
        boolean keepFighting = combat.battle(action);
        combat.updateStatusBars();
        combat.updateBattleLog();

        if (!keepFighting) {
            Combat combatModel = combat.getCombat();
            if (combat.getCombat().playerWon()) {
                JOptionPane.showMessageDialog(mainFrame, "Player won!");

                // check if there are any item drops.
                if (!combatModel.getItemDropLog().isEmpty()) {
                    combatModel.getItemDropLog().forEach(i -> game.addEvent(i));
                }
            } else if (action == BattleSceneConstants.ESCAPE
                    && combat.getCombat().enemyEscaped()) {
                JOptionPane.showMessageDialog(mainFrame, "Player escaped!");
                combat.getCombat().playerEscape();
            } else if (action != BattleSceneConstants.ESCAPE
                    && combat.getCombat().enemyEscaped()) {
                JOptionPane.showMessageDialog(mainFrame, "Enemy has escaped!");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "You have been defeated!");
                Player.getCurrentPlayer().resetCoordinates();
                Player.getCurrentPlayer().savePlayer();
                screenManager.closeGame();
            }
            
            // Update Views.
            game.addEvent(combatModel.getLastLog());
            game.updatePlayerInformation();
            inventory.updateInventoryData();
            screenManager.setCurrentScreen(ScreenManagerConstants.GAME);
        }
    }
}
