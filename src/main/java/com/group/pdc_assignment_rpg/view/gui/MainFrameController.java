/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import static com.group.pdc_assignment_rpg.logic.navigation.Collision.treasureCollision;
import static com.group.pdc_assignment_rpg.logic.navigation.Collision.wallCollision;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.DOWN;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.LEFT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.RIGHT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.UP;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MainFrameController {

    public MainFrameController(MainFrameView view, List<Treasure> treasures) {
        ScreenManager screenManager = ScreenManager.getInstance();
        Map<Coordinates, Treasure> treasureMap = new HashMap<>();
        treasures.forEach(t -> treasureMap.put(t.getCoordinates(), t));

        // Key listener for the frame where we can use keys such
        // as opening inventory and exiting the game using the
        // escape key.
        view.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (view.getCurrentScreen() instanceof GameView) {
                    Player player = Player.getCurrentPlayer();
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_LEFT:
                            player.setIdle();
                    }
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
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
                    List<String> map = ScreenManager.getInstance().getGame().getMapView().getMapTxt();
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            player.setDirection(UP);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_DOWN:
                            player.setDirection(DOWN);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_RIGHT:
                            player.setDirection(RIGHT);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_LEFT:
                            player.setDirection(LEFT);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_ENTER:
                            if (treasureCollision(player, treasures)) {
                                int playerX = player.getX();
                                int playerY = player.getY();

                                Treasure treasure = null;
                                switch (player.getDirection()) {
                                    case UP:
                                        treasure = treasureMap.get(new Coordinates(playerX, playerY - 1));
                                        break;
                                    case DOWN:
                                        treasure = treasureMap.get(new Coordinates(playerX, playerY + 1));
                                        break;
                                    case LEFT:
                                        treasure = treasureMap.get(new Coordinates(playerX - 1, playerY));
                                        break;
                                    case RIGHT:
                                        treasure = treasureMap.get(new Coordinates(playerX + 1, playerY));
                                }

                                if (treasure != null) {
                                    if (!treasure.isOpened()) {
                                        Item item = treasure.open();
                                        player.getInventory().add(item);
                                        screenManager.getInventory().updateInventoryData();
                                        String eventMsg = "Opened treasure chest and obtained "
                                                + item.getName();
                                        screenManager.getGame().addEvent(eventMsg);
                                    }
                                }

                            }
                    }
                }
            }            

        });
    }
}
