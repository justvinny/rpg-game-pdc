/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.Boundaries;
import com.group.pdc_assignment_rpg.logic.Coordinates;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public abstract class Scene {
    
    private Coordinates coordinates;
    private Boundaries boundaries;
    private boolean visible;
    
    public Scene(Coordinates coordinates, Boundaries boundaries) {
        this.coordinates = coordinates;
        this.boundaries = boundaries;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Boundaries getBoundaries() {
        return boundaries;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void toggle() {
        visible = !visible;
    }
    
    public abstract String[] createScene();
    
}
