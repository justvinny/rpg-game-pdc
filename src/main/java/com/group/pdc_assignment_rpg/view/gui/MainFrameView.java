/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Main GUI frame which will be used to contain various JPanel that are part 
 * of our game.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class MainFrameView extends JFrame {

    /*
        Constants
    */
    public static final int FRAME_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.9);
    public static final int FRAME_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.9);
    public static final Dimension FRAME_DIMENSIONS = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static final Dimension DEFAULT_BTN_DIMS = new Dimension(150, 30);
    public static final int DEFAULT_MARGIN = 5;
    public static final Font DEFAULT_FONT = new Font("Impact", Font.PLAIN, 16);
    public static final Color TEXT_COLOR = new Color(215, 215, 215);
    public static final Color BOX_COLOR = new Color(77, 101, 180);
    public static final Color BG_COLOR = new Color(50, 51, 83);
    private static final String GAME_TITLE = "RPG Game";

    /*
        Fields
    */
    private JComponent currentScreen;
    private Player player;

    /*
        Constructor
    */
    public MainFrameView() {
        // First screen at startup.
        player = new Player("Placeholder");
        initGUI();
    }

    /**
     * Gets the current screen being displayed.
     * @return current screen.
     */
    public JComponent getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Changes the current screen being shown.
     * @param screen new screen.
     */
    public void setCurrentScreen(JComponent screen) {
        if (currentScreen != null) {
            getContentPane().remove(currentScreen);
        }
        
        currentScreen = screen;
        getContentPane().add(currentScreen);
        repaint();
        revalidate();

    }

    /**
     * Getter method for the player model currently being used in the game.
     * @return 
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player that will be used in the game.
     * @param player current player being played.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Initialises our GUI.
     */
    private void initGUI() {
        // Frame settings
        setTitle(GAME_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(FRAME_DIMENSIONS);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
