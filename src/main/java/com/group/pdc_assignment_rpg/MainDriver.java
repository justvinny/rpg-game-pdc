package com.group.pdc_assignment_rpg;

import com.group.pdc_assignment_rpg.logic.navigation.*;
import com.group.pdc_assignment_rpg.logic.items.*;
import com.group.pdc_assignment_rpg.logic.entities.*;
import com.group.pdc_assignment_rpg.cli.*;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_END;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_START;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_STEP;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_END;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_START;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_STEP;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.io.IOException;
import java.util.List;

/**
 * Entry point to the RPG game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MainDriver {

    public static void main(String[] args) {
        try {
            init();
        } catch (InvalidMapException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void init() throws InvalidMapException {
        // Load player.
        Player player = new Player("Bob");

        // Load dummy mob.
        Mob mob = new Mob("Red Slime");

        // Load map treasures.
        List<Treasure> treasures = ResourceLoaderUtility.loadTreasures();
        
        // Load map.
        List<String> map = ResourceLoaderUtility.loadMap("game-map");
        MapScene mapScene = new MapScene(map, treasures, player);
        mapScene.toggle(); // Make map visible.

        // Make inventory scene.
        InventoryScene inventoryScene = generateInventoryScene(player.getInventory());

        // Start our game.
        GameTerminal gameTerminal = new GameTerminal(mapScene, inventoryScene, player, mob);

        try {
            gameTerminal.start();
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Helper method to generate inventory and inventory scene.
     *
     * @return
     */
    private static InventoryScene generateInventoryScene(Inventory inventory) {
        // Dummy inventory data.
        inventory.addMultiple(
                new Weapon("Sword of Stabbing", ItemList.SWORD, 10),
                new Weapon("Woodaxe", ItemList.HANDAXE, 7),
                new Armour("Breastplate", ItemList.ARMOUR, 12),
                new Item("Potion of Healing", ItemList.RED_POTION),
                new Item("Mythical Somnos' Spear", ItemList.SPEAR),
                new Item("Yellow Rock", ItemList.JUNK));

        // Set up navigtaion for inventory scene.
        Coordinates inventoryCoords = new Coordinates(
                CURSOR_X_START,
                CURSOR_Y_START,
                CURSOR_X_STEP,
                CURSOR_Y_STEP);

        Boundaries inventoryBounds = new Boundaries(
                CURSOR_X_START,
                CURSOR_Y_START,
                CURSOR_X_END,
                CURSOR_Y_END);

        Navigation inventoryNavigation = new Navigation(
                inventoryCoords,
                inventoryBounds);

        // Make inventory scene.
        InventoryScene inventoryScene = new InventoryScene(
                inventoryNavigation,
                inventory);

        return inventoryScene;
    }
}
