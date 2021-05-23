/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import java.awt.event.WindowEvent;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ScreenManager {

    private static ScreenManager instance = null;
    private MainFrameView mainFrameView;
    private PlayerLoadingView playerLoading;
    private InventoryView inventory;
    private GameView game;
    private MapView map;
    private CombatView combat;

    private ScreenManager(MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
        playerLoading = new PlayerLoadingView();
        inventory = new InventoryView();
        game = new GameView();
        combat = new CombatView();
        map = null;
    }

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

    public void setMap(MapView map) {
        this.map = map;
    }

    public void closeGame() {
        mainFrameView.dispatchEvent(new WindowEvent(mainFrameView, WindowEvent.WINDOW_CLOSING));
    }

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
        mainFrameView.requestFocusInWindow();
    }

    public static void initScreenManager(MainFrameView mainFrameView) {
        instance = new ScreenManager(mainFrameView);
    }

    public static ScreenManager getInstance() {
        return instance;
    }
}
