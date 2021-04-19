package com.group.pdc_assignment_rpg.logic.items;

import java.util.*;

/**
 * Where a creature's inventory is stored, holds item and equipment that are
 * used during gameplay.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 */
public class Inventory {

    private final int MAX_INVENTORY_CAPACITY = 21;

    private Map<Item, Integer> inventory;
    private int capacity;

    public Inventory() {
        this.setInventory(new HashMap<Item, Integer>());
        this.setCapacity(MAX_INVENTORY_CAPACITY);
    }

    /**
     * Future-proofing: An integer will be assumed to be the creature's
     * individual carrying capacity. Always a multiple of 7.
     *
     * @param c = capacity
     */
    public Inventory(int c) {
        this.setInventory(new HashMap<Item, Integer>());
        this.setCapacity(c);
    }

    /**
     * Getters
     *
     */
    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public int size() {
        return inventory.size();
    }

    /**
     * Setters
     *
     */
    public void setInventory(Map<Item, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Item in inventory methods
     *
     */
    public int getAmount(Item i) {
        return inventory.get(i);
    }

    /**
     * Add an item to the inventory.
     *
     * @param item is the item to add to the inventory.
     */
    public void add(Item item) {
        if (inventory.size() <= capacity) {
            if (item != null) {
                if (inventory.put(item, 1) == null) {
                    inventory.put(item, inventory.get(item) + 1);
                }
            }

        }
    }

    public void add(Item item, int amount) {
        if (inventory.size() <= capacity) {
            if (item != null) {
                if (inventory.put(item, amount) == null) {
                    inventory.put(item, inventory.get(item) + amount);
                }
            }
        }
    }

    /**
     * Input should be any number of Items, only able to add one of each at a
     * time
     *
     * @param items
     */
    public void addMultiple(Item... items) {
        for (int i = 0; i < items.length; i++) {
            this.add(items[i]);
        }
    }

    /**
     * Gets an item in the inventory based on index given.
     *
     * @param index of the item in the inventory.
     * @return the item
     */
    public Item get(int index) {
        Item[] items = inventory.keySet().toArray(new Item[inventory.size()]);

        return items[index];
    }

    /**
     * Removes an item from the inventory
     *
     * @param item to remove
     * @return previous mapping of the code or null
     */
    public int remove(Item item) {
        return inventory.remove(item);
    }
}
