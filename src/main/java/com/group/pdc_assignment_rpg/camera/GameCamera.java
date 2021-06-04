/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.camera;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import static com.group.pdc_assignment_rpg.view.gui.MapView.TILE_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MapView.TILE_WIDTH;

/**
 * Game camera class used to view a certain a portion of the map at a time since
 * the map won't fit in one screen.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameCamera {
    
   /*
        Fields
    */
    private float xOffset, yOffset;
    private Player player;
    
    /*
        Constructor
    */
    public GameCamera(Player player, int xOffset, int yOffset) {
        this.player = player;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    /*  
        Getters
    */
    public float getXOffset() {
        return -((player.getX()) * TILE_WIDTH) + xOffset;
    }
    
    public float getYOffset() {
        return -((player.getY()) * TILE_HEIGHT) + yOffset;
    }
    
    /*
        Setter for player model used as reference for game camera offset.
    */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
}
