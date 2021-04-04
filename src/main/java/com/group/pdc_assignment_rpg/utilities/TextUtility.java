/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.utilities;

/**
 * Contains utility methods that manipulates text.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class TextUtility {

    /**
     * Method for repeating characters x amount of times and transforming them
     * into a string.
     *
     * @param nTimes number of times you want to repeat a character.
     * @param character is the character we want to repeat x amount of times.
     * @return a string representation of the characters we repeated x amount of
     * times.
     */
    public static String repeatCharacter(int nTimes, char character) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nTimes; i++) {
            builder.append(character);
        }

        return builder.toString();
    }

    /**
     * Method for repeating Strings x amount of times and transforming them into
     * a string array.
     *
     * @param nTimes number of times you want to repeat a character.
     * @param string is the string we want to repeat x amount of times.
     * @return a string array of the String we repeated x amount of times.
     */
    public static String[] repeatStringIntoArray(int nTimes, String string) {
        String[] strArr = new String[nTimes];

        for (int i = 0; i < nTimes; i++) {
            strArr[i] = string;
        }

        return strArr;
    }

    /**
     * Merge two string arrays into one array while using the longer of the two
     * as the merged array length.
     *
     * @param strArr1 first string array
     * @param strArr2 second string array
     * @return a combined string array
     */
    public static String[] mergeStringArray(String[] strArr1, String[] strArr2) {
        // Use the length of the bigger array.
        int arrLen = (strArr1.length >= strArr2.length) ? strArr1.length : strArr2.length;

        // Transform both arrays to equal size.
        String[] newArr1 = new String[arrLen];
        String[] newArr2 = new String[arrLen];

        // Transfer values of first original array to the new array.
        for (int i = 0; i < strArr1.length; i++) {
            newArr1[i] = strArr1[i];
        }

        // Transfer values of second original array to the new array.
        for (int i = 0; i < strArr2.length; i++) {
            newArr2[i] = strArr2[i];
        }

        // Merge both arrays into one.
        String[] mergedArr = new String[arrLen];
        for (int i = 0; i < strArr1.length; i++) {
            mergedArr[i] = strArr1[i] + strArr2[i];
        }
        return mergedArr;
    }
}
