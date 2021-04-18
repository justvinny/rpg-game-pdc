package com.group.pdc_assignment_rpg;

import com.group.pdc_assignment_rpg.logic.navigation.Boundaries;
import com.group.pdc_assignment_rpg.logic.navigation.Navigation;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.ItemList;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.cli.GameTerminal;
import com.group.pdc_assignment_rpg.cli.InventoryScene;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_END;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_START;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_STEP;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_END;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_START;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_STEP;
import com.group.pdc_assignment_rpg.cli.MapScene;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.utilities.MapLoaderUtility;
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
        // Dummy player.
        Player player = new Player("Bob");

        // Dummy mob.
        Mob mob = new Mob("Red Slime");

        // Dummy map.
        List<String> map = MapLoaderUtility.loadMap("sample");
        MapScene mapScene = new MapScene(map, player);
        mapScene.toggle(); // Make map visible.

        // Make inventory scene.
        InventoryScene inventoryScene = generateInventoryScene();

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
    private static InventoryScene generateInventoryScene() {
        // Dummy inventory data.
        Inventory inventory = new Inventory();
        inventory.addMultiple(
                new Item("Sword of Stabbing", ItemList.SWORD),
                new Item("Woodaxe", ItemList.HANDAXE),
                new Item("Breastplate", ItemList.ARMOUR),
                new Item("Potion of Healing", ItemList.RED_POTION),
                new Item("Mythical Somnos' Spear", ItemList.SPEAR));

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
