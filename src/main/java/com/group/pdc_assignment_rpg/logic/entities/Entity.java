package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import com.group.pdc_assignment_rpg.logic.navigation.Direction;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.DOWN;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.LEFT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.NONE;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.RIGHT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.UP;

/**
 * Basic entity on the map, containing position data as well as format for map
 * appearance, i.e. colour and symbol.
 *
 * @author Macauley Cunningham - 19072621
 */
public abstract class Entity {

    protected char symbol;
    protected TextColor color;
    protected Coordinates coordinates;
    protected Direction direction;
    protected boolean playerRunning;

    public Entity(int x, int y, char s, TextColor c) {
        this.setCoordinates(x, y);
        this.setSymbol(s);
        this.setColor(c);
        this.playerRunning = false;
        this.direction = NONE;
    }

    public Entity(int x, int y) {
        this.setCoordinates(x, y);
        this.setSymbol('#');
        this.setColor(TextColor.ANSI.RED);
        this.playerRunning = false;
        this.direction = NONE;
    }

    /*
	 * Getter methods
	 * 
	 **/
    public Coordinates getCoordinates() {
        return this.coordinates;
    }
    
    public int getX() {
        return this.coordinates.getX();
    }

    public int getY() {
        return this.coordinates.getY();
    }

    public char getSymbol() {
        return this.symbol;
    }

    public TextColor getColor() {
        return this.color;
    }

    public boolean isPlayerRunning() {
        return playerRunning;
    }
    
    public Direction getDirection() {
        return direction;
    }

    /*
	 * Setter methods
	 *
     */
    public final void setCoordinates(int x, int y) {
        this.coordinates = new Coordinates(x, y);
    }

    public final void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public final void setColor(TextColor colour) {
        this.color = colour;
    }

    /**
     * Movement methods
     *
     */
    public void up() {
        this.coordinates.decrementY();
    }

    public void down() {
        this.coordinates.incrementY();
    }

    public void left() {
        this.coordinates.decrementX();
    }

    public void right() {
        this.coordinates.incrementX();
    }

    public void setIdle() {
        playerRunning = false;
    }
    
    public void setRunning() {
        playerRunning = true;
    }
    
    public void setDirection(Direction direction) {
        switch (direction) {
            case UP:
                this.direction = UP;
                break;
            case DOWN:
                this.direction = DOWN;
                break;
            case LEFT:
                this.direction = LEFT;
                break;
            case RIGHT:
                this.direction = RIGHT;
                break;
            default:
                this.direction = NONE;
        }
    }

    @Override
    public String toString() {
        return "" + this.symbol;
    }

}
