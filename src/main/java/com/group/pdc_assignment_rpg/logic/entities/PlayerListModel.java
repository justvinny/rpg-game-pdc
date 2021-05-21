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
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class PlayerListModel extends CustomObservable {

    private List<Player> playerList;

    public PlayerListModel() {
        super();
        playerList = ResourceLoaderUtility.loadAllPlayersFromDB();
    }

    @Override
    public void addObserver(CustomObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CustomObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update());
    }

    public void add(Player player) {
        if (!playerList.contains(player)) {
            ResourceLoaderUtility.writePlayerData(player);
            ResourceLoaderUtility.writeInventoryData(player);
            ResourceLoaderUtility.writeEquippedData(player);
            playerList.add(player);
            notifyObservers();
        }
    }

    public Player get(String name) {
        Player player = playerList
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
        return Objects.requireNonNull(player, "Can't load null player");
    }

    public List<Player> getPlayerList() {
        return playerList
                .stream()
                .sorted(Comparator.comparing(Player::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean playerExists(String name) {
        return playerList
                .stream()
                .anyMatch(p -> p.getName().equals(name));
    }
}