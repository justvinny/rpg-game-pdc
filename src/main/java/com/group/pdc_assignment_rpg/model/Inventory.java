package com.group.pdc_assignment_rpg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Model for the player's inventory where we store items and equipment from the
 * game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Inventory {

    /**
     * Constants
     */
    private final int MAX_INVENTORY_CAPACITY = 21;
    private final List<String> inventory;

    /**
     * Fields
     */
    private int capacity;
    private boolean visible;

    public Inventory() {
        inventory = new ArrayList<>();
        capacity = MAX_INVENTORY_CAPACITY;
        visible = false;
    }

    /**
     * Getter methods
     *
     */
    public int getCapacity() {
        return capacity;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Add an item to the inventory.
     *
     * @param itemName is the name of the item
     */
    public void add(String itemName) {
        if (inventory.size() <= capacity) {
            inventory.add(itemName);
        }
    }

    /**
     * Use a string separated by double commas for adding multiple items.
     *
     * @param itemNames list of items separated by two commas.
     */
    public void addMultiple(String itemNames) {
        String[] items = itemNames.split(",,");

        if (items.length <= 0) {
            return;
        }

        inventory.addAll(Arrays.asList(items));
    }

    /**
     * Gets an item in the inventory based on index given.
     *
     * @param index of the item in the inventory.
     * @return the item
     */
    public String get(int index) {
        return inventory.get(index);
    }

    /**
     * Toggles the visibility of the inventory on or off.
     */
    public void toggle() {
        if (!visible) {
            visible = true;
        } else {
            visible = false;
        }
    }

}
