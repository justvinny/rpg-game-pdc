/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import java.awt.event.WindowEvent;

/**
 * Singleton class to manager screens for the RPG Game. This class will be responsible
 * for change the JPanel being displayed on our JFrame. Used singleton because
 * instantiating a new JPanel object every time we switch screens seems very inefficient.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class ScreenManager {

    private static ScreenManager instance = null;
    
    /*
        Fields
    */
    private final MainFrameView mainFrameView;
    private final PlayerLoadingView playerLoading;
    private final InventoryView inventory;
    private final GameView game;
    private final CombatView combat;
    private MapView map;

    /**
     * Private constructor for singleton class
     * @param mainFrameView reference to Swing JFrame being used.
     */
    private ScreenManager(MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
        playerLoading = new PlayerLoadingView();
        inventory = new InventoryView();
        game = new GameView();
        combat = new CombatView();
        map = null;
    }

    /*
     * Getters for all our screens.
     *  
     */
    public PlayerLoadingView getPlayerLoading() {
        return playerLoading;
    }

    public InventoryView getInventory() {
        return inventory;
    }

    public GameView getGame() {
        return game;
    }

    public CombatView getCombat() {
        return combat;
    }

    public MapView getMap() {
        return map;
    }

    public MainFrameView getMainFrameView() {
        return mainFrameView;
    }

    /*
     * Sets the map for the GameView since this can't be instantiated at startup
     * due to the player model not being present yet when the game view is created.
     * 
     */
    public void setMap(MapView map) {
        this.map = map;
    }

    /**
     * Exits our game by dispatching a closing window event to the system.
     */
    public void closeGame() {
        mainFrameView.dispatchEvent(new WindowEvent(mainFrameView, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Method used to change the current JPanel being displayed on our JFrame.
     * 
     * @param screen is the constant used to determine which screen to change to. 
     */
    public void setCurrentScreen(ScreenManagerConstants screen) {
        switch (screen) {
            case PLAYER_LOADING:
                mainFrameView.setCurrentScreen(playerLoading);
                break;
            case GAME:
                mainFrameView.setCurrentScreen(game);
                break;
            case INVENTORY:
                mainFrameView.setCurrentScreen(inventory);
                break;
            case COMBAT:
                mainFrameView.setCurrentScreen(combat);
                break;
            case MAP:
                mainFrameView.setCurrentScreen(map);

        }
        // Requests focus for the mainframe to make sure key its key listeners work.
        mainFrameView.requestFocusInWindow();
    }

    /**
     * Initialises our singleton class.
     * @param mainFrameView instance of the main frame of our Swing GUI. 
     */
    public static void initScreenManager(MainFrameView mainFrameView) {
        if (instance == null) {
            instance = new ScreenManager(mainFrameView);
        }
    }

    /**
     * Gets the singleton instance of our screen manager.
     * @return singleton instance
     */
    public static ScreenManager getInstance() {
        return instance;
    }
}
