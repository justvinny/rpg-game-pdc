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

    public GameController(MainFrameView mainFrame) {
        ScreenManager screenManager = ScreenManager.getInstance();

        screenManager.getGame().addBtnInventoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the inventory.
                if (mainFrame.getCurrentScreen() instanceof GameView) {
                    mainFrame.setCurrentScreen(screenManager.getInventory());
                }
                
                // Give back focus to main frame after button clicked
                // to allow the key listener on the frame to work.
                mainFrame.requestFocusInWindow(); 
            }
        });
        
        screenManager.getGame().addBtnExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getCurrentPlayer().savePlayer();
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}
