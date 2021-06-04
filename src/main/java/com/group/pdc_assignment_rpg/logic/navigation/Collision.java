/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.navigation;

import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.DOWN;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.LEFT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.RIGHT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.UP;
import java.util.List;

/**
 *  Static collision class to detect if a player collides with an unpassable
 * object like walls, treasures, or bosses.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Collision {

    /**
     * Method to detect collision between the player and walls.
     * @param player
     * @param map
     * @return 
     */
    public static boolean wallCollision(Player player, List<String> map) {
        switch (player.getDirection()) {
            case UP:
                return player.getY() > 0
                        && map.get(player.getY() - 1).charAt(player.getX()) == '#';
            case DOWN:
                return player.getY() < map.size()
                        && map.get(player.getY() + 1).charAt(player.getX()) == '#';
            case LEFT:
                return player.getX() > 0
                        && map.get(player.getY()).charAt(player.getX() - 1) == '#';
            case RIGHT:
                return player.getX() < map.get(player.getY()).length()
                        && map.get(player.getY()).charAt(player.getX() + 1) == '#';
            default:
                return false;
        }
    }

    /**
     * Method to detect collision between the player anda tresure chest.
     * @param player
     * @param treasures
     * @return 
     */
    public static boolean treasureCollision(Player player, List<Treasure> treasures) {
        boolean willCollide = false;

        for (Treasure treasure : treasures) {
            switch (player.getDirection()) {
                case UP:
                    willCollide = treasure.getCoordinates().getY() == player.getY() - 1
                            && treasure.getCoordinates().getX() == player.getX();

                    if (willCollide) {
                        return willCollide;
                    }
                    break;
                case DOWN:
                    willCollide = treasure.getCoordinates().getY() == player.getY() + 1
                            && treasure.getCoordinates().getX() == player.getX();

                    if (willCollide) {
                        return willCollide;
                    }
                    break;
                case LEFT:
                    willCollide = treasure.getCoordinates().getY() == player.getY()
                            && treasure.getCoordinates().getX() == player.getX() - 1;

                    if (willCollide) {
                        return willCollide;
                    }
                    break;
                case RIGHT:
                    willCollide = treasure.getCoordinates().getY() == player.getY()
                            && treasure.getCoordinates().getX() == player.getX() + 1;

                    if (willCollide) {
                        return willCollide;
                    }
            }
        }

        return willCollide;
    }

    /**
     * Method to detect collision between a player and a boss monster.
     * @param player
     * @param boss
     * @return 
     */
    public static boolean bossCollision(Player player, Mob boss) {
        return player.getY() == boss.getY() && player.getX() == boss.getX() - 1
                && boss.isAlive();
    }
}
