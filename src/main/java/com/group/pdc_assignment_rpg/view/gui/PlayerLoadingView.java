/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class PlayerLoadingView extends JPanel {

    private static final int DEFAULT_MARGIN = 5;
    private static final String CREATE_PLAYER_LABEL = "Enter Name";
    private static final String CREATE_PLAYER_BTN = "Create Player";
    private static final String LOAD_PLAYER_TEXT = "Load Player";
    private static final String START_GAME_BTN = "Start Game";
    private static final Dimension DEFAULT_BTN_DIMENSION = new Dimension(150, 30);
    private static final Dimension FIELD_DIMENSIONS = new Dimension(250, 30);
    private static final Dimension LIST_DIMENSIONS = new Dimension(250, 200);

    private SpringLayout layout;
    private JList jListPlayers;
    private JTextField fieldCreatePlayer;
    private JLabel labelCreatePlayer, labelLoadPlayer;
    private JButton btnCreatePlayer, btnStartGame;
    private JScrollPane scrollPlayerList;

    public PlayerLoadingView() {
        panelSettings();
        createPlayer();
        loadPlayerList();
        setSpringLayoutConstraints();
    }

    private void panelSettings() {
        // Panel settings.
        setBackground(Color.WHITE);

        // Layout manager.
        layout = new SpringLayout();
        setLayout(layout);
    }

    // Create character
    private void createPlayer() {
        // Label
        labelCreatePlayer = new JLabel(CREATE_PLAYER_LABEL);
        labelCreatePlayer.requestFocusInWindow();

        // Field
        fieldCreatePlayer = new JTextField();
        fieldCreatePlayer.setPreferredSize(FIELD_DIMENSIONS);
        fieldCreatePlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Button
        btnCreatePlayer = new JButton(CREATE_PLAYER_BTN);
        btnCreatePlayer.setPreferredSize(DEFAULT_BTN_DIMENSION);

        // add 
        add(labelCreatePlayer);
        add(fieldCreatePlayer);
        add(btnCreatePlayer);
    }

    // Load character list
    private void loadPlayerList() {
        // Label
        labelLoadPlayer = new JLabel(LOAD_PLAYER_TEXT);

        // List
        jListPlayers = new JList();
        jListPlayers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPlayerList = new JScrollPane(jListPlayers);
        scrollPlayerList.setPreferredSize(LIST_DIMENSIONS);
        scrollPlayerList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPlayerList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPlayerList.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Button
        btnStartGame = new JButton(START_GAME_BTN);
        btnStartGame.setPreferredSize(DEFAULT_BTN_DIMENSION);

        // add
        add(labelLoadPlayer);
        add(scrollPlayerList);
        add(btnStartGame);
    }

    // Set constraints
    private void setSpringLayoutConstraints() {
        // Create player jlabel
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelCreatePlayer, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, labelCreatePlayer, (int) (MainFrameView.FRAME_HEIGHT * .1), SpringLayout.NORTH, this);

        // Create player jField
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fieldCreatePlayer, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, fieldCreatePlayer, DEFAULT_MARGIN, SpringLayout.SOUTH, labelCreatePlayer);

        // Create player button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnCreatePlayer, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, btnCreatePlayer, DEFAULT_MARGIN, SpringLayout.SOUTH, fieldCreatePlayer);

        // Load player jlabel
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelLoadPlayer, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, labelLoadPlayer, 20, SpringLayout.SOUTH, btnCreatePlayer);

        // Load player jList
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPlayerList, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, scrollPlayerList, DEFAULT_MARGIN, SpringLayout.SOUTH, labelLoadPlayer);

        // Start game button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnStartGame, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, btnStartGame, DEFAULT_MARGIN, SpringLayout.SOUTH, scrollPlayerList);

    }

    public void setPlayerListModel(List<Player> playerList) {
        String[] playerNames = playerList
                .stream()
                .map(p -> p.getName())
                .toArray(String[]::new);
        jListPlayers.setListData(playerNames);
    }

    public void setBtnCreateListener(ActionListener actionListener) {
        btnCreatePlayer.addActionListener(actionListener);
    }

    public void setBtnStartListener(ActionListener actionListener) {
        btnStartGame.addActionListener(actionListener);
    }

    public String playerSelected() {
        boolean value = jListPlayers.getSelectedValue() != null;
        return (value) ? jListPlayers.getSelectedValue().toString() : "";
    }

    public void setPlayerSelected(String name) {
        jListPlayers.setSelectedValue(name, true);
    }

    public String getNameEntered() {
        return fieldCreatePlayer.getText();
    }

    public void clearField() {
        fieldCreatePlayer.setText("");
    }
}
