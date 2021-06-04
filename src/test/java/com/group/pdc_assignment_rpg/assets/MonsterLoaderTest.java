/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.assets;

import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MonsterLoaderTest {
    
    /**
     * Setup DB connection and init monster loader.
     */
    @BeforeClass
    public static void setUpClass() {
        ResourceLoaderUtility.establishConnection();
        MonsterLoader.initMonsterLoader();
    }

    /**
     * Test of getMob method by getting a guardian golem mob.
     */
    @Test
    public void testGetMobGuardianGolem() {
        MonsterLoader instance = MonsterLoader.getInstance();
        String expected = "Guardian Golem";
        String result = instance.getMob(expected).getName();
        assertEquals(expected, result);
    }

    /**
     * Test of getMob method by getting an executioner mob.
     */
    @Test
    public void testGetMobExecutioner() {
        MonsterLoader instance = MonsterLoader.getInstance();
        String expected = "Executioner";
        String result = instance.getMob(expected).getName();
        assertEquals(expected, result);
    }

    /**
     * Test of getInstance method, of class MonsterLoader
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        MonsterLoader expResult = null;
        MonsterLoader result = MonsterLoader.getInstance();
        assertNotEquals(expResult, result);
    }

}
