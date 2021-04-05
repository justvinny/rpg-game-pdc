/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.MAX_COLUMNS;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.MAX_WORD_LENGTH;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.N_ROW_DASHES;
import com.group.pdc_assignment_rpg.logic.Inventory;
import com.group.pdc_assignment_rpg.logic.Navigation;
import com.group.pdc_assignment_rpg.utilities.TextUtility;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the UI for our inventory on the terminal.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class InventoryScene extends Scene {

    private Inventory inventory;
    private Navigation navigation;

    public InventoryScene(Navigation navigation, Inventory inventory) {
        super();
        this.navigation = navigation;
        this.inventory = inventory;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<String> createScene() {
        StringBuilder builder = new StringBuilder();

        builder.append(makeHeader());

        int columnCounter = 0;
        for (int i = 0; i < inventory.getCapacity(); i++) {
            String item;
            if ((inventory.getInventory().size() - 1) >= i) {
                item = inventory.get(i).toString();
            } else {
                item = "Empty";
            }

            // Format our item by adding leading and trailing spaces to make our
            // inventory command line interface look organised and easy to read.
            String formattedItem = addLeadingTrailing(item, ' ');

            // Add item to builder.
            builder.append(formattedItem);

            columnCounter++;

            // Add new line to start at a new row.
            if (columnCounter == MAX_COLUMNS || i == inventory.getCapacity() - 1) {
                columnCounter = 0;
                builder.append("|\n");
                builder.append(makeRowDashes());
            }

        }

        return Arrays.asList(builder.toString().split("\n"));
    }

    /**
     * Helper method to make row dashes to separate rows in inventory.
     *
     * @return a row of dashes.
     */
    private String makeRowDashes() {
        return String.format("%s\n", TextUtility.repeatCharacter(N_ROW_DASHES, '-'));
    }

    private String addLeadingTrailing(String word, char character) {
        double nLeadingSpaces = Math.ceil((double) (MAX_WORD_LENGTH - word.length()) / 2.0);
        String leadingSpaces = TextUtility.repeatCharacter((int) nLeadingSpaces, character);

        // Add spaces on the right side of the item name for formatting.
        double nTrailingSpaces = Math.floor((double) (MAX_WORD_LENGTH - word.length()) / 2.0);
        String trailingSpaces = TextUtility.repeatCharacter((int) nTrailingSpaces, character);

        return String.format("|%s%s%s", leadingSpaces, word, trailingSpaces);
    }

    private String makeHeader() {
        String nLeadingSpaces = TextUtility.repeatCharacter(8, ' ');
        String title
                = nLeadingSpaces
                + " ___   __    _  __   __  _______  __    _  _______  _______  ______    __   __ \n"
                + nLeadingSpaces
                + "|   | |  |  | ||  | |  ||       ||  |  | ||       ||       ||    _ |  |  | |  |\n"
                + nLeadingSpaces
                + "|   | |   |_| ||  |_|  ||    ___||   |_| ||_     _||   _   ||   | ||  |  |_|  |\n"
                + nLeadingSpaces
                + "|   | |       ||       ||   |___ |       |  |   |  |  | |  ||   |_||_ |       |\n"
                + nLeadingSpaces
                + "|   | |  _    ||       ||    ___||  _    |  |   |  |  |_|  ||    __  ||_     _|\n"
                + nLeadingSpaces
                + "|   | | | |   | |     | |   |___ | | |   |  |   |  |       ||   |  | |  |   |  \n"
                + nLeadingSpaces
                + "|___| |_|  |__|  |___|  |_______||_|  |__|  |___|  |_______||___|  |_|  |___|  \n";

        return makeRowDashes() + title + makeRowDashes();
    }

    /**
     * Inventory navigation
     *
     * Note: Uses composition as not all scenes may implement all navigation
     * methods.
     */
    public void up() {
        navigation.up();
    }

    public void down() {
        navigation.down();
    }

    public void left() {
        navigation.left();
    }

    public void right() {
        navigation.right();
    }
}
