/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.assets.ImageLoader;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.BOX_COLOR;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
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
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_BTN_DIMS;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_WIDTH;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.TEXT_COLOR;
import java.awt.Graphics;
import java.awt.Image;

/**
 * GUI View for the initial player loading screen where the user can create
 * a character or load a character to play the game.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class PlayerLoadingView extends JPanel {

    /*
        Constants
    */
    private static final String CREATE_PLAYER_LABEL = "- Enter Name -";
    private static final String CREATE_PLAYER_BTN = "Create Player";
    private static final String LOAD_PLAYER_TEXT = "- Load Player -";
    private static final String START_GAME_BTN = "Start Game";
    private static final Dimension FIELD_DIMENSIONS = new Dimension(250, 30);
    private static final Dimension LIST_DIMENSIONS = new Dimension(250, 200);

    /*
        Fields
    */
    private SpringLayout layout;
    private JList jListPlayers;
    private JTextField fieldCreatePlayer;
    private JLabel labelCreatePlayer, labelLoadPlayer;
    private JButton btnCreatePlayer, btnStartGame;
    private JScrollPane scrollPlayerList;

    /*
        Constructor
    */
    public PlayerLoadingView() {
        panelSettings();
        createPlayer();
        loadPlayerList();
        setSpringLayoutConstraints();
    }

    /**
     * JPanel settings go here. 
     */
    private void panelSettings() {
        // Layout manager.
        layout = new SpringLayout();
        setLayout(layout);
    }

    /**
     * Initialises all the JComponents needed for creating a player.
     */
    private void createPlayer() {
        // Label
        labelCreatePlayer = new JLabel(CREATE_PLAYER_LABEL);
        labelCreatePlayer.setForeground(TEXT_COLOR);
        labelCreatePlayer.requestFocusInWindow();
        labelCreatePlayer.setFont(DEFAULT_FONT);

        // Field
        fieldCreatePlayer = new JTextField();
        fieldCreatePlayer.setPreferredSize(FIELD_DIMENSIONS);
        fieldCreatePlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Button
        btnCreatePlayer = new JButton(CREATE_PLAYER_BTN);
        btnCreatePlayer.setPreferredSize(DEFAULT_BTN_DIMS);
        btnCreatePlayer.setFont(DEFAULT_FONT);
        btnCreatePlayer.setBackground(BOX_COLOR);
        btnCreatePlayer.setForeground(TEXT_COLOR);

        // add 
        add(labelCreatePlayer);
        add(fieldCreatePlayer);
        add(btnCreatePlayer);
    }

    /**
     * Initialises all the JComponents needed for loading a player.
     */
    private void loadPlayerList() {
        // Label
        labelLoadPlayer = new JLabel(LOAD_PLAYER_TEXT);
        labelLoadPlayer.setForeground(TEXT_COLOR);
        labelLoadPlayer.setFont(DEFAULT_FONT);

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
        btnStartGame.setPreferredSize(DEFAULT_BTN_DIMS);
        btnStartGame.setFont(DEFAULT_FONT);
        btnStartGame.setBackground(BOX_COLOR);
        btnStartGame.setForeground(TEXT_COLOR);

        // add
        add(labelLoadPlayer);
        add(scrollPlayerList);
        add(btnStartGame);
    }


    /**
     * Spring Layout constraints to position our Components.
     */
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

    /*
     * Overrided paintComponent to display a customer background.
     *  
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        Image bg = ImageLoader.getInstance().getLoadingBG();
        g.drawImage(bg, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
    }

    /**
     * Sets the player list model to reflect all the players in the database.
     * 
     * @param playerList complete list of players.
     */
    public void setPlayerListModel(List<Player> playerList) {
        String[] playerNames = playerList
                .stream()
                .map(p -> p.getName())
                .toArray(String[]::new);
        jListPlayers.setListData(playerNames);
    }

    /*
     * Methods to create event listeners on our GUI which will be used by
     * its controller.
     * 
     */
    public void setBtnCreateListener(ActionListener actionListener) {
        btnCreatePlayer.addActionListener(actionListener);
    }

    public void setBtnStartListener(ActionListener actionListener) {
        btnStartGame.addActionListener(actionListener);
    }

    /**
     * Gets the player selected from the JList
     * @return selected player.
     */
    public String playerSelected() {
        boolean isNull = jListPlayers.getSelectedValue() == null;
        return (isNull) ? "" : jListPlayers.getSelectedValue().toString();
    }

    /**
     * This method selects the player that has just been created to better
     * the user experience.
     * @param name of the created player
     */
    public void setPlayerSelected(String name) {
        jListPlayers.setSelectedValue(name, true);
    }

    /**
     * Gets the name entered to the JTextField to create a new player.
     * @return name of the new player.
     */
    public String getNameEntered() {
        return fieldCreatePlayer.getText();
    }

    /**
     * Used to empty JTextField when a player is successfully created.
     */
    public void clearField() {
        fieldCreatePlayer.setText("");
    }
    
    
}
