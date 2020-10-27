package StreetCalculator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Start {

	float x;
	float y;

	public Start() {}
	public Start(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) throws SlickException {
		g.setColor(Color.black);
		Image im = new Image("res/Start.png");
		Image i = im.getScaledCopy(80,80);
		g.drawImage(i,x,y);
	}
	
	public boolean press(int mouseX, int mouseY){
		if (mouseX >= x && mouseX <= x + 80 && mouseY >= y && mouseY <= y + 80) {
			return true;
		}
		return false;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
