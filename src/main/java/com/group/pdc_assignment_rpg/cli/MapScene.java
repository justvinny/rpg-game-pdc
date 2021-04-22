/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.utilities.TextUtility;
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
    private String actionMessage;

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
        this.actionMessage = "None";

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
            refreshScene();
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
        scene.addAll(addPlayerBar());
        scene.addAll(addLegend());
        scene.addAll(addActionMessage());
    }

    /**
     * String representation of the player bar that would be shown on the map as
     * part of the HUD. This details the player name, current hp, and max hp.
     *
     * @return the player bar.
     */
    private List<String> addPlayerBar() {
        List<String> playerBar = new ArrayList<>();
        String playerInfo1 = String.format("Name: %s | Lv. %s | HP: %d/%d",
                player.getName(),
                player.getLevel().getLvl(),
                player.getHP(),
                player.getMaxHP());
        
        String playerInfo2 = String.format("Vitality: %d | Endurance: %d |"
                + " Willpower: %d | Damage: %d | Protection: %d",
                player.getStats().getVitality(),
                player.getStats().getEndurance(),
                player.getStats().getWillpower(),
                player.getDamage(),
                player.getProtection());
        
        playerBar.add(createDashes());
        playerBar.add(playerInfo1);
        playerBar.add(playerInfo2);
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
        legend.add(createDashes());
        legend.add("Keys: [I] - Inventory | [Esc] - Exit Game");
        legend.add("Symbols: P - Player (You) | T - Treasures | B - Final Boss");
        legend.add("Use arrow keys for player movement. Up, Down, Left, or Right.");
        return legend;
    }

    /**
     * Shows the last event that happened to the UI so our players knows
     * they've picked up an item from a treasure for example.
     * 
     * @return the event UI.
     */
    private List<String> addActionMessage() {
        List<String> message = new ArrayList<>();
        message.add(createDashes());
        message.add("Event: " + actionMessage);
        message.add(createDashes());
        return message;
    }

    /**
     * Makes a row dashes at the same width of our map for UI decoration
     * purposes.
     * 
     * @return a row of dashes.
     */
    private String createDashes() {
        int dashLength = map.get(0).length();
        return TextUtility.repeatCharacter(dashLength, '-');
    }

    /**
     * Changes the message in response to events like picking up items, 
     * fighting a mob, or fighting a boss.
     * 
     * @param message of the event that is happening.
     */
    public void setActionMessage(String message) {
        actionMessage = message;
        refreshScene();
    }
}
