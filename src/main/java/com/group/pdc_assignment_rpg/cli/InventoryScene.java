/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.ITEM_X_STEP;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.ITEM_Y_STEP;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.MAX_COLUMNS;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.MAX_WORD_LENGTH;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.N_ROW_DASHES;
import com.group.pdc_assignment_rpg.logic.entities.EquipmentSlot;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Armour;
import com.group.pdc_assignment_rpg.logic.items.ConsumableItem;
import com.group.pdc_assignment_rpg.logic.items.EquippableItem;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.Weapon;
import com.group.pdc_assignment_rpg.logic.navigation.Navigation;
import com.group.pdc_assignment_rpg.utilities.TextUtility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the UI for our inventory on the terminal.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class InventoryScene extends Scene {

    private Player player;
    private List<Item> itemsPrinted;
    private Inventory inventory;
    private Navigation navigation;
    private String actionMessage;
    private int itemSelected;

    /**
     * Constructor for inventory.
     *
     * @param navigation - allows navigation in our inventory so we can select
     * an item.
     * @param player - model for player which stores the inventory data we need.
     * logic.
     */
    public InventoryScene(Navigation navigation, Player player) {
        super();
        this.player = player;
        this.itemsPrinted = new ArrayList<>();
        this.navigation = navigation;
        this.inventory = player.getInventory();
        this.actionMessage = "\n";
        this.itemSelected = 0;
    }

    /*
     * Getters 
     */
    public Navigation getNavigation() {
        return navigation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the item currently pointed by the cursor in the inventory. Returns
     * null if the cursor is at an empty slot of the inventory.
     *
     * @return the selected item in the inventory.
     */
    public Item getSelectedItem() {
        return (itemSelected < itemsPrinted.size()) ? itemsPrinted.get(itemSelected) : null;
    }

    /**
     * Resets actionMessage to default.
     */
    public void resetActionMessage() {
        actionMessage = "\n";
    }

    /**
     * Combines all the elements needed to make our Inventory Scene that the
     * user will see in the game.
     *
     * @return the inventory scene.
     */
    @Override
    public List<String> createScene() {
        StringBuilder builder = new StringBuilder();

        builder.append(makeHeader());

        itemsPrinted.clear();
        int columnCounter = 0;
        for (int i = 0; i < inventory.getCapacity(); i++) {
            String item;
            if ((inventory.getInventory().size() - 1) >= i) {
                item = inventory.get(i).toString();
                itemsPrinted.add(inventory.get(i));

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

        // Show what the player currently has equipped.
        builder.append(makeEquipmentDisplayBox());

        // Add description box. 
        builder.append(makeDescriptionBox(getSelectedItem()));

        // Add available keys.
        builder.append(printAvailableKeys());

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

    /**
     * Helper method to add x amount of characters before and after a string for
     * UI purposes.
     *
     * @param word string we want to add characters to.
     * @param character the character we want to add. For example, a hash #.
     * @return beautified string that suits our UI theme.
     */
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
     * Prints out the currently equipped items to the inventory UI.
     *
     * @return the currently equipped items.
     */
    private String makeEquipmentDisplayBox() {
        Item weapon = inventory.getEquipment().get(EquipmentSlot.HAND);
        Item armour = inventory.getEquipment().get(EquipmentSlot.ARMOUR);
        String weaponString = (weapon == null) ? "None" : weapon.toString();
        String armourString = (armour == null) ? "None" : armour.toString();
        String equipped = String.format("Equipped:\n Weapon - %s\n Armour - %s\n",
                weaponString, armourString);

        return makeRowHashes() + equipped;
    }

    /**
     * Prints out our description box for an item currently hovered on by our
     * cursor.
     *
     * @param item
     * @return
     */
    private String makeDescriptionBox(Item item) {
        String description;

        if (item == null) {
            description = " This is an empty slot.\n";
        } else {
            // Add [Equipped] to message if the item is already equipped.
            String equipped = "";
            if (inventory.getEquipment().values().contains(item)) {
                equipped = "[Equipped] ";
            }

            // Add extra stats if item is weapon or armour.
            String extraStats = "";
            if (item instanceof Weapon) {
                extraStats = " | Damage: " + ((Weapon) item).getDamage();
            } else if (item instanceof Armour) {
                extraStats = " | Protection: " + ((Armour) item).getProtection();
            }

            description = String.format(" %s%s | Type: %s | Weight: %d Value: %d%s\n",
                    equipped, item.getName(), item.getItem().getType(),
                    item.getItem().getWeight(), item.getItem().getValue(),
                    extraStats);
        }

        return makeRowHashes() + description + actionMessage + makeRowHashes();
    }

    /**
     * Helper method for making a row of hashes for our description box.
     *
     * @return a row of hashes.
     */
    private String makeRowHashes() {
        return String.format("%s\n", TextUtility.repeatCharacter(N_ROW_DASHES, '#'));
    }

    private String printAvailableKeys() {
        return "\nKeys: [U] - Use   [D] - Drop   [I] - Close Inventory   [Esc] - Exit Game\n"
                + "Navigation: Arrow Keys - Up, Down, Left, Right\n";
    }

    /**
     * Inventory navigation
     *
     * Note: Uses composition as not all scenes may implement all navigation
     * methods.
     */
    public void up() {
        if (navigation.up()) {
            itemSelected -= ITEM_Y_STEP;
        }
    }

    public void down() {
        if (navigation.down()) {
            itemSelected += ITEM_Y_STEP;
        }
    }

    public void left() {
        if (navigation.left()) {
            itemSelected -= ITEM_X_STEP;
        }
    }

    public void right() {
        if (navigation.right()) {
            itemSelected += ITEM_X_STEP;
        }
    }

    /**
     * Use will consume an item if it is consumable or equip it if it is an
     * equipment. Selecting an empty inventory slot will do nothing and prints
     * an appropriate message.
     */
    public void use() {
        Item item = getSelectedItem();

        if (item != null) {
            if (item instanceof EquippableItem) {
                if (inventory.getEquipment().values().contains(item)) {
                    inventory.unequip(item);
                    actionMessage = String.format(" %s unequipped!\n", item.getName());
                } else if (inventory.equip(item)) {
                    actionMessage = String.format(" %s equipped!\n", item.getName());
                }
            } else {
                if (item instanceof ConsumableItem) {
                    if (item.getName().equals("Potion of Healing")) {
                        player.heal(((ConsumableItem) item).getSpecialValue());
                        inventory.remove(item);
                    }
                }
                actionMessage = String.format(" %s used!\n", item.getName());
            }
        } else {
            actionMessage = " No item selected. Cannot use!\n";
        }
    }

    /**
     * Drop will delete an item from the inventory permanently. Same as use,
     * drop will not work on an empty slot and will print an appropriate message
     * that tells you so.
     */
    public void drop() {
        Item item = getSelectedItem();

        if (item != null) {
            actionMessage = String.format(" %s dropped!\n", item.getName());
            inventory.unequip(item); // Unequip if selected item is equipped.
            inventory.remove(item); // Delete the item.
        } else {
            actionMessage = " No item selected. Cannot drop!\n";
        }

    }

}
