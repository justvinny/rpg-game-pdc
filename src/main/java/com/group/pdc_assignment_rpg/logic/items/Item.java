package com.group.pdc_assignment_rpg.logic.items;

import java.io.IOException;
import java.util.Objects;

/**
 * These items get directly added into a creature's inventory. They are an
 * extension of the StaticObject object which holds their position in the world.
 *
 * @author Macauley Cunningham - 19072621
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Item {

    private static final int MAX_NAME_LENGTH = 23;

    private String name;
    private ItemList item;
    private int dropRate;

    public Item(String n, ItemList i, int dropRate) {
        try {
            this.setName(n);
        } catch (IOException e) {
            this.name = i.toString();
        }
        this.setItem(i);
        this.dropRate = dropRate;
    }

    public Item(String n, ItemList i) {
        this(n, i, 0);
    }

    public String getName() {
        return name;
    }

    public ItemList getItem() {
        return item;
    }

    public int getDropRate() {
        return dropRate;
    }

    public void setDropRate(int dropRate) {
        this.dropRate = dropRate;
    }
    
    private void setName(String name) throws IOException {
        if (name.length() < MAX_NAME_LENGTH) {
            this.name = name;
        } else {
            throw (new IOException("Name too long, exceeds character limit"));
        }
    }

    private void setItem(ItemList item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.item);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return this.item == other.item;
    }

}
