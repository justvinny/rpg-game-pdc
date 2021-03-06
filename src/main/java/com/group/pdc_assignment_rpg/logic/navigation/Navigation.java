/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.navigation;

/**
 * This class handles the movement logic for any object that needs to move
 * around in 2d space.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Navigation {

    private final Coordinates coordinates;
    private final Boundaries boundaries;

    /**
     * Constructor.
     * 
     * @param coordinates - x and y values for our navigation.
     * @param boundaries - boundaries where we're not allowed to navigate to.
     */
    public Navigation(Coordinates coordinates, Boundaries boundaries) {
        this.coordinates = coordinates;
        this.boundaries = boundaries;
    }

    /*
     * Getters 
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Boundaries getBoundaries() {
        return boundaries;
    }
    
    /*
     * Movement/navigation.
     * Up - decrements y
     * Down -increments y
     * Left - increments x
     * Right - decrements x
     *
     * return - all of them return a boolean value on whether they successfully
     *          moved or not.
     */
    public boolean up() {
        if (coordinates.getY() > boundaries.getYBoundStart()) {
            coordinates.decrementY();
            return true;
        }
        
        return false;
    }

    public boolean down() {
        if (coordinates.getY() < boundaries.getYBoundEnd()) {
            coordinates.incrementY();
            return true;
        }
        
        return false;
    }

    public boolean right() {
        if (coordinates.getX() < boundaries.getXBoundEnd()) {
            coordinates.incrementX();
            return true;
        }
        
        return false;
    }

    public boolean left() {
        if (coordinates.getX() > boundaries.getXBoundStart()) {
            coordinates.decrementX();
            return true;
        }
        
        return false;
    }
}
