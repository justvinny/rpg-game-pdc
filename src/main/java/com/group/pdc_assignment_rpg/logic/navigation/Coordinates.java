/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.navigation;

/**
 * Class that contains 2d coordinates.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Coordinates {

    /**
     * Constants
     */
    private static final int DEFAULT_STEP = 1;
    
    /**
     * Fields
     */
    private int x, y, xStep, yStep;

    /**
     * Constructor
     * @param x - starting x position.
     * @param y - starting y position.
     * @param xStep - number of steps for each movement of x.
     * @param yStep - numbers of steps for each movement of y.
     */
    public Coordinates(int x, int y, int xStep, int yStep) {
        this.x = x;
        this.y = y;
        this.xStep = xStep;
        this.yStep = yStep;
    }

    /**
     * Alternate constructor
     * @param x - starting x position.
     * @param y - starting y position.
     */
    public Coordinates(int x, int y) {
        this(x, y, DEFAULT_STEP, DEFAULT_STEP);
    }

    /*
     * Getters
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    /*
     * Setters
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /*
     * Movement in a 2d plane by manipulating x and y values.
     */
    public void incrementX() {
        x += xStep;
    }

    public void decrementX() {
        x -= xStep;
    }

    public void incrementY() {
        y += yStep;
    }

    public void decrementY() {
        y -= yStep;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        hash = 97 * hash + this.xStep;
        hash = 97 * hash + this.yStep;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinates other = (Coordinates) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.xStep != other.xStep) {
            return false;
        }
        return this.yStep == other.yStep;
    }
    
    
}
