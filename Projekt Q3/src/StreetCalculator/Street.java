package StreetCalculator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Street {
	
	float x1;
	float y1;
	float x2;
	float y2;
	Vector2f result = new Vector2f();
	boolean isA;
	boolean isB;
	boolean crossing = false;
	
	public Street(float x1,float y1,float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void draw(Graphics g, Color color, int width) {
		g.setColor(color);
		g.setLineWidth(width);
		g.drawLine(x1, y1, x2, y2);
	}
	
	public float getCrossTime(int speed) {
		double addition = (double) (((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
		double length = Math.pow(addition, 0.5);
		float time = (float) length / speed;
		return time;
	}
	
	public Vector2f checkCross(float x3,float y3, float x4, float y4) {

		if((x1 != x3 || y1 != y3) && (x2 != x3 || y2 != y3) && (x1 != x4 || y1 != y4) && (x2 != x4 || y2 != y4)) {
			Line line1 = new Line(x1, y1, x2, y2);
			Line line2 = new Line(x3, y3, x4, y4);
			crossing = line1.intersect(line2, true, result);
			if(crossing) {
				crossing = false;
				return result;
			}
			return null;
		}
		return null;
	}
	
	public float getX1() {
		return x1;
	}
	
	public float getY1() {
		return y1;
	}
	
	public float getX2() {
		return x2;
	}
	
	public float getY2() {
		return y2;
	}
	

}
