package com.group.pdc_assignment_rpg.logic;

import com.googlecode.lanterna.TextColor;

/** 
 * Basic entity on the map, containing position data as well
 * as format for map appearance, i.e. colour and symbol.
 * 
 * @author Macauley Cunningham - 19072621
 */
public abstract class Entity {

	private int posx;
	private int posy;
	private char symbol;
	private TextColor color;
	
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

	/**
	 * Getter methods
	 * 
	 **/
	public int getX() {
		return this.posx;
	}
	
	public int getY() {
		return this.posy;
	}

	public char getSymbol() {
		return this.symbol;
	}
	
	public TextColor getColor() {
		return this.color;
	}

	/**
	 * Setter methods
	 *
	 */
	public void setX(int posx) {
		this.posx = posx;
	}
	
	public void setY(int posy) {
		this.posy = posy;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public void setColor(TextColor colour) {
		this.color = colour;
	}

	public String toString() {
		return "" + this.symbol;
	}

}
