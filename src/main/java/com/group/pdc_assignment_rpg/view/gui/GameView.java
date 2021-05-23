/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.BOX_COLOR;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SpringLayout;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_BTN_DIMS;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_WIDTH;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.TEXT_COLOR;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameView extends JLayeredPane {

    private static final String INSPECT_BTN = "[ Enter ] - Inspect";
    private static final String INVENTORY_BTN = "[ I ] - Inventory";
    private static final String EXIT_BTN = "[ Esc ] - Exit";
    private static final Font PLAYER_INFO_FONT = new Font("Impact", Font.BOLD, 18);
    private static final Dimension EVENT_LIST_DIMS = new Dimension((int) (FRAME_WIDTH * .4), 100);

    private SpringLayout layout;
    private JLabel labelPlayerInfo;
    private MapView gameMapContainer;
    private JButton btnInventory, btnExit;
    private JTextArea txtAreaEventList;
    private JScrollPane scrollEventList;

    private Player player;
    private List<Treasure> treasures;
    private List<String> eventList;

    public GameView() {
        player = new Player("Placeholder");
        treasures = ResourceLoaderUtility.loadTreasures();
        eventList = new ArrayList<>();
        panelSettings();
        createGameMapContainer();
        createPlayerHUD();
        createEventList();
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
        gameMapContainer = new MapView(treasures);

        // JLayeredPane only works with boxed integers for some reason
        // and doesn't work with primitive int.
        add(gameMapContainer, new Integer(0));
    }

    // Player HUD at bottom of game view.
    private void createPlayerHUD() {
        // Player info
        labelPlayerInfo = new JLabel();
        labelPlayerInfo.setFont(PLAYER_INFO_FONT);
        labelPlayerInfo.setForeground(TEXT_COLOR);

        // Inventory button
        btnInventory = new JButton(INVENTORY_BTN);
        btnInventory.setPreferredSize(DEFAULT_BTN_DIMS);
        btnInventory.setFont(DEFAULT_FONT);
        btnInventory.setBackground(BOX_COLOR);
        btnInventory.setForeground(TEXT_COLOR);

        // Exit button
        btnExit = new JButton(EXIT_BTN);
        btnExit.setPreferredSize(DEFAULT_BTN_DIMS);
        btnExit.setFont(DEFAULT_FONT);
        btnExit.setBackground(BOX_COLOR);
        btnExit.setForeground(TEXT_COLOR);

        // JLayered pane uses boxed integers and not primitive int.
        add(labelPlayerInfo, new Integer(1));
        add(btnInventory, new Integer(1));
        add(btnExit, new Integer(1));
    }

    private void createEventList() {
        txtAreaEventList = new JTextArea();
        txtAreaEventList.setOpaque(false);
        txtAreaEventList.setForeground(TEXT_COLOR);
        txtAreaEventList.setFont(DEFAULT_FONT);
        txtAreaEventList.setFocusable(false);

        scrollEventList = new JScrollPane(txtAreaEventList);
        scrollEventList.setOpaque(false);
        scrollEventList.getViewport().setOpaque(false);
        scrollEventList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollEventList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollEventList.setPreferredSize(EVENT_LIST_DIMS);
        scrollEventList.setFocusable(false);

        add(scrollEventList, new Integer(1));
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

        // event list
        layout.putConstraint(SpringLayout.SOUTH, scrollEventList, -DEFAULT_MARGIN, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, scrollEventList, DEFAULT_MARGIN, SpringLayout.WEST, this);

    }

    public void updatePlayerInformation() {
        String playerInfo = String.format("Name: %s | Lv. %s | HP %d / %d | Exp: %d / %d",
                player.getName(),
                player.getLevel().getLvl(),
                player.getHP(),
                player.getMaxHP(),
                player.getXP(),
                player.getLevel().getThreshold());
        labelPlayerInfo.setText(playerInfo);
    }

    public void setPlayer(Player player) {
        this.player = player;
        updatePlayerInformation();
        gameMapContainer.setPlayerCamera(player);
    }

    public void addEvent(String event) {
        StringBuilder builder = new StringBuilder();
        eventList.add(event);
        eventList.forEach(e -> builder.append(e).append("\n"));
        builder.deleteCharAt(builder.length() - 1); // delete last new line
        txtAreaEventList.setText(builder.toString());
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
        gameMapContainer.setTreasures(treasures);
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
