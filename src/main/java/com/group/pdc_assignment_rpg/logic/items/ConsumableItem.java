package com.group.pdc_assignment_rpg.logic.items;

/**
 * Consumables items are one use items such as Red Potions with
 * special effects. In the Potion of Healing's case, it heals
 * the player's HP by 10.
 * 
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com> 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class ConsumableItem extends Item {

    private int specialValue;
    
    public ConsumableItem(String n, ItemList i, int specialValue) {
        super(n, i);
        this.specialValue = specialValue;
    }

    public int getSpecialValue() {
        return specialValue;
    }
}
