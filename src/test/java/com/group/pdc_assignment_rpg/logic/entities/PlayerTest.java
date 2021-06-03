package com.group.pdc_assignment_rpg.logic.entities;

import com.group.pdc_assignment_rpg.logic.character.Level;
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
public class PlayerTest {
    
    public PlayerTest() {
    }

 
    /**
     * Test of Player() constructor method, using only name parameter.
     */
    @org.junit.Test
    public void testCreatePlayerFromName() {
        // Create a new Player object with a name
        String name = "John Doe";
        Player player = new Player(name);
        
        // Ensure default equipment is equipped correctly
        assertTrue(player.isWeaponEquipped());
        assertTrue(player.isArmourEquipped());
        
        // Ensure defaults are correctly set for a new player
        assertEquals(Level.L1, player.getLevel());
        assertEquals(player.getHP(), player.getMaxHP());
        assertFalse(player.isDefending());
    }

    /**
     * Test of setPlayerInstance method, of class Player.
     */
    @org.junit.Test
    public void testSetPlayerInstance() {
        System.out.println("setPlayerInstance");
        String name = "John Doe";
        Player player = new Player(name);
        
        // Ensure currentPlayer can be set to a player
        Player.setPlayerInstance(player);
        assertEquals(player, Player.getCurrentPlayer());
        
        // Ensure currentPlayer cannot be set to a new player when it is already set to a player
        Player newPlayer = new Player("Test");
        Player.setPlayerInstance(newPlayer);
        assertEquals(player, Player.getCurrentPlayer());
    }
    
}
