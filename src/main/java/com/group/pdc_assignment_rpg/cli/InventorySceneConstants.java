/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

/**
 * Constants that are useful for our inventory scene such as boundaries for
 * our cursor, maximum item name length, number of dashes for our boxes.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class InventorySceneConstants {

    public static final int CURSOR_Y_START = 9;
    public static final int CURSOR_Y_END = 21;
    public static final int CURSOR_Y_STEP = 2;
    public static final int CURSOR_X_START = 2;
    public static final int CURSOR_X_STEP = 31;
    public static final int CURSOR_X_END = CURSOR_X_START + 31 * 2;
    public static final int ITEM_X_STEP = 1;
    public static final int ITEM_Y_STEP = 3;
    public static final int MAX_WORD_LENGTH = 30;
    public static final int MAX_COLUMNS = 3;
    public static final int N_ROW_DASHES = ((MAX_WORD_LENGTH + 1) * MAX_COLUMNS) + 1;
}
