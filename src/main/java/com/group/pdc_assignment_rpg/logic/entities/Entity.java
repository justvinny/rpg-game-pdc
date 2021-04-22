package com.group.pdc_assignment_rpg.logic.entities;

import com.googlecode.lanterna.TextColor;

/** 
 * Basic entity on the map, containing position data as well
 * as format for map appearance, i.e. colour and symbol.
 * 
 * @author Macauley Cunningham - 19072621
 */
public abstract class Entity {

	protected int x;
	protected int y;
	protected char symbol;
	protected TextColor color;
	
	public Entity(int x, int y, char s, TextColor c){
		this.setX(x);
		this.setY(y);
		this.setSymbol(s);
		this.setColor(c);
	}
	
	public Entity(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setSymbol('#');
		this.setColor(TextColor.ANSI.RED);
	}

	/*
	 * Getter methods
	 * 
	 **/
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public char getSymbol() {
		return this.symbol;
	}
	
	public TextColor getColor() {
		return this.color;
	}

	/*
	 * Setter methods
	 *
	 */
	public final void setX(int x) {
		this.x = x;
	}
	
	public final void setY(int y) {
		this.y = y;
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

        @Override
	public String toString() {
		return "" + this.symbol;
	}

}
