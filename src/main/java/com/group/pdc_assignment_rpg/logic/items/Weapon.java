package com.group.pdc_assignment_rpg.logic.items;

/**
 *
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
 */
public final class Weapon extends EquippableItem {

    private int damage;

    public Weapon(String n, ItemList i, int damage){
        super(n, i);
        this.setDamage(damage);
    }
    
    
    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    
}
