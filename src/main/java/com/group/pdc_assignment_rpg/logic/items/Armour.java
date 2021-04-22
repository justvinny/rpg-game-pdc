package com.group.pdc_assignment_rpg.logic.items;

/**
 *
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 */
public final class Armour extends EquippableItem {
    private int protection;

    public Armour(String n, ItemList i, int protection){
        super(n, i);
        this.setProtection(protection);
    }
    
    
    /**
     * @return the protection
     */
    public int getProtection() {
        return protection;
    }

    /**
     * @param protection the protection to set
     */
    public void setProtection(int protection) {
        this.protection = protection;
    }
    
}
