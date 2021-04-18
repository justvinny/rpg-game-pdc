/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.util.List;

/**
 * Represents a game map we're playing on.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MapScene extends Scene {

    private List<String> map;
    private Player player;
    
    public MapScene(List<String> map, Player player) {
        super();
        this.map = map;
        this.player = player;
        
        addPlayerBar();
        addKeysAvailable();
    }

    @Override
    public List<String> createScene() {
        return map;
    }

    private void addPlayerBar() {
        String playerBar = String.format("Name: %s | HP: %d/%d",
                player.getName(), player.getHP(), player.getMaxHP());
        map.add(playerBar);
    }
    
    private void addKeysAvailable() {
        map.add(" ");
        map.add("Keys: [I] - Inventory   [Esc] - Exit Game");
        map.add("Player Movement: Arrow Keys - Up, Down, Left, Right");
    }

}
