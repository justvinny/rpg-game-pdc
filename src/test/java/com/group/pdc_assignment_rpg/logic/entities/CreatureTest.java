/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.entities;

import com.group.pdc_assignment_rpg.logic.CStats;
import com.group.pdc_assignment_rpg.logic.StatBlock;
import com.group.pdc_assignment_rpg.logic.Stats;
import com.group.pdc_assignment_rpg.logic.character.Level;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public class CreatureTest {
    
    public CreatureTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test damage() method.
     */
    @org.junit.Test
    public void testDamage() {
        String name = "John Doe";
        Creature creature = new Player(name);
        
        int damageAmount;
        
        // Test damage when damage does not exceed creature's HP
        damageAmount = 1;
        // Ensure that damage method returns false when HP is not reduced to below 0
        assertFalse(creature.damage(damageAmount));
        // Ensure that creature's HP has been reduced by 1
        assertEquals(creature.getMaxHP() - damageAmount, creature.getHP());
        
        // Test damage when damage exceeds creature's HP
        damageAmount = creature.getHP() + 1;
        // Ensure that damage method returns true when HP is reduced to below 0 
        assertTrue(creature.damage(damageAmount));
        // Ensure that creature's HP is not reduced to below 0 by damage function
        assertEquals(0, creature.getHP());
    }
    
    /**
     * Test setHP() method.
     */
    @org.junit.Test
    public void testSetHP() {
        String name = "John Doe";
        Creature creature = new Player(name);
        
        int HP;
        int expectedHP;
        
        // Ensure HP can be set to a value between 0 and its MaxHP
        HP = creature.getMaxHP() - 1;
        expectedHP = HP;
        creature.setHP(HP);
        assertEquals(expectedHP, creature.getHP());
        
        // Ensure HP can be set to 0
        HP = 0;
        expectedHP = HP;
        creature.setHP(HP);
        assertEquals(expectedHP, creature.getHP());
        
        // Ensure HP can be set to a value equal to creature's MaxHP
        HP = creature.getMaxHP();
        expectedHP = HP;
        creature.setHP(HP);
        assertEquals(expectedHP, creature.getHP());
        
        // Ensure HP cannot be set to a value below zero
        HP = -1;
        expectedHP = 0;
        creature.setHP(HP);
        assertEquals(expectedHP, creature.getHP());
        
        // Ensure HP cannot be set above the value of creature's MaxHP
        HP = creature.getMaxHP() + 1;
        expectedHP = creature.getMaxHP();
        creature.setHP(HP);
        assertEquals(expectedHP, creature.getHP());
    }
    
    /**
     * Test setName() method.
     */
    @org.junit.Test
    public void testSetName() {
        String name = "John Doe";
        Creature creature = new Player(name);
        
        String testName;
        
        // Ensure valid name is accepted
        testName = "Valid Test";
        creature.setName(testName);
        assertEquals(testName, creature.getName());
        
        // Ensure invalid name is not accepted
        creature.setName(name);
        testName = "";
        try {
            creature.setName(testName);
            fail("Exception not thrown when setting creature name to null.");
        } catch (IllegalArgumentException e) {
            assertEquals(name, creature.getName());
        }
        
    }
    
}
