/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.items;

import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Treasure {

    public static final String COLOUR = "YELLOW";
    public static final char SYMBOl = 'T';
    private Item item;
    private Coordinates coordinates;
    private boolean opened;

    public Treasure(Item item, Coordinates coordinates) {
        this.item = item;
        this.coordinates = coordinates;
        this.opened = false;
    }

    public Item open() {
        if (!opened) {
            opened = true;
            return item;
        }

        return null;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    public boolean isOpened() {
        return opened;
    }
}
