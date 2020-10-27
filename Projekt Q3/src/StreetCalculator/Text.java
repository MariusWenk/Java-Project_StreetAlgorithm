package StreetCalculator;


import org.newdawn.slick.Graphics;

public class Text {
	
	int x;
	int y;
	String text;
	
	public Text(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public void draw(Graphics g) {
		g.drawString(text, x, y);
	}

}
