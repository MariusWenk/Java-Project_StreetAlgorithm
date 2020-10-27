package StreetCalculator;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.TextField;

import StreetCalculator.Street;

public class StreetCalculator extends BasicGame{
	
	private ArrayList<Street> street;
	private ArrayList<Street> deleteStreet = new ArrayList<Street>();
	private ArrayList<Street> delete2Street = new ArrayList<Street>();
	private ArrayList<Street> addStreet = new ArrayList<Street>();
	private ArrayList<Street> addStreet2 = new ArrayList<Street>();
	private ArrayList<Street> streetSolution = new ArrayList<Street>();
	private ArrayList<Street> streetSolution1 = new ArrayList<Street>();
	private ArrayList<Float> countedxPos = new ArrayList<Float>();
	private ArrayList<Float> countedyPos = new ArrayList<Float>();
	private ArrayList<Float> crossPlaces = new ArrayList<Float>();
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	boolean newStreetSet = false;
	boolean manualOpen1 = true;
	boolean manualOpen2 = true;
	boolean newStreet = true;
	boolean connection1 = false;
	boolean connection2 = false;
	boolean circles = false;
	boolean crosss = false;
	boolean dStreet = false;
	boolean startSet = false;
	boolean zielSet = false;
	boolean calculated = false;
	boolean startMoved;
	boolean zielMoved;
	Street street1;
	Street street2;
	Street street3;
	Street street4;
	Start start;
	Ziel ziel;
	Start start1 = new Start();
	Ziel ziel1 = new Ziel();
	Calculator calculator;
	Manual manual1;
	Manual manual2;
	float x1;
	float y1;
	float x2;
	float y2;
	float xpos;
	float ypos;
	float redLength;
	float blueLength;
	float averageTLWaitingTime; 
	int circle = 20;
	int n = 3;
	int x = 0;
	int speedTL = 10;
	int speed = 50;
	int crosses = 0;
	Street stre;
	TextField textfield1;
	TextField textfield2;
	
	public static final int fps = 60;
	public StreetCalculator() {
		super("StreetCalculator");
	}

	public static void main(String[] arguments)
	    {
	        try
	        {
	        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	            AppGameContainer app = new AppGameContainer(new StreetCalculator());
	            app.setDisplayMode( (int) screenSize.getWidth()-50,  (int) screenSize.getHeight()-50, false);
				app.setTargetFrameRate(fps);
				app.setShowFPS(false);
				app.setMultiSample(16);
				Display.setInitialBackground(1,1,1) ;
	            app.start();
	        }
	        catch (SlickException e)
	        {
	            e.printStackTrace();
	        }
	    }

	@Override
	public void init(GameContainer container) throws SlickException {
		button1 = new Button(container.getWidth()-125,120,100,60,"Delete All",45);
		button2 = new Button(container.getWidth()-125,200,100,60,"Delete Last",50);
		button3 = new Button(container.getWidth()-125,280,100,60,"Delete S",38);
		button4 = new Button(container.getWidth()-125,360,100,60,"Delete Z",38);
		button5 = new Button(container.getWidth()-125,440,100,60,"Manual",28);
		button6 = new Button(container.getWidth()-125,520,100,60,"Calculate",40);
		street = new ArrayList<Street>();
		start = new Start(10,10);
		ziel = new Ziel(100,10);
		textfield1 = new TextField(container,container.getDefaultFont(),200,50,150,20);
		textfield1.setBackgroundColor(Color.white);
		textfield1.setBorderColor(Color.black);
		textfield1.setTextColor(Color.black);
		textfield1.setText("10");
		textfield2 = new TextField(container,container.getDefaultFont(),400,50,150,20);
		textfield2.setBackgroundColor(Color.white);
		textfield2.setBorderColor(Color.black);
		textfield2.setTextColor(Color.black);
		textfield2.setText("50");
		calculator = new Calculator();
		float speedtl = (float) speedTL;
		float speee = (float) speed;
		averageTLWaitingTime = (((speedtl*(speedtl+1))/2)/(speedtl*2)) + ( (float)0.25 * (speee / 10));
		manual1 = new Manual(200,200,550,250,true,"res/Anleitung1.png");
		manual2 = new Manual(220,180,550,600,true,"res/Anleitung2.png");
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		g.setBackground(Color.white);
		g.setColor(Color.black);
		for(int i = 0; i < crossPlaces.size(); i+=2){
			if( !((ziel1.getX()+40) == crossPlaces.get(i) && (ziel1.getY()+40) == crossPlaces.get(i+1)) && !((start1.getX()+40) == crossPlaces.get(i) && (start1.getY()+40) == crossPlaces.get(i+1))){
				Image imag = new Image("res/Ampel.png");
				Image image = imag.getScaledCopy(25,60);
				g.drawImage(image, crossPlaces.get(i)-25/2, crossPlaces.get(i+1)-30);
			}
		}
		for (Street stree : street) {
			stree.draw(g, Color.black,1);
		}
		g.setLineWidth(1);
		g.setColor(Color.gray);
		g.fillRect(container.getWidth()-160, 100, container.getWidth(), container.getHeight());
		g.fillRect(0, 0, container.getWidth(), 100);
		g.setColor(Color.black);
		g.drawLine(container.getWidth()-160, 100, container.getWidth()-160, container.getHeight());
		g.drawLine(0, 100, container.getWidth(), 100);
		if(calculated){
			for (Street stre : streetSolution) {
				stre.draw(g, Color.red,20);
			}
			for (Street stre : streetSolution1) {
				stre.draw(g, Color.blue,2);
			}
			g.setLineWidth(1);
			g.setColor(Color.red);
			g.drawString(Float.toString(redLength)+" s", 850, 46);
			g.setColor(Color.blue);
			g.drawString(Float.toString(blueLength)+" s", 850, 82);
		}
		g.setLineWidth(1);
		button1.draw(g);
		button2.draw(g);
		button3.draw(g);
		button4.draw(g);
		button5.draw(g);
		button6.draw(g);
		if(newStreet == false){
			g.setColor(Color.gray);
			g.drawLine(x1, y1, mouseX, mouseY);
		}
		start.draw(g);
		ziel.draw(g);
		if(startSet) {
			start1.draw(g);
		}
		if(zielSet) {
			ziel1.draw(g);
		}
		if(circles) {
			g.drawOval(xpos-circle, ypos-circle, 2*circle, 2*circle);
		}
		g.setColor(Color.white);
		textfield1.render(container, g);
		textfield2.render(container, g);
		g.drawString("Ampelintervall", 200, 10);
		g.drawString("in s:", 200, 30);
		g.drawString("Beschleunigung: 10 Pixel pro s^2", 200, 75);
		g.drawString("Hauptgeschwindigkeit", 400, 10);
		g.drawString("in Pixel pro s:", 400, 30);
		g.drawString("Straßen gesetzt:", 650, 10);
		g.drawString("Ergebnis:", 850, 10);
		if(street.size() > 30 && street.size() <= 50){
			g.setColor(Color.orange);
		}
		else if(street.size() > 50){
			g.setColor(Color.red);
		}
		else{
			g.setColor(Color.black);
		}
		g.drawString(Integer.toString(street.size()), 650, 50);
		g.setColor(Color.red);
		g.drawString("rot = kürzester Weg zeitlich (mit Ampeln und Geschwindigkeit):", 850, 28);
		g.setColor(Color.blue);
		g.drawString("blau = kürzester Weg ohne Ampeln (reine Strecke):", 850, 64);
		manual2.draw(g);
		manual1.draw(g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		circles = false;
		for (Street str : street) {
			float xpo1 = str.getX1();
			float ypo1 = str.getY1();
			float xpo2 = str.getX2();
			float ypo2 = str.getY2();
			if(mouseX <= xpo1+circle && mouseX >= xpo1-circle && mouseY <= ypo1+circle && mouseY >= ypo1-circle) {
				circles = true;
				xpos = xpo1;
				ypos = ypo1;
			}
			else if(mouseX <= xpo2+circle && mouseX >= xpo2-circle && mouseY <= ypo2+circle && mouseY >= ypo2-circle) {
				circles = true;
				xpos = xpo2;
				ypos = ypo2;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			String text = textfield1.getText();
			if(text.length() < 10){
				int speeTL = Integer.parseInt(textfield1.getText());
				if(speeTL > 0){
					speedTL = speeTL;
				}
				else{
					textfield1.setText(Integer.toString(speedTL));
				}
			}
			else{
				textfield1.setText(Integer.toString(speedTL));
			}
			String text2 = textfield2.getText();
			if(text2.length() < 10){
				int spee = Integer.parseInt(textfield2.getText());
				if(spee > 0){
					speed = spee;
				}
				else{
					textfield2.setText(Integer.toString(speed));
				}
			}
			else{
				textfield2.setText(Integer.toString(speed));
			}
			float speedtl = (float) speedTL;
			float speee = (float) speed;
			averageTLWaitingTime = (((speedtl*(speedtl+1))/2)/(speedtl*2)) + ( (float)0.25 * (speee / 10));
		}
		
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			newStreet = true;
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX <= container.getWidth()-160 && mouseY >= 100 && !manualOpen1 && !manualOpen2) {
				for (Street stree : street) {
					float xposi1 = stree.getX1();
					float yposi1 = stree.getY1();
					float xposi2 = stree.getX2();
					float yposi2 = stree.getY2();
					if(mouseX <= xposi1+circle && mouseX >= xposi1-circle && mouseY <= yposi1+circle && mouseY >= yposi1-circle) {
						if(newStreet) {
							connection1 = true;
							this.x1 = xposi1;
							this.y1 = yposi1;
						}
						else {
							connection2 = true;
							this.x2 = xposi1;
							this.y2 = yposi1;
						}
					}
					else if(mouseX <= xposi2+circle && mouseX >= xposi2-circle && mouseY <= yposi2+circle && mouseY >= yposi2-circle){
						if(newStreet) {
							connection1 = true;
							this.x1 = xposi2;
							this.y1 = yposi2;
						}
						else {
							connection2 = true;
							this.x2 = xposi2;
							this.y2 = yposi2;
						}
					}
				}
				if(newStreet) {
					if(connection1 == false) {
						this.x1 = mouseX;
						this.y1 = mouseY;
					}
					else {
						connection1 = false;
					}
					newStreet = !newStreet;
				}
				else {
					if(connection2) {
						stre = new Street(x1,y1,x2,y2);
						connection2 = false;
						this.x1 = x2;
						this.y1 = y2;
					}
					else {
						stre = new Street(x1,y1,mouseX,mouseY);
						this.x1 = mouseX;
						this.y1 = mouseY;
					}
					street.add(stre);
					newStreetSet = true;
				}
			}
			
			if(button1.press(mouseX,mouseY)){
				street.removeAll(street);
				streetSolution.removeAll(streetSolution);
				streetSolution1.removeAll(streetSolution1);
				crossPlaces.removeAll(crossPlaces);
				crosses = 0;
				start1 = new Start();
				ziel1 = new Ziel();
				start1.setX(10);
				start1.setY(10);
				ziel1.setX(100);
				ziel1.setY(10);
				newStreet = true;
				calculated = false;
			}
			
			if(button2.press(mouseX,mouseY)){
				if(street.size() != 0) {
					street.remove(street.size() - 1);
					newStreet = true;
					calculated = false;
				}
			}
			
			if(button3.press(mouseX,mouseY)){
				streetSolution.removeAll(streetSolution);
				streetSolution1.removeAll(streetSolution1);
				start1 = new Start();
				start1.setX(10);
				start1.setY(10);
				calculated = false;
			}
			
			if(button4.press(mouseX,mouseY)){
				streetSolution.removeAll(streetSolution);
				streetSolution1.removeAll(streetSolution1);
				ziel1 = new Ziel();
				ziel1.setX(100);
				ziel1.setY(10);
				calculated = false;
			}
			
			if(button5.press(mouseX,mouseY)){
				manual1.open();
				manual2.open();
				manualOpen1 = true;
				manualOpen2 = true;
			}
			
			if(button6.press(mouseX,mouseY)){
				if(startSet == true && zielSet == true){
					calculated = calculator.calculate(street,start1,ziel1,speed,crosses,crossPlaces,averageTLWaitingTime);
					if(calculated){
						streetSolution = calculator.getStreetSolution();
						streetSolution1 = calculator.getStreetSolution1();
						redLength = calculator.getRedLength();
						blueLength = calculator.getBlueLength();
					}
				}
			}
			
			if(manual1.but(mouseX, mouseY)){
				manual1.close();
				manualOpen1 = false;
			}
			
			if(manual2.but(mouseX, mouseY)){
				manual2.close();
				manualOpen2 = false;
			}
			
			if (mouseX >= textfield1.getX()&&mouseX <= textfield1.getX()+150&&mouseY >= textfield1.getY()&&mouseY <= textfield1.getY()+20) {
				textfield1.setText("");
			}
			if (mouseX >= textfield2.getX()&&mouseX <= textfield2.getX()+150&&mouseY >= textfield2.getY()&&mouseY <= textfield2.getY()+20) {
				textfield2.setText("");
			}
			if(ziel.press(mouseX, mouseY)) {
				zielMoved = true;
			}
			if(start.press(mouseX, mouseY)) {
				startMoved = true;
			}
			if(zielMoved && mouseY >= 140 && mouseX <= container.getWidth()-200 && !manualOpen1 && !manualOpen2) {
				zielSet = true;
				zielMoved = false;
				xpos = mouseX;
				ypos = mouseY;
				for (Street str : street) {
					float xpo1 = str.getX1();
					float ypo1 = str.getY1();
					float xpo2 = str.getX2();
					float ypo2 = str.getY2();
					if(mouseX <= xpo1+circle && mouseX >= xpo1-circle && mouseY <= ypo1+circle && mouseY >= ypo1-circle) {
						xpos = xpo1;
						ypos = ypo1;
					}
					else if(mouseX <= xpo2+circle && mouseX >= xpo2-circle && mouseY <= ypo2+circle && mouseY >= ypo2-circle) {
						xpos = xpo2;
						ypos = ypo2;
					}
				}
				ziel1.setX(xpos-40);
				ziel1.setY(ypos-40);
				streetSolution.removeAll(streetSolution);
				streetSolution1.removeAll(streetSolution1);
			}
			if(startMoved && mouseY >= 140 && mouseX <= container.getWidth()-200 && !manualOpen1 && !manualOpen2) {
				startSet = true;
				startMoved = false;
				xpos = mouseX;
				ypos = mouseY;
				for (Street str : street) {
					float xpo1 = str.getX1();
					float ypo1 = str.getY1();
					float xpo2 = str.getX2();
					float ypo2 = str.getY2();
					if(mouseX <= xpo1+circle && mouseX >= xpo1-circle && mouseY <= ypo1+circle && mouseY >= ypo1-circle) {
						xpos = xpo1;
						ypos = ypo1;
					}
					else if(mouseX <= xpo2+circle && mouseX >= xpo2-circle && mouseY <= ypo2+circle && mouseY >= ypo2-circle) {
						xpos = xpo2;
						ypos = ypo2;
					}
				}
				start1.setX(xpos-40);
				start1.setY(ypos-40);
				streetSolution.removeAll(streetSolution);
				streetSolution1.removeAll(streetSolution1);
			}
		}
		
		if(newStreetSet){
			for (Street str : street) {
				if(str.getCrossTime(speed)==0){
					deleteStreet.add(str);
				}
			}
			street.removeAll(deleteStreet);
			deleteStreet.removeAll(deleteStreet);
		}
		
		if(newStreetSet){
			int a = 0;
			for (Street str : street) {
				float xpo1 = str.getX1();
				float ypo1 = str.getY1();
				float xpo2 = str.getX2();
				float ypo2 = str.getY2();
				if(a > 0){
					for(int b = 0; b < a; b++){
						Street stre = street.get(a-b-1);
						float xposi1 = stre.getX1();
						float yposi1 = stre.getY1();
						float xposi2 = stre.getX2();
						float yposi2 = stre.getY2();
						if(xposi1 <= xpo1+n && xposi1 >= xpo1-n && yposi1 <= ypo1+n && yposi1 >= ypo1-n && xposi2 <= xpo2+n && xposi2 >= xpo2-n && yposi2 <= ypo2+n && yposi2 >= ypo2-n || xposi1 <= xpo2+n && xposi1 >= xpo2-n && yposi1 <= ypo2+n && yposi1 >= ypo2-n && xposi2 <= xpo1+n && xposi2 >= xpo1-n && yposi2 <= ypo1+n && yposi2 >= ypo1-n){
							deleteStreet.add(str);
							dStreet = true;
						}
					}
				}
				a++;
			}
			street.removeAll(deleteStreet);
			deleteStreet.removeAll(deleteStreet);
			a = 0;
		}
		
		if(newStreetSet){
			int cr = 0;
			ArrayList<Float> xCrosses = new ArrayList<Float>();
			ArrayList<Float> yCrosses = new ArrayList<Float>();
			for (Street str : street) {
				float xpo1 = str.getX1();
				float ypo1 = str.getY1();
				float xpo2 = str.getX2();
				float ypo2 = str.getY2();
				Street stre = street.get(street.size() - 1);
				float xposi1 = stre.getX1();
				float yposi1 = stre.getY1();
				float xposi2 = stre.getX2();
				float yposi2 = stre.getY2();
				if(str.checkCross(xposi1,yposi1,xposi2,yposi2) != null) {
					if(cr == 0){
						Vector2f cross = str.checkCross(xposi1,yposi1,xposi2,yposi2);
						float xCross = cross.getX();
						float yCross = cross.getY();
						xCrosses.add(xposi1);
						yCrosses.add(yposi1);
						xCrosses.add(xposi2);
						yCrosses.add(yposi2);
						xCrosses.add(xCross);
						yCrosses.add(yCross);
						delete2Street.add(str);
						delete2Street.add(stre);
						street1 = new Street(xpo1,ypo1,xCross,yCross);
						street2 = new Street(xpo2,ypo2,xCross,yCross);
						street3 = new Street(xposi1,yposi1,xCross,yCross);
						street4 = new Street(xposi2,yposi2,xCross,yCross);
						addStreet.add(street1);
						addStreet.add(street2);
						addStreet2.add(street3);
						addStreet2.add(street4);
						crosss = true;
					}
					else{
						Vector2f cross = str.checkCross(xposi1,yposi1,xposi2,yposi2);
						float xCross = cross.getX();
						float yCross = cross.getY();
						xCrosses.add(xCross);
						yCrosses.add(yCross);
						delete2Street.add(str);
						street1 = new Street(xpo1,ypo1,xCross,yCross);
						street2 = new Street(xpo2,ypo2,xCross,yCross);
						addStreet2.removeAll(addStreet2);
						addStreet.add(street1);
						addStreet.add(street2);
						float[] sortedX = new float[xCrosses.size()];
						float[] sortedY = new float[xCrosses.size()];
						if(xCrosses.get(0) >= xCrosses.get(1)+2 || xCrosses.get(0) <= xCrosses.get(1)-2){
							float[] toSort = new float[xCrosses.size()];
							for(int i = 0; i < xCrosses.size(); i++){
								toSort[i] = xCrosses.get(i);
							}
							float[] sorted = sort(toSort);
							float[] toSortCo = new float[yCrosses.size()];
							float[] sortCo = new float[yCrosses.size()];
							for(int i = 0; i < yCrosses.size(); i++){
								toSortCo[i] = yCrosses.get(i);
							}
							float[] sortedCo = sort(toSortCo);
							if(xCrosses.get(0) < xCrosses.get(xCrosses.size()-1)){
								if(sortedCo[0] != yCrosses.get(0)){
									for(int i = 0; i < sortedCo.length; i++){
										sortCo[i] = sortedCo[(sortedCo.length-1)-i];
									}
								}
								else{
									sortCo = sortedCo;
								}
							}
							else{
								if(sortedCo[0] != yCrosses.get(0)){
									sortCo = sortedCo;
								}
								else{
									for(int i = 0; i < sortedCo.length; i++){
										sortCo[i] = sortedCo[(sortedCo.length-1)-i];
									}
								}
							}
							sortedX = sorted;
							sortedY = sortCo;						
						}
						else{
							float[] toSort = new float[yCrosses.size()];
							for(int i = 0; i < yCrosses.size(); i++){
								toSort[i] = yCrosses.get(i);
							}
							float[] sorted = sort(toSort);
							float[] toSortCo = new float[xCrosses.size()];
							float[] sortCo = new float[xCrosses.size()];
							for(int i = 0; i < xCrosses.size(); i++){
								toSortCo[i] = xCrosses.get(i);
							}
							float[] sortedCo = sort(toSortCo);
							if(yCrosses.get(0) > yCrosses.get(yCrosses.size()-1)){
								if(sortedCo[0] != xCrosses.get(0)){
									for(int i = 0; i < sortedCo.length; i++){
										sortCo[i] = sortedCo[(sortedCo.length-1)-i];
									}
								}
								else{
									sortCo = sortedCo;
								}
							}
							else{
								if(sortedCo[0] != xCrosses.get(0)){
									sortCo = sortedCo;
								}
								else{
									for(int i = 0; i < sortedCo.length; i++){
										sortCo[i] = sortedCo[(sortedCo.length-1)-i];
									}
								}
							}
							sortedY = sorted;
							sortedX = sortCo;
						}
						for(int i = 0; i <=cr+1; i++){
							Street streetse = new Street(sortedX[i],sortedY[i],sortedX[i+1],sortedY[i+1]);
							addStreet2.add(streetse);
						}
					}
					cr++;
				}
			}
			if(crosss){
				crosss = false;
				dStreet = false;
				street.removeAll(delete2Street);
				delete2Street.removeAll(delete2Street);
				street.addAll(addStreet);
				addStreet.removeAll(addStreet);
				street.addAll(addStreet2);
				addStreet2.removeAll(addStreet2);
			}
		}
		
		if(newStreetSet){
			crosses = 0;
			countedxPos = new ArrayList<Float>();
			countedyPos = new ArrayList<Float>();
			for(Street str: street){
				float x1 = str.getX1();
				float y1 = str.getY1();
				float x2 = str.getX2();
				float y2 = str.getY2();
				int count1 = 0;
				int count2 = 0;
				boolean crossNotYetCounted1 = true;
				boolean crossNotYetCounted2 = true;
				for(int i = 0; i < countedxPos.size(); i++){
					float x = countedxPos.get(i);
					float y = countedyPos.get(i);
					if(x == x1 && y == y1){
						crossNotYetCounted1 = false;
					}
					if(x == x2 && y == y2){
						crossNotYetCounted2 = false;
					}
				}
				for(Street stre : street){
					float xp1 = stre.getX1();
					float yp1 = stre.getY1();
					float xp2 = stre.getX2();
					float yp2 = stre.getY2();
					if(str != stre){
						if(crossNotYetCounted1){
							if(x1 == xp1 && y1 == yp1){
								count1++;
							}
							if(x1 == xp2 && y1 == yp2){
								count1++;
							}
						}
						if(crossNotYetCounted2){
							if(x2 == xp1 && y2 == yp1){
								count2++;
							}
							if(x2 == xp2 && y2 == yp2){
								count2++;
							}
						}
						countedxPos.add(x1);
						countedyPos.add(y1);
						countedxPos.add(x2);
						countedyPos.add(y2);
					}
				}
				if(count1 >= 2){
					crosses++;
					if(crossPlaces.size() == 0){
						crossPlaces.add(x1);
						crossPlaces.add(y1);
					}
					else{
						boolean yetCrossed = false;
						for(int t = 0; t<crossPlaces.size();t+=2){
							if(crossPlaces.get(t) == x1 && crossPlaces.get(t+1) == y1){
								yetCrossed = true;
							}
						}
						if(!yetCrossed){
							crossPlaces.add(x1);
							crossPlaces.add(y1);
						}
					}
				}
				if(count2 >= 2){
					crosses++;
					if(crossPlaces.size() == 0){
						crossPlaces.add(x2);
						crossPlaces.add(y2);
					}
					else{
						boolean yetCrossed = false;
						for(int t = 0; t<crossPlaces.size();t+=2){
							if(crossPlaces.get(t) == x2 && crossPlaces.get(t+1) == y2){
								yetCrossed = true;
							}
						}
						if(!yetCrossed){
							crossPlaces.add(x2);
							crossPlaces.add(y2);
						}
					}
				}
			}
		}
		x++;
		if(x==2){
			x = 0;
			newStreetSet = false;
		}
	}
	
	public static float[] sort(float[] sor) {
		
		for (int i = 0; i < sor.length - 1; i++) {
			
			int index = i;
			
            for (int j = i + 1; j < sor.length; j++){
            	
                if (sor[j] < sor[index]){
                	
                    index = j;
                }
            }
            
            float s = sor[index];  
            sor[index] = sor[i];
            sor[i] = s;
		}
		return sor;
	}

}
