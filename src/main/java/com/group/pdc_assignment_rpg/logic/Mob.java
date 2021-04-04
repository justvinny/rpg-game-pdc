package com.group.pdc_assignment_rpg.logic;

//public class Mob extends Entity {
//}
public class Mob {

    private String name;
    private int maxHP, currentHP;
    private int x, y;
    private char charSymbol;

    public Mob() {
        name = "Red Slime";
        maxHP = 50;
        currentHP = 50;
        x = 13;
        y = 21;
        charSymbol = 'M';
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
}
