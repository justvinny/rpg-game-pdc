package com.group.pdc_assignment_rpg;

import com.group.pdc_assignment_rpg.cli.GameTerminal;
import com.group.pdc_assignment_rpg.cli.InventoryScene;
import com.group.pdc_assignment_rpg.cli.InventorySceneConstants;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.*;
import com.group.pdc_assignment_rpg.utilities.MapLoaderUtility;
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
        // Dummy map.
        List<String> map = MapLoaderUtility.loadMap("sample");

        // Dummy player.
        Player player = new Player("Bob");

        // Dummy mob.
        Mob mob = new Mob("Red Slime");

        // Make inventory scene.
        InventoryScene inventoryScene = generateInventoryScene();
        
        // Start our game.
        GameTerminal.start(map, inventoryScene, player, mob);
    }

    /**
     * Helper method to generate inventory and inventory scene.
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
                new Item("Mythical Andromeda's Spear", ItemList.SPEAR));

        // Make inventory scene.
        Coordinates inventoryCoords = new Coordinates(
                InventorySceneConstants.CURSOR_X_START,
                InventorySceneConstants.CURSOR_Y_START,
                InventorySceneConstants.CURSOR_X_STEP,
                InventorySceneConstants.CURSOR_Y_STEP);

        Boundaries inventoryBounds = new Boundaries(
                InventorySceneConstants.CURSOR_X_START,
                InventorySceneConstants.CURSOR_Y_START,
                InventorySceneConstants.CURSOR_X_END,
                InventorySceneConstants.CURSOR_Y_END);

        InventoryScene inventoryScene = new InventoryScene(
                inventoryCoords,
                inventoryBounds,
                inventory);
        
        return inventoryScene;
    }
}
