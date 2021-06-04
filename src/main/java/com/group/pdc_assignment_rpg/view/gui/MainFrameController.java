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
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.DOWN;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.LEFT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.RIGHT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.UP;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for the main GUI frame. Most of the keyboard event handling 
 * for movement, interacting with treasures, and the player menu are stored here.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class MainFrameController {

    /*
        Fields
     */
    private final List<Treasure> treasures;
    private Map<Coordinates, Treasure> treasureMap;
    private ScreenManager screenManager;
    private MainFrameView mainFrame;

    /*
        Constructor
     */
    public MainFrameController(List<Treasure> treasures) {
        screenManager = ScreenManager.getInstance();
        mainFrame = screenManager.getMainFrameView();
        treasureMap = new HashMap<>();
        this.treasures = treasures;
        initTreasureCoordinates();
        initKeyListener();
    }

    /**
     * Coordinates for the treasure put in a map for quick efficient access.
     */
    private void initTreasureCoordinates() {
        treasures.forEach(t -> treasureMap.put(t.getCoordinates(), t));
    }

    /**
     * Event listener for the keyboard that will be used for player movement,
     * interacting with treasures on the map, opening the inventory, and exiting
     * the game.
     */
    private void initKeyListener() {
        Map<Integer, Boolean> keyPressed = new HashMap<>();
        populateKeyPressedMap(keyPressed);

        // Key listener for the frame where we can use keys such
        // as opening inventory and exiting the game using the
        // escape key.
        mainFrame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (mainFrame.getCurrentScreen() instanceof GameView) {
                    Player player = Player.getCurrentPlayer();
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            keyPressed.put(KeyEvent.VK_UP, false);
                            break;
                        case KeyEvent.VK_DOWN:
                            keyPressed.put(KeyEvent.VK_DOWN, false);
                            break;
                        case KeyEvent.VK_RIGHT:
                            keyPressed.put(KeyEvent.VK_RIGHT, false);
                            break;
                        case KeyEvent.VK_LEFT:
                            keyPressed.put(KeyEvent.VK_LEFT, false);
                            break;
                    }

                    // Only set the character to idle when there are
                    // absolutely zero arrow keys being pressed.
                    if (!keyPressed.values().contains(true)) {
                        player.setIdle();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (!(mainFrame.getCurrentScreen() instanceof PlayerLoadingView)) {
                    if (e.getKeyCode() == KeyEvent.VK_I) {
                        if (mainFrame.getCurrentScreen() instanceof GameView) {
                            mainFrame.setCurrentScreen(screenManager.getInventory());
                        } else if (mainFrame.getCurrentScreen() instanceof InventoryView) {
                            mainFrame.setCurrentScreen(screenManager.getGame());
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if (Player.getCurrentPlayer() != null) {
                            Player.getCurrentPlayer().savePlayer();
                        }

                        // Exit
                        screenManager.closeGame();
                    }
                }

                if (mainFrame.getCurrentScreen() instanceof GameView) {
                    Player player = Player.getCurrentPlayer();

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            keyPressed.put(KeyEvent.VK_UP, true);
                            player.setDirection(UP);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_DOWN:
                            keyPressed.put(KeyEvent.VK_DOWN, true);
                            player.setDirection(DOWN);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_RIGHT:
                            keyPressed.put(KeyEvent.VK_RIGHT, true);
                            player.setDirection(RIGHT);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_LEFT:
                            keyPressed.put(KeyEvent.VK_LEFT, true);
                            player.setDirection(LEFT);
                            player.setRunning();
                            break;
                        case KeyEvent.VK_ENTER:
                            checkForTreasure(player, treasureMap);
                    }
                }
            }

        });
    }

    /**
     * Set initial values for arrow key events to false to indicate that
     * they have not been pressed yet. This will be used to smoothen the 
     * player movement animation as Swing has a 1 second delay between recognizing
     * that a key has been pressed and transition it to a held state.
     * @param keyPressed 
     */
    private void populateKeyPressedMap(Map<Integer, Boolean> keyPressed) {
        keyPressed.put(KeyEvent.VK_UP, Boolean.FALSE);
        keyPressed.put(KeyEvent.VK_DOWN, Boolean.FALSE);
        keyPressed.put(KeyEvent.VK_LEFT, Boolean.FALSE);
        keyPressed.put(KeyEvent.VK_RIGHT, Boolean.FALSE);
    }

    /**
     * Helper method to to check for treasure when the enter key is pressed.
     * @param player used to add the treasure to the player inventory if unopened.
     * @param treasureMap contains coordinates of the treasure.
     */
    private void checkForTreasure(Player player, Map<Coordinates, Treasure> treasureMap) {
        if (treasureCollision(player, treasures)) {
            int playerX = player.getX();
            int playerY = player.getY();

            // Gets the treasure the player collided with.
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

            // Checks if the treasure has not been opened yet and if not, player
            // can open it and obtain the item inside.
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
