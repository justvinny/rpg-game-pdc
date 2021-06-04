/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.utilities;

import static com.group.pdc_assignment_rpg.utilities.TextUtility.mergeStringArray;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class TextUtilityTest {

    /**
     * Test of repeatCharacter method, of class TextUtility.
     */
    @Test
    public void testRepeatCharacterTenTimes() {
        System.out.println("repeatCharacter");
        int nTimes = 10;
        char character = '#';
        String expResult = "##########";
        String result = TextUtility.repeatCharacter(nTimes, character);
        assertEquals(expResult, result);
    }

    /**
     * Test of repeatStringIntoArray method, of class TextUtility.
     */
    @Test
    public void testRepeatStringIntoArrayThreeTimes() {
        System.out.println("repeatStringIntoArray");
        int nTimes = 3;
        String string = "Test";
        String[] expResult = {"Test", "Test", "Test"};
        String[] result = TextUtility.repeatStringIntoArray(nTimes, string);
        assertEquals(expResult.length, result.length);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of mergeStringArray method, of class TextUtility.
     */
    @Test
    public void testMergeStringArray() {
        System.out.println("mergeStringArray");
        String[] arr1 = new String[5];
        arr1[0] = "A";arr1[1] = "B";arr1[2] = "C";arr1[3] = "D";arr1[4] = "E";
        String[] arr2 = new String[7];
        arr2[0] = "Z";arr2[1] = "X";arr2[2] = "S";arr2[3] = "F";arr2[4] = "W";
        arr2[5] = "O";arr2[6] = "P";
        String[] expResult = {"AZ", "BX", "CS", "DF", "EW", "O", "P"};
        String[] result = mergeStringArray(arr1, arr2);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toTitleCase method, of class TextUtility.
     */
    @Test
    public void testToTitleCase() {
        System.out.println("toTitleCase");
        String string = "title";
        String expResult = "Title";
        String result = TextUtility.toTitleCase(string);
        assertEquals(expResult, result);
    }
    
}
