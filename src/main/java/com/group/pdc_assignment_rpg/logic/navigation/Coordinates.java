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
}
