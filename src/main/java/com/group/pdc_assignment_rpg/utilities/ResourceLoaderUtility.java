package com.group.pdc_assignment_rpg.utilities;

import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.ItemList;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to for loading game maps from text files.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ResourceLoaderUtility {

    private static final String RESOURCE_PATH = "./resources";
    private static final String TREASURES_PATH = RESOURCE_PATH + "/treasures.txt";

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

    public static List<Treasure> loadTreasures() {
        File file = new File(TREASURES_PATH);

        List<Treasure> treasures = new ArrayList<>();     
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] item = line.split(",");
                String itemName = item[0];
                ItemList itemType = ItemList.valueOf(item[1].toUpperCase());
                int x = Integer.valueOf(item[2]);
                int y = Integer.valueOf(item[3]);
                Coordinates coordinates = new Coordinates(x,y);
                treasures.add(new Treasure(new Item(itemName, itemType), coordinates));
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
}
