/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MainFrameController {

    public MainFrameController(MainFrameView view) {
        ScreenManager screenManager = ScreenManager.getInstance();

        // Key listener for the frame where we can use keys such
        // as opening inventory and exiting the game using the
        // escape key.
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!(view.getCurrentScreen() instanceof PlayerLoadingView)) {
                    if (e.getKeyCode() == KeyEvent.VK_I) {
                        if (view.getCurrentScreen() instanceof GameView) {
                            view.setCurrentScreen(screenManager.getInventory());
                        } else if (view.getCurrentScreen() instanceof InventoryView) {
                            view.setCurrentScreen(screenManager.getGame());
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if (Player.getCurrentPlayer() != null) {
                            Player.getCurrentPlayer().savePlayer();
                        }

                        // Exit
                        view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
                    }
                }

                if (view.getCurrentScreen() instanceof GameView) {
                    Player player = Player.getCurrentPlayer();
                    List<String> map = ScreenManager.getInstance().getMap().getMapTxt();
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (player.getY() > 0
                                    && map.get(player.getY() - 1).charAt(player.getX()) != '#') {
                                player.up();
                            }

                            break;
                        case KeyEvent.VK_DOWN:
                            if (player.getY() < map.size()
                                    && map.get(player.getY() + 1).charAt(player.getX()) != '#') {
                                player.down();
                            }

                            break;
                        case KeyEvent.VK_RIGHT:
                            if (player.getX() < map.get(player.getY()).length()
                                    && map.get(player.getY()).charAt(player.getX() + 1) != '#') {
                                player.right();
                            }

                            break;
                        case KeyEvent.VK_LEFT:
                            if (player.getX() > 0
                                    && map.get(player.getY()).charAt(player.getX() - 1) != '#') {
                                player.left();
                            }
                    }
                }
            }
        });
    }
}
