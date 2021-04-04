/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic;

/**
 * Class that contains 2d coordinates.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Coordinates {
    
    private int x, y;
    
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void incrementX() {
        x++;
    }
    
    public void decrementX() {
        x--;
    }
    
    public void incrementY() {
        y++;
    }
    
    public void decrementY() {
        y--;
    }
    
    
}
