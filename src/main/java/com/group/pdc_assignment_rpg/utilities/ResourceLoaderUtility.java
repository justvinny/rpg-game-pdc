package com.group.pdc_assignment_rpg.utilities;

import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.entities.EquipmentSlot;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Armour;
import com.group.pdc_assignment_rpg.logic.items.ConsumableItem;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.ItemList;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.logic.items.Weapon;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to for loading game maps from text files.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ResourceLoaderUtility {

    /**
     * Constants
     */
    private static final String RESOURCE_PATH = "./resources";
    private static final String TREASURES_PATH = RESOURCE_PATH + "/treasures.txt";
    private static final String PLAYER_LIST_PATH = RESOURCE_PATH + "/players.txt";
    private static final String PLAYER_INVENTORY_PATH = RESOURCE_PATH + "/player-inventories.txt";
    private static final String ITEM_LIST_PATH = RESOURCE_PATH + "/item-list.txt";
    private static final String EQUIPPED_ITEMS_PATH = RESOURCE_PATH + "/equipped.txt";

    /**
     * Method to load a map from a text file depending on map name argument
     * given.Once successfully loaded, the map will be stored in a list.
     *
     * @param mapName name of the map text file we're going to load.
     * @return a List collection containing a map for our game.
     * @throws InvalidMapException for loading invalid maps.
     */
    public static List<String> loadMap(String mapName) throws InvalidMapException {
        String fileName = RESOURCE_PATH + "/" + mapName + ".txt";
        File file = new File(fileName);

        List<String> map = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                map.add(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            // Ensures that the reader is closed.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

        // Check if file loaded is a valid map.
        if (!isValidMap(map)) {
            throw new InvalidMapException();
        }

        return map;
    }

    /**
     * Helper method to ensure the text file we're loading is a valid map.
     *
     * @param gameMap is a list we have to check whether it's a valid map or
     * not.
     * @return a boolean value on whether the map loaded is valid or not.
     */
    private static boolean isValidMap(List<String> gameMap) {
        // Check list is not empty and then get the length of the first index
        // which will be used to ensure that it is valid map that has the same
        // length for all the strings.
        int mapWidth = (gameMap.size() > 0) ? gameMap.get(0).length() : 0;

        // Compares length of each line in the map.
        return gameMap.stream().noneMatch(line -> (mapWidth != line.length()));
    }

    /**
     * Method to load the treasures located in a map which allows our player to
     * interact with it and pick them up.
     *
     * @return a list of treasures on the map.
     */
    public static List<Treasure> loadTreasures() {
        File file = new File(TREASURES_PATH);

        List<Treasure> treasures = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Convert the comma separated string into a treasure.
                String[] item = line.split(",");
                String itemName = item[0];
                int x = Integer.valueOf(item[1]);
                int y = Integer.valueOf(item[2]);
                Coordinates coordinates = new Coordinates(x, y);

                // Add the treasure to our list.
                Item itemToAdd = itemLoaderFactory(itemName);
                treasures.add(new Treasure(itemToAdd, coordinates));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

        return treasures;
    }

    /**
     * Load the player from the database.
     *
     * @param playerName
     * @return
     */
    public static Player loadPlayerFromDB(String playerName) {
        return loadAllPlayersFromDB().stream()
                .filter(p -> p.getName().equals(playerName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Loads all the players that exist in our database.
     *
     * @return a list of existing players.
     */
    public static List<Player> loadAllPlayersFromDB() {
        List<Player> players = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(PLAYER_LIST_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] playerData = line.split(",");
                String playerName = playerData[0];
                int playerLevel = Integer.valueOf(playerData[1]);

                // Player inventory.
                Inventory playerInventory = loadPlayerInventory(playerName);

                // Equip items
                List<String> playerEquipped = loadPlayerEquippedItems(playerName);

                if (!playerEquipped.get(0).equals("None")) {
                    playerInventory.getEquipment().put(EquipmentSlot.HAND,
                            playerInventory.getItem(playerEquipped.get(0)));
                }

                if (!playerEquipped.get(1).equals("None")) {
                    playerInventory.getEquipment().put(EquipmentSlot.ARMOUR,
                            playerInventory.getItem(playerEquipped.get(1)));
                }

                // Player coordinates.
                int playerX = Integer.valueOf(playerData[2]);
                int playerY = Integer.valueOf(playerData[3]);

                // Stat block
                int playerStrength = Integer.valueOf(playerData[4]);
                int playerDexterity = Integer.valueOf(playerData[5]);
                int playerIntellect = Integer.valueOf(playerData[6]);
                StatBlock playerStatBlock = new StatBlock(
                        playerStrength,
                        playerDexterity,
                        playerIntellect);

                // Make player
                Player player = new Player(
                        playerName,
                        playerLevel,
                        playerInventory,
                        playerX,
                        playerY,
                        playerStatBlock);

                players.add(player);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return players;
    }

    /**
     * Checks if player exists in our database.
     *
     * @param name of the player we need to search.
     * @return boolean value if there's a match or not.
     */
    public static boolean playerExists(String name) {
        return loadAllPlayersFromDB().stream()
                .anyMatch(p -> p.getName().equals(name));
    }

    /**
     * Write a new player or update an existing player to our database.
     */
    public static void writePlayerData(Player player) {
        List<Player> players = loadAllPlayersFromDB();

        // Check if player already exists.
        if (players.contains(player)) {
            // Delete the player.
            players.remove(player);
        }

        // Update player.
        players.add(player);

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(PLAYER_LIST_PATH));

            for (Player p : players) {
                printWriter.println(p.toCommaSeparatedString());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    /**
     * Loads all the inventory items of a player from the database using their
     * player name.
     *
     * @param playerName name of the player used to query the DB.
     * @return an Inventory of the player.
     */
    public static Inventory loadPlayerInventory(String playerName) {
        return Objects.requireNonNull(loadAllPlayerInventories().get(playerName));
    }

    /**
     * Loads all the inventories in the database.
     *
     * @return all the player inventories.
     */
    public static Map<String, Inventory> loadAllPlayerInventories() {
        Map<String, Inventory> inventories = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(PLAYER_INVENTORY_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] inventoryData = line.split(",");
                String playerNameDB = inventoryData[0];

                Inventory inventory = new Inventory();
                for (int i = 1; i < inventoryData.length; i++) {
                    inventory.add(itemLoaderFactory(inventoryData[i]));
                }

                inventories.put(playerNameDB, inventory);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return inventories;
    }

    /**
     * Write a new inventory or update an existing one.
     *
     * @param player the player who we wish to write the inventory data for.
     */
    public static void writeInventoryData(Player player) {
        Map<String, Inventory> inventories = loadAllPlayerInventories();

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(PLAYER_INVENTORY_PATH));

            // Update inventory of player.
            inventories.put(player.getName(), player.getInventory());

            for (String playerName : inventories.keySet()) {
                printWriter.println(inventories.get(playerName).toCommaSeparatedString(playerName));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    /**
     * Write a new data for player's equipped items or update an existing one.
     *
     * @param player the player who we wish to write the inventory data for.
     */
    public static void writeEquippedData(Player player) {
        Map<String, List<String>> equippedItems = loadAllPlayerEquippedItems();

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(EQUIPPED_ITEMS_PATH));

            // Update equpped items of player
            equippedItems.put(player.getName(), new ArrayList<>());
            equippedItems.get(player.getName()).add(player.getWeaponName());
            equippedItems.get(player.getName()).add(player.getArmourName());

            for (String playerName : equippedItems.keySet()) {
                List<String> equippedItemsPlayer = equippedItems.get(playerName);
                String weapon = equippedItemsPlayer.get(0);
                String armour = equippedItemsPlayer.get(1);
                printWriter.println(String.format("%s,%s,%s",
                        playerName, weapon, armour));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    /**
     * Loads the equipped items for the player using their name.
     *
     * @param playerName name of the player used to query the DB.
     * @return a list of the equipped items of the player.
     */
    public static List<String> loadPlayerEquippedItems(String playerName) {
        return Objects.requireNonNull(loadAllPlayerEquippedItems().get(playerName));
    }

    /**
     * Loads all the equipped items for players from the database.
     *
     * @return all the player equipped items.
     */
    public static Map<String, List<String>> loadAllPlayerEquippedItems() {
        Map<String, List<String>> equipped = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(EQUIPPED_ITEMS_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] equippedData = line.split(",");
                String playerNameDB = equippedData[0];

                equipped.put(playerNameDB, new ArrayList<>());
                equipped.get(playerNameDB).add(equippedData[1]);
                equipped.get(playerNameDB).add(equippedData[2]);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return equipped;
    }

    /**
     * Factory method to generate items of different types based on our
     * item-list database which uses the item name as the key.
     *
     * @param itemName name of the item.
     * @return an item that can either be a weapon, armour, consumable, etc.
     */
    public static Item itemLoaderFactory(String itemName) {
        Item item = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(ITEM_LIST_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] itemData = line.split(",");
                String itemNameDB = itemData[0];
                ItemList itemType = ItemList.valueOf(itemData[1].toUpperCase());

                // Create an item depending on type.
                if (itemNameDB.equals(itemName)) {
                    switch (itemType) {
                        case ARMOUR:
                            int protection = Integer.valueOf(itemData[2]);
                            item = new Armour(itemNameDB, itemType, protection);
                            break;
                        case RED_POTION:
                            int healing = Integer.valueOf(itemData[2]);
                            item = new ConsumableItem(itemNameDB, itemType, healing);
                            break;
                        case SWORD:
                        case HANDAXE:
                        case SPEAR:
                            int damage = Integer.valueOf(itemData[2]);
                            item = new Weapon(itemNameDB, itemType, damage);
                            break;
                        default:
                            item = new Item(itemNameDB, itemType);
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return item;
    }
}
