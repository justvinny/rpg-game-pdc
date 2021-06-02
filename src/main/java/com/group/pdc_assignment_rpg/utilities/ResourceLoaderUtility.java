package com.group.pdc_assignment_rpg.utilities;

import static com.group.pdc_assignment_rpg.assets.MonsterLoader.BOSS_MOB;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.entities.Mob;

import com.group.pdc_assignment_rpg.logic.character.Level;
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
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Utility class to for loading game maps from text files.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ResourceLoaderUtility {
    
    
    /**
     * Constants
     */
    public static Connection conn = null;
    private static final String URL="jdbc:derby://localhost:1527/RPGDB;create=true";
    private static final String USERNAME="root";
    private static final String PASSWORD="010101";
    public static final ExecutorService DB_EXECUTOR = Executors.newFixedThreadPool(1);
    private static final String RESOURCE_PATH = "./resources";
    private static final String TREASURES_TABLE = "TREASURE";
    private static final String PLAYER_TABLE = "PLAYER";
    private static final String INVENTORY_TABLE = "INVENTORY";
    private static final String ITEM_LIST_TABLE = "ITEM";
    private static final String EQUIPPED_ITEMS_TABLE = "EQUIPMENT";
    private static final String MOBS_TABLE = "MOB";
    private static final String MOB_DROPS_TABLE = "MOBDROP";
    
    public static void establishConnection()
    {
        try{
            conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(URL+" connected...");
        }
        catch (SQLException ex) {
            System.err.println("Establish Connection - SQLException: " + ex.getMessage());
        }
    } 
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
        List<Treasure> treasures = new ArrayList<>();
        Statement statement1 = null;
        Statement statement2 = null;
        try{
            statement1 = conn.createStatement();
            statement2 = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement1.executeQuery("SELECT * FROM " + TREASURES_TABLE);
            ResultSet ms;
            while(rs.next()){
                ms = statement2.executeQuery("SELECT * FROM " + ITEM_LIST_TABLE + " WHERE ItemName = '" + rs.getString("ItemName") + "'");
                if(ms.next()){
                    treasures.add(new Treasure(createItem(ms.getString("ItemName")), new Coordinates(rs.getInt("PosX"), rs.getInt("PosY"))));
                }
            }
        } catch(SQLException ex){
            System.err.println("Load Treasure - SQLException: " + ex.getMessage());
        } finally{
            if(statement1!=null) {
                try {
                    conn.commit();
                    statement1.close();
                }
                catch(SQLException ex) {
                    System.err.println("Could not close query");
                }
            }
            if(statement2!=null) {
                try {
                     statement2.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
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
        Player player;
        Statement statement = null;
        try{
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + PLAYER_TABLE + " WHERE PlayerName = '" + playerName + "'");
            if(rs.next()){
                String name = rs.getString("PlayerName");
                Level level = Level.valueOf(rs.getString("Level"));
                Inventory inventory = loadPlayerInventory(playerName);
                inventory.setEquipment(loadPlayerEquippedItems(playerName));
                
                int x = rs.getInt("PosX");
                int y = rs.getInt("PosY");
                
                StatBlock stats = new StatBlock(   
                        rs.getInt("PlayerStrength"),
                        rs.getInt("PlayerDexterity"),
                        rs.getInt("PlayerIntellect"));
                
                player = new Player(
                        name,
                        level,
                        inventory,
                        x,
                        y,
                        stats);
            } else {
                player = new Player(playerName);
            }
        } catch (SQLException ex) {
            System.err.println("Load Player - SQLException: " + ex.getMessage());
            player = new Player(playerName);
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return player;
    }   

    public static List<Player> loadAllPlayersFromDB() {
        List<Player> players = new ArrayList<>();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + PLAYER_TABLE);
            while (rs.next()) {
                players.add(loadPlayerFromDB(rs.getString("PlayerName")));
            }
        } catch (SQLException ex) {
            System.err.println("Load All Players - SQL Exception: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return players;
    }
    
    public static boolean playerExists(String playerName){
        boolean exists = false;
        Statement statement = null;
        try{
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + PLAYER_TABLE + " WHERE PlayerName = '" + playerName + "'");
            if(rs.next()){
                exists = true;
            }
        } catch (SQLException ex){
            
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return exists;
    }
    
    /**
     * Write a new player or update an existing player to our database.
     */
    public static void writePlayerData(Player player) {
        Statement statement = null;
        try{
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + PLAYER_TABLE
                    + " WHERE PlayerName = '" + player.getName() + "'");
            if (rs.next()){ // Check if player exists
                statement.executeUpdate("UPDATE " + PLAYER_TABLE 
                    + " set posx = " + player.getX() 
                    + ", posy = " + player.getY()  
                    + ", PlayerStrength = " + player.getStats().getStrength() 
                    + ", PlayerDexterity = " + player.getStats().getDexterity() 
                    + ", PlayerIntellect = " + player.getStats().getIntellect() 
                    + ", Level = '" + player.getLevel().toString()
                    + "' WHERE PlayerName = '" + player.getName() + "'");
            } else{
                statement.executeUpdate("INSERT INTO " + PLAYER_TABLE + " VALUES ('" 
                    + player.getName() 
                    + "'," + player.getX() 
                    + "," + player.getY()  
                    + "," + player.getStats().getStrength() 
                    + "," + player.getStats().getDexterity() 
                    + "," + player.getStats().getIntellect() 
                    + ",'" + player.getLevel().toString()
                    + "')");
            }
                writeInventoryData(player);
                writeEquippedData(player);
            
        } catch (SQLException ex) {
            System.err.println("Write Player - SQLException: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
    }

    /**
     * Write a new inventory or update an existing one.
     *
     * @param player the player who we wish to write the inventory data for.
     */
    public static void writeInventoryData(Player player) {
        List<Item> inventory = player.getInventory().getAllItems();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + INVENTORY_TABLE + " WHERE PlayerName = '" + player.getName() + "'");
            if (rs.next()){
                statement.executeUpdate("DELETE FROM " + INVENTORY_TABLE + " WHERE PlayerName ='" + player.getName() + "'");
            }
            for (Item item : inventory){
                int amount = player.getInventory().getAmount(item);
                statement.executeUpdate("INSERT INTO " + INVENTORY_TABLE + " VALUES('" + player.getName() + "','" + item.getName() + "', " + amount + ")");   System.out.println(amount);
            }
        } catch(SQLException ex){
            System.err.println("Write Inventory - SQLException: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
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
        Inventory inventory = new Inventory();
        Statement statement1 = null;
        Statement statement2 = null;
        try {
            statement1 = conn.createStatement();
            statement2 = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement1.executeQuery("SELECT * FROM " + INVENTORY_TABLE + " WHERE PlayerName = '" + playerName + "'");
            ResultSet ms;
            if(rs.next()){
                do {
                    ms = statement2.executeQuery("SELECT * FROM " + ITEM_LIST_TABLE + " WHERE ItemName = '" + rs.getString("ItemName") + "'");
                    if(ms.next()){
                        inventory.add(createItem(ms.getString("ItemName")), rs.getInt("Quantity"));
                    }
                } while(rs.next());
            } 
        } catch (SQLException ex) {
            System.err.println("Load Inventory - SQLException: " + ex.getMessage());
        } finally{
            if(statement1!=null) {
                try {
                    conn.commit();
                    statement1.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
            if(statement2!=null) {
                try {
                     statement2.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return inventory;
    }

    /**
     * Write a new data for player's equipped items or update an existing one.
     *
     * @param player the player who we wish to write the inventory data for.
     */
    public static void writeEquippedData(Player player) {
        EnumMap <EquipmentSlot, Item> equipment = player.getInventory().getEquipment();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + EQUIPPED_ITEMS_TABLE + " WHERE PlayerName = '" + player.getName() + "'");
            if(rs.next()){
                statement.executeUpdate("DELETE FROM " + EQUIPPED_ITEMS_TABLE + " WHERE PlayerName ='" + player.getName() + "'");
            }
            if(equipment.get(EquipmentSlot.HAND) != null){
                statement.executeUpdate("INSERT INTO " + EQUIPPED_ITEMS_TABLE + " VALUES('" + player.getName() + "','hand', '" + equipment.get(EquipmentSlot.HAND).getName() + "')");            
            } else {
                statement.executeUpdate("INSERT INTO " + EQUIPPED_ITEMS_TABLE + " VALUES('" + player.getName() + "','hand', " + null + ")");            
            }
            if(equipment.get(EquipmentSlot.ARMOUR) != null){
                statement.executeUpdate("INSERT INTO " + EQUIPPED_ITEMS_TABLE + " VALUES('" + player.getName() + "','armour', '" + equipment.get(EquipmentSlot.ARMOUR).getName() + "')"); 
            } else {
                statement.executeUpdate("INSERT INTO " + EQUIPPED_ITEMS_TABLE + " VALUES('" + player.getName() + "','armour'," + null + ")");            
            }
        } catch(SQLException ex) {
            System.err.println("Write Equipment - SQLException: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
    }

    /**
     * Loads the equipped items for the player using their name.
     *
     * @param playerName name of the player used to query the DB.
     * @return a list of the equipped items of the player.
     */
    public static EnumMap<EquipmentSlot, Item> loadPlayerEquippedItems(String playerName) {
        EnumMap<EquipmentSlot, Item> equipment = new EnumMap(EquipmentSlot.class);
        Statement statement1 = null;
        Statement statement2 = null;
        try {
            statement1 = conn.createStatement();
            statement2 = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet hand = statement1.executeQuery("SELECT * FROM " + EQUIPPED_ITEMS_TABLE + " WHERE PlayerName = '" + playerName + "' AND ItemSlot = 'hand'");
            ResultSet body = statement2.executeQuery("SELECT * FROM " + EQUIPPED_ITEMS_TABLE + " WHERE PlayerName = '" + playerName + "' AND ItemSlot = 'body'");

            if(hand.next()){
                if(!hand.getString("ItemName").equals("None")){
                    equipment.put(EquipmentSlot.HAND, createItem(hand.getString("ItemName")));
                }
            } 
            if(body.next()){
                if(!body.getString("ItemName").equals("None")){
                    equipment.put(EquipmentSlot.ARMOUR, createItem(body.getString("ItemName")));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Load Equipment - SQLException: " + ex.getMessage());
        } finally{
            if(statement1!=null) {
                try {
                    conn.commit();
                    statement1.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
            if(statement2!=null) {
                try {
                    conn.commit();
                    statement2.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return equipment;
    }

    public static Item createItem(String name){
        Item item = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + ITEM_LIST_TABLE + " WHERE ItemName = '" + name + "'");
            if(rs.next()){
                String itemname = rs.getString("ItemName");
                ItemList itemtype = ItemList.valueOf(rs.getString("ItemType").toUpperCase());
                int power = rs.getInt("Power");
                switch(itemtype){
                    case SWORD: item = new Weapon(itemname, itemtype, power); return item;
                    case HANDAXE: item = new Weapon(itemname, itemtype, power); return item;
                    case ARMOUR: item = new Armour(itemname, itemtype, power); return item;
                    case RED_POTION: item = new ConsumableItem(itemname, itemtype, power); return item;
                    default: item = new Item(itemname, itemtype); return item;
                }
            }
        } catch (SQLException ex){
            System.err.println("Create Item - SQLException: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return item;
    }

    /**
     * Loads a mob from the database.
     *
     * @return all the player equipped items.
     */
    public static Mob loadMobFromDB(String mobName) {
        Mob mob = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + MOBS_TABLE + " WHERE MobName = '" + mobName + "'");
            if(rs.next()){
                StatBlock stats = new StatBlock(rs.getInt("MobStrength"), rs.getInt("MobDexterity"), rs.getInt("MobIntellect"));
                String name = rs.getString("MobName");
                Level level = Level.valueOf("L" + rs.getString("MobLevel"));
                if (mobName.equals(BOSS_MOB)) {
                        mob = new Mob(name, 'B', level.getLvl(), 86, 2, stats);
                    } else {
                        mob = new Mob(name, 'R', level.getLvl(), stats);
                    }
                
            }
        } catch (SQLException ex){
            System.err.println("Load Mob - SQLException: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
       
        return mob;
    }
    
    public static Map<String, Mob> loadAllMobs() {
        Map<String, Mob> mobMap = new HashMap<>();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + MOBS_TABLE);
            while (rs.next()) {
                Mob mob = loadMobFromDB(rs.getString("MobName"));
                mobMap.put(rs.getString("MobName"), mob);
            }
        } catch (SQLException ex) {
            System.err.println("Load All Mobs - SQL Exception: " + ex.getMessage());
        } finally{
            if(statement!=null) {
                try {
                    conn.commit();
                    statement.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        return mobMap;
    }

    /**
     * Method to load the possible mob drops that a player can obtain once they
     * win in combat.
     *
     * @param mobName name of mob the player is battling.
     * @return items that the player will get.
     */
    public static Inventory loadMobDrops(String mobName) {
        Inventory inventory = new Inventory();
        Statement statement1 = null;
        Statement statement2 = null;
        try {
            statement1 = conn.createStatement();
            statement2 = conn.createStatement();
            conn.setAutoCommit(false);
            ResultSet rs = statement1.executeQuery("SELECT * FROM " + MOB_DROPS_TABLE + " WHERE MobName = '" + mobName + "'");
            ResultSet ms;
            while(rs.next()){
                ms = statement2.executeQuery("SELECT * FROM " + ITEM_LIST_TABLE + " WHERE ItemName = '" + rs.getString("ItemName") + "'");
                if (ms.next()){
                    Item item = createItem(ms.getString("ItemName"));
                    item.setDropRate(rs.getInt("Probability"));
                    inventory.add(item);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Load Drops - SQLException: " + ex.getMessage());
        } finally{
            if(statement1!=null) {
                try {
                    conn.commit();
                    statement1.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
            if(statement2!=null) {
                try {
                    conn.commit();
                    statement2.close();
                }
                catch(SQLException ex) {
                      System.err.println("Could not close query");
                }
            }
       }
        
        return inventory;
    }
}
