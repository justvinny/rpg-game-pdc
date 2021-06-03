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
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class PlayerLoadingController implements CustomObserver {

    private static final String KEYS_AVAILABLE_MSG = "Keys Available:\nEnter - Open Treasures\nI - Open Inventory\nEsc - Exit and Save Game";
    private final ScreenManager screenManager;
    private final MainFrameView mainFrame;
    private final PlayerLoadingView playerLoading;
    private final PlayerListModel playerListModel;
    private final List<Treasure> treasures;

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
                    JOptionPane.showMessageDialog(mainFrame, KEYS_AVAILABLE_MSG);
                    mainFrame.requestFocusInWindow();

                    // TODO: hook up game logic when game map is finished.
                }
            }
        });
    }

    @Override
    public void update() {
        playerLoading.setPlayerListModel(playerListModel.getPlayerList());
    }

}
