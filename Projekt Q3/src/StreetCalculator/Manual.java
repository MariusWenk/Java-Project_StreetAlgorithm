package StreetCalculator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Manual {
	
	int x;
	int y;
	int width;
	int height;
	Boolean openclose;
	Button closebut;
	String imageString;
	
	public Manual(int x, int y, int width, int height,Boolean openclose, String imageString){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.openclose = openclose;
		this.imageString = imageString;
	}
	
	public void draw(Graphics g) throws SlickException {
		if(openclose) {
			Image imag = new Image(imageString);
			Image image = imag.getScaledCopy(width,height);
			g.setColor(Color.white);
			g.fillRect(x, y, width, height);
			g.drawImage(image,x,y);
			g.setColor(Color.black);
			g.drawRect(x, y, width, height);
			closebut = new Button(x+width-10,y,10,10,"X",7);
			closebut.draw(g);
		}
	}

	public void close() {
	    openclose = false;
	}
	
	public void open() {
		openclose = true;
    }

	public boolean but(int mouseX ,int mouseY) {
		if (mouseX >= x+width-10 && mouseX <= x + width && mouseY >= y && mouseY <= y + 10) {
			return true;
		}
		return false;
	}
	


}
