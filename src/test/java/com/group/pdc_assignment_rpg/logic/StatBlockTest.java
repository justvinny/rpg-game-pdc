package com.group.pdc_assignment_rpg.logic;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public class StatBlockTest {
    
    /**
     * Test of getVitality method, of class StatBlock.
     */
    @Test
    public void testGetVitality() {
        System.out.println("getVitality");
        StatBlock instance = new StatBlock();
        
        instance.writeStat(Stats.STRENGTH, 5);
        
        // Ensure vitality is calculated correctly
        // Calculation: 1 + (5 - 5) = 1
        int expResult = 1;
        int result = instance.getVitality();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndurance method, of class StatBlock.
     */
    @Test
    public void testGetEndurance() {
        System.out.println("getEndurance");
        StatBlock instance = new StatBlock();
        
        instance.writeStat(Stats.DEXTERITY, 5);
        
        // Ensure endurance is calculated correctly
        // Calculation: 1 + (5 - 5) = 1
        int expResult = 1;
        int result = instance.getEndurance();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWillpower method, of class StatBlock.
     */
    @Test
    public void testGetWillpower() {
        System.out.println("getWillpower");
        StatBlock instance = new StatBlock();
        
        instance.writeStat(Stats.INTELLECT, 5);
        
        // Ensure willpower is calculated correctly
        // Calculation: 1 + (5 - 5) = 1
        int expResult = 1;
        int result = instance.getWillpower();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateHealth method, of class StatBlock.
     */
    @Test
    public void testCalculateHealth() {
        System.out.println("calculateHealth");
        StatBlock instance = new StatBlock();
        
        // Set applicable stats to explicit values
        instance.writeStat(Stats.STRENGTH, 5);
        instance.writeStat(Stats.DEXTERITY, 5);
        
        // Ensure health is calculated correctly
        // 5 + ((1 + (5 - 5)) * 10) + ((1 + (5 - 5)) * 5) 
        // 5 + (1 * 10) + (1 * 5) = 20
        int expResult = 20;
        
        int result = instance.calculateHealth();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateStamina method, of class StatBlock.
     */
    @Test
    public void testCalculateStamina() {
        System.out.println("calculateStamina");
        StatBlock instance = new StatBlock();
        
        // Set applicable stats to explicit values
        instance.writeStat(Stats.DEXTERITY, 5);
        instance.writeStat(Stats.INTELLECT, 5);
        
        // Ensure stamina is calculated correctly
        // 5 + ((1 + (5 - 5)) * 10) + ((1 + (5 - 5)) * 5) 
        // 5 + (1 * 10) + (1 * 5) = 20
        int expResult = 20;
        
        int result = instance.calculateStamina();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateWill method, of class StatBlock.
     */
    @Test
    public void testCalculateWill() {
        System.out.println("calculateWill");
        StatBlock instance = new StatBlock();
        
        // Set applicable stats to explicit values
        instance.writeStat(Stats.INTELLECT, 5);
        instance.writeStat(Stats.STRENGTH, 5);
        
        // Ensure will is calculated correctly
        // 5 + ((1 + (5 - 5)) * 10) + ((1 + (5 - 5)) * 5) 
        // 5 + (1 * 10) + (1 * 5) = 20
        int expResult = 20;
        
        int result = instance.calculateWill();
        assertEquals(expResult, result);
    }
    
}
