/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ScreenManager {
    private static ScreenManager instance = null;
    private PlayerLoadingView playerLoading;
    private InventoryView inventory;
    private GameView game;
    private MapView map;
    
    private ScreenManager() {
        playerLoading = new PlayerLoadingView();
        inventory = new InventoryView();
        game = new GameView();
        map = new MapView();
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
    
    public MapView getMap() {
        return map;
    }
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        
        return instance;
    }
}
