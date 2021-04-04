package com.group.pdc_assignment_rpg.logic;

/**
 * Dummy player class. Please edit as you see fit Macauley/Jess.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class Player {

    private String name;
    private int maxHP, currentHP;
    private int x, y;
    private char charSymbol;

    public Player() {
        name = "Son Goku";
        maxHP = 100;
        currentHP = 100;
        x = 5;
        y = 23;
        charSymbol = 'P';
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getCharSymbol() {
        return charSymbol;
    }
    
    public void up() {
        y--;
    }
    
    public void down() {
        y++;
    }
    
    public void left() {
        x--;
    }
    
    public void right() {
        x++;
    }
}
