/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic;

/**
 * This class handles the movement logic for any object that needs to move
 * around in 2d space.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Navigation {

    private Coordinates coordinates;
    private Boundaries boundaries;

    public Navigation(Coordinates coordinates, Boundaries boundaries) {
        this.coordinates = coordinates;
        this.boundaries = boundaries;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Boundaries getBoundaries() {
        return boundaries;
    }
    
    public void up() {
        if (coordinates.getY() > boundaries.getYBoundStart()) {
            coordinates.decrementY();
        }
    }

    public void down() {
        if (coordinates.getY() < boundaries.getYBoundEnd()) {
            coordinates.incrementY();
        }
    }

    public void right() {
        if (coordinates.getX() < boundaries.getXBoundEnd()) {
            coordinates.incrementX();
        }
    }

    public void left() {
        if (coordinates.getX() > boundaries.getXBoundStart()) {
            coordinates.decrementX();
        }
    }
}
