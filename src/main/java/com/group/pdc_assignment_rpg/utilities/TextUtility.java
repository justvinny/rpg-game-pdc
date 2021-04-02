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
     * Method for repeating characters x amount of times and transforming
     * them into a string.
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
}
