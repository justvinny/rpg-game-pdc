package com.group.pdc_assignment_rpg.logic.navigation;

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
public class NavigationTest {
    
    public NavigationTest() {
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
     * Test of up method, of class Navigation.
     */
    @Test
    public void testUp() {
        System.out.println("up");
        Coordinates coords = new Coordinates(1, 1);
        Boundaries bounds = new Boundaries(0, 0, 1, 1);
        Navigation nav = new Navigation(coords, bounds);
        
        // Ensure navigation upward is accepted when coords have not reached upper y boundary
        assertTrue(nav.up());
        
        // Ensure navigation upward is not accepted when coords have reached upper y boundary
        assertFalse(nav.up());
    }

    /**
     * Test of down method, of class Navigation.
     */
    @Test
    public void testDown() {
        System.out.println("down");
        Coordinates coords = new Coordinates(0, 0);
        Boundaries bounds = new Boundaries(0, 0, 1, 1);
        Navigation nav = new Navigation(coords, bounds);
        
        // Ensure navigation downward is accepted when coords have not reached lower y boundary
        assertTrue(nav.down());
        
        // Ensure navigation downward is not accepted when coords have reached lower y boundary
        assertFalse(nav.down());
    }

    /**
     * Test of right method, of class Navigation.
     */
    @Test
    public void testRight() {
        Coordinates coords = new Coordinates(0, 0);
        Boundaries bounds = new Boundaries(0, 0, 1, 1);
        Navigation nav = new Navigation(coords, bounds);
        
        // Ensure navigation right is accepted when coords have not reached lower x boundary
        assertTrue(nav.right());
        
        // Ensure navigation right is not accepted when coords have reached lower x boundary
        assertFalse(nav.right());
    }

    /**
     * Test of left method, of class Navigation.
     */
    @Test
    public void testLeft() {
        Coordinates coords = new Coordinates(1, 1);
        Boundaries bounds = new Boundaries(0, 0, 1, 1);
        Navigation nav = new Navigation(coords, bounds);
        
        // Ensure navigation left is accepted when coords have not reached upper x boundary
        assertTrue(nav.left());
        
        // Ensure navigation downward is not accepted when coords have reached upper x boundary
        assertFalse(nav.left());
    }
    
}
