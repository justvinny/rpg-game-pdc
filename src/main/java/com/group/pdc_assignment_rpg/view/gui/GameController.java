/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameController {

    public GameController(MainFrameView mainFrame) {
        ScreenManager screenManager = ScreenManager.getInstance();

        screenManager.getGame().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    if (mainFrame.getCurrentScreen() instanceof GameView) {
                        mainFrame.setCurrentScreen(screenManager.getInventory());
                        screenManager.getInventory().requestFocusInWindow();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Exit
                    mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
                }

            }
        });
    }
}
