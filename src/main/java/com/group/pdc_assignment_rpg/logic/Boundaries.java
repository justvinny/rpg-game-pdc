/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic;

/**
 * Class that contains boundaries where an object is confined in for movement.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Boundaries {
    
    private final int xBoundStart, yBoundStart;
    private final int xBoundEnd, yBoundEnd;
    
    public Boundaries(
            int xBoundStart, 
            int yBoundStart, 
            int xBoundEnd, 
            int yBoundEnd) {
        
        this.xBoundStart = xBoundStart;
        this.yBoundStart = yBoundStart;
        this.xBoundEnd = xBoundEnd;
        this.yBoundEnd = yBoundEnd;
    }

    public int getXBoundStart() {
        return xBoundStart;
    }

    public int getYBoundStart() {
        return yBoundStart;
    }

    public int getXBoundEnd() {
        return xBoundEnd;
    }

    public int getYBoundEnd() {
        return yBoundEnd;
    }
}
