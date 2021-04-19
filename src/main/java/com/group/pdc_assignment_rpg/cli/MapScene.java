/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game map we're playing on.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MapScene extends Scene {

    private List<String> map;
    private List<String> scene;
    private Player player;
    private List<Treasure> treasures;

    public MapScene(List<String> map, List<Treasure> treasures, Player player) {
        super();
        this.map = map;
        this.treasures = treasures;
        this.player = player;

        addPlayerBar();
        addKeysAvailable();
    }

    @Override
    public List<String> createScene() {
        if (scene == null) {
            scene = new ArrayList<>();
            scene.addAll(map);
            scene.add(addPlayerBar());
            scene.addAll(addKeysAvailable());
        }

        return scene;
    }

    public void refreshScene() {
        scene = new ArrayList<>();
        scene.addAll(map);
        scene.add(addPlayerBar());
        scene.addAll(addKeysAvailable());
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    private String addPlayerBar() {
        String playerBar = String.format("Name: %s | HP: %d/%d",
                player.getName(), player.getHP(), player.getMaxHP());
        return playerBar;
    }

    private List<String> addKeysAvailable() {
        List<String> keys = new ArrayList<>();
        keys.add(" ");
        keys.add("Keys: [I] - Inventory   [Esc] - Exit Game");
        keys.add("Player Movement: Arrow Keys - Up, Down, Left, Right");

        return keys;
    }

}
