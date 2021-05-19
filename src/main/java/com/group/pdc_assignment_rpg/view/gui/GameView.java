/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_BTN_DIMENSION;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameView extends JPanel {

    private static final Dimension GAME_MAP_DIMS = new Dimension((int) (MainFrameView.FRAME_WIDTH * .99), (int) (MainFrameView.FRAME_HEIGHT * .9));
    private static final String INVENTORY_BTN = "[ I ] nventory";
    private static final String EXIT_BTN = "[ E ] xit";

    private SpringLayout layout;
    private JLabel labelPlayerInfo;
    private JPanel gameMapContainer;
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
        gameMapContainer = new JPanel();
        gameMapContainer.setBackground(Color.BLACK);
        gameMapContainer.setPreferredSize(GAME_MAP_DIMS);

        add(gameMapContainer);
    }

    // Player HUD at bottom of game view.
    private void createPlayerHUD() {
        // Player info
        labelPlayerInfo = new JLabel(getPlayerInformation());
        labelPlayerInfo.setFont(DEFAULT_FONT);

        // Inventory button
        btnInventory = new JButton(INVENTORY_BTN);
        btnInventory.setPreferredSize(DEFAULT_BTN_DIMENSION);
        btnInventory.setFont(DEFAULT_FONT);

        // Exit button
        btnExit = new JButton(EXIT_BTN);
        btnExit.setPreferredSize(DEFAULT_BTN_DIMENSION);
        btnExit.setFont(DEFAULT_FONT);

        // Add
        add(labelPlayerInfo);
        add(btnInventory);
        add(btnExit);
    }

    private String getPlayerInformation() {
        return String.format("Name: %s | Lv. %s | Exp %d/%d | HP: %d/%d | "
                + "Damage: %d | Protection: %d | Vitality: %d | Endurance: %d |"
                + " Willpower: %d",
                player.getName(),
                player.getLevel().getLvl(),
                player.getXP(),
                player.getLevel().getThreshold(),
                player.getHP(),
                player.getMaxHP(),
                player.getDamage(),
                player.getProtection(),
                player.getStats().getVitality(),
                player.getStats().getEndurance(),
                player.getStats().getWillpower());
    }

    // constraints
    private void setSpringLayoutConstraints() {
        // Game map panel
        layout.putConstraint(SpringLayout.WEST, gameMapContainer, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, gameMapContainer, 0, SpringLayout.NORTH, this);

        // label for player info
        layout.putConstraint(SpringLayout.SOUTH, labelPlayerInfo, -DEFAULT_MARGIN, SpringLayout.SOUTH, this);
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
        labelPlayerInfo.setText(getPlayerInformation());
    }

    public void addBtnInventoryListener(ActionListener actionListener) {
        btnInventory.addActionListener(actionListener);
    }

    public void addBtnExitListener(ActionListener actionListener) {
        btnExit.addActionListener(actionListener);
    }
}
