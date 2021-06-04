/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.entities;

import com.group.pdc_assignment_rpg.observer.CustomObservable;
import com.group.pdc_assignment_rpg.observer.CustomObserver;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Observable player list model that contains all the players in the database.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class PlayerListModel extends CustomObservable {

    /*
        Fields
    */
    private List<Player> playerList;

    /*
        Constructor
    */
    public PlayerListModel() {
        super();
        setPlayerList();
    }

    /**
     * Load the player list from the database.
     */
    private void setPlayerList() {
        playerList = ResourceLoaderUtility.loadAllPlayersFromDB();
    }

    /**
     * Add an object interested in observing this class.
     * @param observer 
     */
    @Override
    public void addObserver(CustomObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer.
     * @param observer 
     */
    @Override
    public void removeObserver(CustomObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers that there has been a change in the model.
     */
    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update());
    }

    /**
     * Adds a new player to the database.
     * @param player 
     */
    public void add(Player player) {
        if (!playerList.contains(player)) {
            ResourceLoaderUtility.writePlayerData(player);
            playerList.add(player);
            notifyObservers();
        }
    }

    /**
     * Gets a player from the list based on the player name given.
     * @param name
     * @return 
     */
    public Player get(String name) {
        Player player = playerList
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
        return Objects.requireNonNull(player, "Can't load null player");
    }

    /**
     * Gets a list of all the players in the database.
     * @return 
     */
    public List<Player> getPlayerList() {
        return playerList
                .stream()
                .sorted(Comparator.comparing(Player::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Checks if a given player name exists in the database.
     * @param name
     * @return 
     */
    public boolean playerExists(String name) {
        return playerList
                .stream()
                .anyMatch(p -> p.getName().equals(name));
    }
}
