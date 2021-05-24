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
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class CombatController {

    private ScreenManager screenManager;
    private MainFrameView mainFrame;
    private CombatView combat;
    private GameView game;
    private InventoryView inventory;

    public CombatController() {
        screenManager = ScreenManager.getInstance();
        mainFrame = screenManager.getMainFrameView();
        combat = screenManager.getCombat();
        game = screenManager.getGame();
        inventory = screenManager.getInventory();

        combat.addBtnAttackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle(BattleSceneConstants.ATTACK);

            }
        });

        combat.addBtnDefendListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle(BattleSceneConstants.DEFEND);
            }
        });

        combat.addBtnEscapeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle(BattleSceneConstants.ESCAPE);
            }
        });
    }

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
            } else if (action == BattleSceneConstants.ESCAPE) {
                JOptionPane.showMessageDialog(mainFrame, "Player escaped!");
                combat.getCombat().playerEscape();
            } else if (combat.getCombat().mobEscaped()) {
                JOptionPane.showMessageDialog(mainFrame, "Enemy has escaped!");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "You have been defeated!");
                Player.getCurrentPlayer().resetCoordinates();
                Player.getCurrentPlayer().savePlayer();
                screenManager.closeGame();
            }
            game.addEvent(combatModel.getLastLog());
            game.updatePlayerInformation();
            inventory.updateInventoryData();
            screenManager.setCurrentScreen(ScreenManagerConstants.GAME);
        }
    }
}
