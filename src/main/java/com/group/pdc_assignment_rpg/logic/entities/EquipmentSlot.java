/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.entities;

import com.group.pdc_assignment_rpg.utilities.TextUtility;

/**
 *
 * @author Macauley
 */
public enum EquipmentSlot {
    ARMOUR, HAND;
    
    public String toString(){
        return this.name().toLowerCase();
    }
}
