/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameController {

    private ScreenManager screenManager;
    public GameController() {
        screenManager = ScreenManager.getInstance();

        screenManager.getGame().addBtnInventoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.setCurrentScreen(ScreenManagerConstants.INVENTORY);
            }
        });

        screenManager.getGame().addBtnExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getCurrentPlayer().savePlayer();
                screenManager.closeGame();
            }
        });
    }
}
