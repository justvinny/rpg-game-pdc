package com.group.pdc_assignment_rpg.logic.items;

import com.group.pdc_assignment_rpg.logic.entities.EquipmentSlot;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import java.util.EnumMap;
import java.util.List;
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
public class InventoryTest {
    
    public InventoryTest() {
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
     * Test of add method, of class Inventory.
     */
    @Test
    public void testAdd_Item() {
        System.out.println("add");
        Inventory instance = new Inventory();
        Item item;
        
        // Ensure null value cannot be added to inventory
        item = null;
        instance.add(item);
        assertEquals(0, instance.size());
        
        // Ensure valid item can be successfully added to inventory
        item = new Item("Test", ItemList.JUNK);
        instance.add(item);
        try {
            assertEquals(item, instance.get(0));
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Item could not be added to list.");
        }
        
        // Ensure items cannot be added if capacity is exceeded
        int capacitySize = instance.size();
        instance.setCapacity(capacitySize);
        item = new Item("Test 2", ItemList.JUNK);
        instance.add(item);
        assertEquals(capacitySize, instance.size());
        
    }

    /**
     * Test of add method, of class Inventory.
     */
    @Test
    public void testAdd_Item_int() {
        System.out.println("add");
        Inventory instance = new Inventory();
        Item item;
        
        // Ensure null value cannot be added to inventory
        item = null;
        instance.add(item, 2);
        assertEquals(0, instance.size());
        
        // Ensure valid item can be successfully added to inventory
        item = new Item("Test", ItemList.JUNK);
        instance.add(item, 4);
        try {
            assertEquals(item, instance.get(0));
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Item could not be added to list.");
        }
        
        // Ensure items cannot be added if capacity is exceeded
        int capacitySize = instance.size();
        instance.setCapacity(capacitySize);
        item = new Item("Test 2", ItemList.JUNK);
        instance.add(item, 3);
        assertEquals(capacitySize, instance.size());
    }

    
}
