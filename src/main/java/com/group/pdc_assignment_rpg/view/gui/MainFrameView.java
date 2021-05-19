/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MainFrameView extends JFrame {

    public static final int FRAME_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.75);
    public static final int FRAME_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.75);
    public static final Dimension FRAME_DIMENSIONS = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static final Dimension DEFAULT_BTN_DIMENSION = new Dimension(150, 30);
    public static final int DEFAULT_MARGIN = 5;
    public static final Font DEFAULT_FONT = new Font("Impact", Font.PLAIN, 16);
    
    private static final String GAME_TITLE = "RPG Game";

    private JPanel currentScreen;

    public MainFrameView() {
        // First screen at startup.
        currentScreen = ScreenManager.getInstance().getPlayerLoading();
        getContentPane().add(currentScreen);
        initGUI();
    }

    public JPanel getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(JPanel screen) {
        getContentPane().remove(currentScreen);
        currentScreen = screen;
        getContentPane().add(currentScreen);
        repaint();
        revalidate();

    }

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
