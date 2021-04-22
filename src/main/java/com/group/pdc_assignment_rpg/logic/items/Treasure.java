/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.items;

import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;

/**
 * A treasure located in the map that contains an item that a player
 * can obtain.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class Treasure {

    /*
     * Constants
     */
    public static final String COLOUR = "YELLOW";
    public static final char SYMBOL = 'T';
    
    /*
     * Fields
     */
    private final Item item;
    private final Coordinates coordinates;
    private boolean opened;

    /*
     * Constructor
     */
    public Treasure(Item item, Coordinates coordinates) {
        this.item = item;
        this.coordinates = coordinates;
        this.opened = false;
    }

    /**
     * Opening the chest can only be done once each time you play
     * the game.
     * @return 
     */
    public Item open() {
        if (!opened) {
            opened = true;
            return item;
        }

        return null;
    }

    /*
     * Getters  
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    public boolean isOpened() {
        return opened;
    }
}
