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

    private static final int DEFAULT_STEP = 1;
    private int x, y, xStep, yStep;

    public Coordinates(int x, int y, int xStep, int yStep) {
        this.x = x;
        this.y = y;
        this.xStep = xStep;
        this.yStep = yStep;
    }

    public Coordinates(int x, int y) {
        this(x, y, DEFAULT_STEP, DEFAULT_STEP);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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
