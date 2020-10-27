package StreetCalculator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Button {
	
	int x;
	int y;
	int width;
	int height;
	String name;
	Text text;
	int tp;
	Boolean press;
	Input input;

	public Button(int x, int y, int width, int height, String name,int tp) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.tp = tp;
	}
	
	public void draw(Graphics g) {
		text = new Text(x + width/2 - tp, y + height/2 - 10, name);
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		text.draw(g);
	}
	
	
	public boolean press(int mouseX, int mouseY){
			if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				return true;
			}
			return false;
	}

	public void setInput(Input input) {
		this.input = input;
	}
		
}
