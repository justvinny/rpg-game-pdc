/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import java.util.List;

/**
 * Represents a game map we're playing on.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MapScene extends Scene {

    private List<String> map;
    
    public MapScene(List<String> map) {
        super();
        this.map = map;
    }
    
    @Override
    public List<String> createScene() {
        return map;
    }

}
