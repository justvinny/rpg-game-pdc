/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.entities.PlayerListModel;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.observer.CustomObserver;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *  Controller class that handles the events from the PlayerLoading View. 
 *  This class is also an observer to the PlayerListModel and responds
 *  to any changes from that model.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class PlayerLoadingController implements CustomObserver {

    /*
        Constants
    */
    private static final String GAME_MESSAGE = "Hi! Welcome to our RPG Game!\nGameplay:\n"
            + "- There are 5 treasures chests that can be found.\n"
            + "- You can heal  your HP by leveling up or using Potions.\n"
            + "- Battles are random except for the boss monster.\n"
            + "- Boss monster can be found near the north east corner of the map.\n"
            + "- You can save the game by pressing the Exit button on the bottom right corner.\n"
            + "- Game exits and saves when you die and you'll be sent back to the entrance.\n\n";
    private static final String KEYS_AVAILABLE_MSG = "Keys Available:\nUse Arrows Keys for Movement\n"
            + "Enter - Open Treasures\nI - Open Inventory\nEsc - Exit and Save Game";
    
    /*
        Fields
    */
    private final ScreenManager screenManager;
    private final MainFrameView mainFrame;
    private final PlayerLoadingView playerLoading;
    private final PlayerListModel playerListModel;
    private final List<Treasure> treasures;

    /*
        Constructor
    */
    public PlayerLoadingController(PlayerListModel playerListModel, List<Treasure> treasures) {
        screenManager = ScreenManager.getInstance();
        mainFrame = screenManager.getMainFrameView();
        playerLoading = screenManager.getPlayerLoading();
        this.playerListModel = playerListModel;
        this.treasures = treasures;
        createBtnListener();
        startBtnListener();
        playerListModel.addObserver(this);
    }

    /**
     * Create player button listener. This button creates a player and saves
     * it to our database. If an invalid player name is entered, a JOptionPane
     * dialog is shown as an error message.
     */
    private void createBtnListener() {
        playerLoading.setBtnCreateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = playerLoading.getNameEntered();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Name can't be empty",
                            "Empty Name", JOptionPane.ERROR_MESSAGE);
                } else if (playerListModel.playerExists(name)) {
                    JOptionPane.showMessageDialog(mainFrame, name + " already exists.",
                            "Player Exists", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Create a new player and save it to the DB.
                    Player player = new Player(name);

                    ResourceLoaderUtility.DB_EXECUTOR.submit(new Runnable() {
                        @Override
                        public void run() {
                            playerListModel.add(player);

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    playerLoading.clearField();
                                    playerLoading.setPlayerSelected(name);
                                }
                            });
                        }
                    });

                }
            }
        });
    }

    /**
     * Start button listener for starting the game with the player selected.
     * Sets up the necessary things needed before loading up our main game screen.
     * This shows a JOptionPane as an error if a player has not been selected. 
     * At startup, this also shows a JOptionPane message that briefs the player
     * on how to play the game.
     */
    private void startBtnListener() {
        playerLoading.setBtnStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerLoading.playerSelected().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Must select a player to load.",
                            "No Player Selected", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Launch the main game with the selected player.
                    Player player = playerListModel.get(playerLoading.playerSelected());
                    Player.setPlayerInstance(player);
                    ScreenManager.getInstance().getGame().setPlayer(player);
                    ScreenManager.getInstance().getGame().setTreasures(treasures);
                    ScreenManager.getInstance().getInventory().setPlayer(player);
                    ScreenManager.getInstance().getInventory().updateInventoryData();
                    mainFrame.setCurrentScreen(ScreenManager.getInstance().getGame());
                    JOptionPane.showMessageDialog(mainFrame, GAME_MESSAGE + KEYS_AVAILABLE_MSG);
                    mainFrame.requestFocusInWindow();
                }
            }
        });
    }

    /**
     * Updates the PlayerLoading View when a change happens to the PlayerListModel
     * being observed.
     */
    @Override
    public void update() {
        playerLoading.setPlayerListModel(playerListModel.getPlayerList());
    }

}
