/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.model.Inventory;
import com.group.pdc_assignment_rpg.utilities.TextUtility;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class InventoryView {

    public static final int ITEM_ROW_START = 9;
    public static final int ITEM_ROW_END = 21;
    public static final int ITEM_ROW_INCREMENT = 2;
    public static final int ITEM_COL_START = 2;
    public static final int ITEM_COL_INCREMENT = 31;
    public static final int ITEM_COL_END = ITEM_COL_START + 31 * 2;

    private static final int MAX_WORD_LENGTH = 30;
    private static final int MAX_COLUMNS = 3;
    private static final int N_ROW_DASHES = ((MAX_WORD_LENGTH + 1) * MAX_COLUMNS) + 1;

    private Inventory inventory;
    private int x, y;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        this.x = ITEM_COL_START;
        this.y = ITEM_ROW_START;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String makeView() {
        StringBuilder builder = new StringBuilder();

        builder.append(makeHeader());

        int columnCounter = 0;
        for (int i = 0; i < inventory.getCapacity(); i++) {
            String item;
            if ((inventory.getInventory().size() - 1) >= i) {
                item = inventory.get(i);
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

        return builder.toString();
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

    public void moveRight() {
        if (x < ITEM_COL_END) {
            x += ITEM_COL_INCREMENT;
        }
    }

    public void moveLeft() {
        if (x > ITEM_COL_START) {
            x -= ITEM_COL_INCREMENT;
        }
    }

    public void moveUp() {
        if (y > ITEM_ROW_START) {
            y -= ITEM_ROW_INCREMENT;
        }
    }

    public void moveDown() {
        if (y < ITEM_ROW_END) {
            y += ITEM_ROW_INCREMENT;
        }
    }
}
