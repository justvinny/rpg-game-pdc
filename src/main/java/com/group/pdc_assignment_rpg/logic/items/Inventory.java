package com.group.pdc_assignment_rpg.logic.items;

import com.group.pdc_assignment_rpg.logic.entities.EquipmentSlot;
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
    private EnumMap<EquipmentSlot, Item> equipment;

    public Inventory() {
        this.setInventory(new HashMap<Item, Integer>());
        this.setCapacity(MAX_INVENTORY_CAPACITY);
        this.equipment = new EnumMap<EquipmentSlot, Item>(EquipmentSlot.class);
        this.blankEquipment();
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
        this.blankEquipment();
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

    public EnumMap<EquipmentSlot, Item> getEquipment() {
        return equipment;
    }

    public Item getItem(EquipmentSlot e) {
        return this.getEquipment().get(e);
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

    public void setEquipment(EnumMap<EquipmentSlot, Item> equipment) {
        this.equipment = equipment;
    }

    public void blankEquipment() {
        this.equipment.put(EquipmentSlot.ARMOUR, null);
        this.equipment.put(EquipmentSlot.HAND, null);
    }

    public void setEquip(EquipmentSlot e, Item i) {
        this.equipment.put(e, i);
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
            if (inventory.put(item, 1) == null) {
                inventory.put(item, inventory.get(item) + 1);
            }
        }
    }

    public void add(Item item, int amount) {
        if (inventory.size() <= capacity) {
            if (inventory.put(item, amount) == null) {
                inventory.put(item, inventory.get(item) + amount);
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

    /**
     * Equips an item from the inventory.
     *
     * @param item to equip.
     * @return confirmation of equip.
     */
    public boolean equip(Item item) {
        if (item instanceof EquippableItem) {
            if (item instanceof Armour) {
                this.setEquip(EquipmentSlot.ARMOUR, item);
                return true;
            } else if (item instanceof Weapon) {
                this.setEquip(EquipmentSlot.HAND, item);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void unequip(EquipmentSlot e) {
        this.setEquip(e, null);
    }

    public void unequip(Item item) {
        if (item instanceof Weapon) {
            this.setEquip(EquipmentSlot.HAND, null);
        } else if (item instanceof Armour) {
            this.setEquip(EquipmentSlot.ARMOUR, null);
        }
    }
}
