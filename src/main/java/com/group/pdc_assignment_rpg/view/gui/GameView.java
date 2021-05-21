/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_BTN_DIMS;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameView extends JLayeredPane {

    private static final String INVENTORY_BTN = "[ I ] - Inventory";
    private static final String EXIT_BTN = "[ Esc ] - Exit";
    private static final Font PLAYER_INFO_FONT = new Font("Impact", Font.BOLD, 18);

    private SpringLayout layout;
    private JLabel labelPlayerInfo;
    private MapView gameMapContainer;
    private JButton btnInventory, btnExit;

    private Player player;

    public GameView() {
        player = new Player("Placeholder");
        panelSettings();
        createGameMapContainer();
        createPlayerHUD();
        setSpringLayoutConstraints();
    }

    private void panelSettings() {
        setBackground(Color.WHITE);

        // Spring layout.
        layout = new SpringLayout();
        setLayout(layout);
    }

    // Game map panel
    private void createGameMapContainer() {
        gameMapContainer = new MapView();

        // JLayeredPane only works with boxed integers for some reason
        // and doesn't work with primitive int.
        add(gameMapContainer, new Integer(0));
    }

    // Player HUD at bottom of game view.
    private void createPlayerHUD() {
        // Player info
        labelPlayerInfo = new JLabel();
        labelPlayerInfo.setFont(PLAYER_INFO_FONT);
        labelPlayerInfo.setForeground(Color.WHITE);

        // Inventory button
        btnInventory = new JButton(INVENTORY_BTN);
        btnInventory.setPreferredSize(DEFAULT_BTN_DIMS);
        btnInventory.setFont(DEFAULT_FONT);

        // Exit button
        btnExit = new JButton(EXIT_BTN);
        btnExit.setPreferredSize(DEFAULT_BTN_DIMS);
        btnExit.setFont(DEFAULT_FONT);

        // JLayered pane uses boxed integers and not primitive int.
        add(labelPlayerInfo, new Integer(1));
        add(btnInventory, new Integer(1));
        add(btnExit, new Integer(1));
    }

    private void setPlayerInformation() {
        String playerInfo = String.format("Name: %s | Lv. %s | HP %d / %d | Exp: %d / %d",
                player.getName(),
                player.getLevel().getLvl(),
                player.getHP(),
                player.getMaxHP(),
                player.getXP(),
                player.getLevel().getThreshold());
        labelPlayerInfo.setText(playerInfo);
    }

    // constraints
    private void setSpringLayoutConstraints() {
        // Game map panel
        layout.putConstraint(SpringLayout.WEST, gameMapContainer, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, gameMapContainer, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, gameMapContainer, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, gameMapContainer, 0, SpringLayout.SOUTH, this);

        // label for player info
        layout.putConstraint(SpringLayout.NORTH, labelPlayerInfo, DEFAULT_MARGIN, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, labelPlayerInfo, DEFAULT_MARGIN, SpringLayout.WEST, this);

        // btn for inventory
        layout.putConstraint(SpringLayout.SOUTH, btnInventory, -DEFAULT_MARGIN, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnInventory, -DEFAULT_MARGIN, SpringLayout.WEST, btnExit);

        // btn for exit
        layout.putConstraint(SpringLayout.SOUTH, btnExit, -DEFAULT_MARGIN, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnExit, -DEFAULT_MARGIN, SpringLayout.EAST, this);

    }

    public void setPlayer(Player player) {
        this.player = player;
        setPlayerInformation();
        gameMapContainer.setPlayerCamera(player);
    }

    public void addBtnInventoryListener(ActionListener actionListener) {
        btnInventory.addActionListener(actionListener);
    }

    public void addBtnExitListener(ActionListener actionListener) {
        btnExit.addActionListener(actionListener);
    }
    
    public MapView getMapView() {
        return gameMapContainer;
    }
}
