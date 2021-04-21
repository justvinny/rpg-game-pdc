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
import java.util.Scanner;

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
        try {
            // Load player and start screen.
            Player player = loadStartScreen();

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
            // Message that says game terminal is loaded.
            System.out.println("Game terminal is now running! Check the new popup window.");
            gameTerminal.start();

        } catch (IOException | InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Helper method to initialise our start screen which asks the user
     * for their player name. Then it checks the name in our database and
     * loads the player if it exists, otherwise, it will create a new player.
     * @return either a new player or a saved player.
     * @throws InterruptedException 
     */
    private static Player loadStartScreen() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to our RPG game!");
        System.out.println("What is your name?");
        String playerName = scanner.nextLine();

        // Default if no records exist of player.
        Player player = new Player(playerName);
        // Add default items for new character.
        player.getInventory().addMultiple(
            ResourceLoaderUtility.itemLoaderFactory("Broken Sword"),
            ResourceLoaderUtility.itemLoaderFactory("Tattered Clothing"),
            ResourceLoaderUtility.itemLoaderFactory("Potion of Healing"));
        
        // Check if player exists in the system.
        // Load the player from the DB if it exists.
        // Otherwise, create a new player.
        if (ResourceLoaderUtility.playerExists(playerName)) {
            player = Player.loadPlayerFactory(playerName);
            System.out.println("Welcome back!");
            System.out.println("Loading player...");
        } else {
            ResourceLoaderUtility.writePlayerData(player);
            ResourceLoaderUtility.writeInventoryData(player);
            System.out.println("Creating new player...");
        }

        System.out.println("Loading game terminal...");
        Thread.sleep(1000); // Adds delay to emulate loading screen.
        return player;
    }

    /**
     * Helper method to generate inventory and inventory scene. This helps
     * set up the navigation for the inventory and make sure the cursor
     * does not go out of bounds.
     *
     * @return
     */
    private static InventoryScene generateInventoryScene(Inventory inventory) {
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
