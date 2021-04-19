/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import java.util.List;

/**
 * Base class for all scenes.
 * Scenes are what we see on the screen when we're playing.
 * These include: maps, inventory, and battles.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public abstract class Scene {
    
    private boolean visible;
    
    /**
     * Constructor that sets visibility of a scene to false by default.
     */
    public Scene() {
        visible = false; 
    }

    /*
     * Getter method
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Toggles the visibility on or off for the scene.
     * On means to draw it on our terminal/ui.
     * Off means not to draw it.
     */
    public void toggle() {
        visible = !visible;
    }
    
    /**
     * Used for transforming a scene into a list of strings so
     * we can draw it to our Lanterna console line by line.
     * @return a list of strings representing our scene.
     */
    public abstract List<String> createScene();
    
}
