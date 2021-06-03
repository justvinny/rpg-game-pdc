package com.group.pdc_assignment_rpg.logic.items;

import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jessica McCormick - 20096516 <jessymccormick@gmail.com>
 */
public class TreasureTest {
    
    /**
     * Test of open method, of class Treasure.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        Item item = new Item("Test", ItemList.JUNK);
        Coordinates coords = new Coordinates(1, 1);
        Treasure instance = new Treasure(item, coords);
        
        // Ensure that treasure is unopened by default
        assertFalse(instance.isOpened());
        
        // Ensure that treasure changes to opened when open method is called
        instance.open();
        assertTrue(instance.isOpened());
    }

}
