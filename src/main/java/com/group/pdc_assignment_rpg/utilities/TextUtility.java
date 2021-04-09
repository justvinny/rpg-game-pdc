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
 * @author Macauley Cunningham - 19072621 <macalite@flashgiver.com>
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
    
    /**
     * Method to convert a string into title case and replace any underscores with spaces.
     * Primarily to be used for easily converting enumeration's .name() value into text.
     * 
     * @param string String to be converted
     * @return a string in titlecase with spaces instead of underscores
     */
    public static String toTitleCase(String string) {
    	
    	if (string == null || string.isEmpty()) {
    		return "";
    	} 

    	if (string.length() == 1) {
    		return string.toUpperCase();
    	}
    	
    	string = string.toLowerCase();
    	
    	String parts[] = string.split("_");
    	
    	StringBuilder titlecase = new StringBuilder(string.length());
    	
    	for (String part : parts) {
    		if (part.length() > 1) {
    			titlecase.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
    		} else {
    			titlecase.append(part.toLowerCase());
    		}
    		titlecase.append(" ");
    	}
    	
    	return titlecase.toString().trim();
    }
}
