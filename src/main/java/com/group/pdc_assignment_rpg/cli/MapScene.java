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
    private Player player;
    private List<Treasure> treasures;
    private List<String> scene;

    /**
     * Constructor for map scene.
     *
     * @param map - the map we are loading for our main game.
     * @param treasures - treasures around the map in the game.
     * @param player - the main player which we control.
     */
    public MapScene(List<String> map, List<Treasure> treasures, Player player) {
        super();
        this.map = map;
        this.treasures = treasures;
        this.player = player;

        addPlayerBar();
        addLegend();
    }

    /*
     * Getters
     */
    public List<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * Combines all the elements needed to form our main game map where our
     * player can play on.
     *
     * @return the main game map representation.
     */
    @Override
    public List<String> createScene() {
        if (scene == null) {
            scene = new ArrayList<>();
            scene.addAll(map);
            scene.add(addPlayerBar());
            scene.addAll(addLegend());
        }

        return scene;
    }

    /**
     * Updates the scene for any UI changes such as player HP reduction after a
     * battle.
     */
    public void refreshScene() {
        scene = new ArrayList<>();
        scene.addAll(map);
        scene.add(addPlayerBar());
        scene.addAll(addLegend());
    }

    /**
     * String representation of the player bar that would be shown on the map as
     * part of the HUD. This details the player name, current hp, and max hp.
     *
     * @return the player bar.
     */
    private String addPlayerBar() {
        String playerBar = String.format("Name: %s | Lv. %d | HP: %d/%d | Vitality: %d "
                + "| Endurance: %d | Willpower: %d | Damage: %d | Protection: %d",
                player.getName(),
                player.getLevel(),
                player.getHP(),
                player.getMaxHP(),
                player.getStats().getVitality(),
                player.getStats().getEndurance(),
                player.getStats().getWillpower(),
                player.getDamage(),
                player.getProtection());
        return playerBar;
    }

    /**
     * String representation of the keys available for the player to press. Keys
     * such as movement navigation, opening inventory, and exiting the game.
     *
     * @return the keys available.
     */
    private List<String> addLegend() {
        List<String> legend = new ArrayList<>();
        legend.add(" ");
        legend.add("Keys: [I] - Inventory | [Esc] - Exit Game");
        legend.add("Symbols: P - Player (You) | T - Treasures | B - Final Boss");
        legend.add("Use arrow keys for player movement. Up, Down, Left, or Right.");
        return legend;
    }
}
