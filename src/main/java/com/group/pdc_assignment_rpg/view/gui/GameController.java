/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the Game View that handles the events such as opening
 * the inventory and closing the game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class GameController {

    /*
        Fields
     */
    private ScreenManager screenManager;

    /*
        Constructor
     */
    public GameController() {
        screenManager = ScreenManager.getInstance();
        initListeners();
    }

    private void initListeners() {
        initBtnInventoryListener();
        initBtnExitListener();
    }

    private void initBtnInventoryListener() {
        screenManager.getGame().addBtnInventoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.setCurrentScreen(ScreenManagerConstants.INVENTORY);
            }
        });
    }

    private void initBtnExitListener() {
        screenManager.getGame().addBtnExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getCurrentPlayer().savePlayer();
                screenManager.closeGame();
            }
        });
    }
}
