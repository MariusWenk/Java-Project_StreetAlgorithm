package StreetCalculator;

import java.util.ArrayList;

public class Calculator {
	
	ArrayList<Street> street;
	Start start;
	Ziel ziel;
	ArrayList<ArrayList<Float>> zielPos = new ArrayList<ArrayList<Float>>();
	ArrayList<ArrayList<Float>> addPos = new ArrayList<ArrayList<Float>>();
	ArrayList<ArrayList<Float>> deletePos = new ArrayList<ArrayList<Float>>();
	ArrayList<ArrayList<Float>> PosList = new ArrayList<ArrayList<Float>>();
	ArrayList<Street> streetSolution = new ArrayList<Street>();
	ArrayList<Street> streetSolution1 = new ArrayList<Street>();
	ArrayList<ArrayList<Street>> streetRows = new ArrayList<ArrayList<Street>>();
	ArrayList<Float> Po = new ArrayList<Float>();
	int b = 0;
	int u = 2;
	boolean c = false;
	boolean d = false;
	float x;
	float y;
	float redLength;
	float blueLength;
	
	public Calculator(){}
	
	public boolean calculate(ArrayList<Street> street, Start start, Ziel ziel, int speed, int crosses, ArrayList<Float> crossPlaces, float averageTLWaitingTime) {
		this.street = street;
		this.start = start;
		this.ziel = ziel;
		zielPos = new ArrayList<ArrayList<Float>>();
		addPos = new ArrayList<ArrayList<Float>>();
		deletePos = new ArrayList<ArrayList<Float>>();
		PosList = new ArrayList<ArrayList<Float>>();
		streetSolution = new ArrayList<Street>();
		streetSolution1 = new ArrayList<Street>();
		d = false;
		for(Street str: street){
			float x1 = str.getX1();
			float y1 = str.getY1();
			float x2 = str.getX2();
			float y2 = str.getY2();
			float xStart = start.getX()+40;
			float yStart = start.getY()+40;
			if(x1 == xStart && y1 == yStart){
				ArrayList<Float> Pos = new ArrayList<Float>();
				Pos.add(x2);
				Pos.add(y2);
				PosList.add(Pos);
				d = true;
			}
			if(x2 == xStart && y2 == yStart){
				ArrayList<Float> Pos = new ArrayList<Float>();
				Pos.add(x1);
				Pos.add(y1);
				PosList.add(Pos);
				d = true;
			}
		}
		
		if(d == true){
			b = crosses;
			while(b<=street.size()){
//			int f = 0;
//			if(street.size() > 35){
//				f = 25;
//			}
//			while(f<=35){
//			f++;
				for(ArrayList<Float> P : PosList){
					float xEnde = P.get(P.size()-2);
					float yEnde = P.get(P.size()-1);
					float xZiel = ziel.getX()+40;
					float yZiel = ziel.getY()+40;
					if(xEnde == xZiel && yEnde == yZiel){
						c = false;
						zielPos.add(P);
						deletePos.add(P);
					}
				}
				PosList.removeAll(deletePos);
				deletePos.removeAll(deletePos);
				
				for(Street str: street){
					float x1 = str.getX1();
					float y1 = str.getY1();
					float x2 = str.getX2();
					float y2 = str.getY2();
					float xStart = start.getX()+40;
					float yStart = start.getY()+40;
					for(ArrayList<Float> P : PosList){
						float xEnde = P.get(P.size()-2);
						float yEnde = P.get(P.size()-1);
						if(xEnde <= x1+u && xEnde >= x1-u && yEnde <= y1+u && yEnde >= y1-u){
							x = x2;
							y = y2;
							if( !(xStart == x2 && yStart == y2) ){
								c = true;
							}
							int n = P.size()/2;
							while(n>1){
								float xAnfang = P.get(P.size()-(n*2));
								float yAnfang = P.get(P.size()-((n*2)-1));
								if(xAnfang <= x2+u && xAnfang >= x2-u && yAnfang <= y2+u && yAnfang >= y2-u){
									c = false;
								}
								n--;
							}
						}
						if(xEnde <= x2+u && xEnde >= x2-u && yEnde <= y2+u && yEnde >= y2-u){
							x = x1;
							y = y1;
							if( !(xStart == x1 && yStart == y1) ){
								c = true;
							}
							int n = P.size()/2;
							while(n>1){
								float xAnfang = P.get(P.size()-(n*2));
								float yAnfang = P.get(P.size()-((n*2)-1));
								if(xAnfang <= x1+u && xAnfang >= x1-u && yAnfang <= y1+u && yAnfang >= y1-u){
									c = false;
								}
								n--;
							}
						}
						if(c == true){
								deletePos.add(P);
								Po = new ArrayList<Float>();
								for(int z = P.size(); z>=1;z--){
									Po.add(P.get(P.size()-z));
								}
								Po.add(x);
								Po.add(y);
								addPos.add(Po);
							c = false;
						}
					}
				}
				PosList.removeAll(deletePos);
				PosList.addAll(addPos);
				deletePos.removeAll(deletePos);
				addPos.removeAll(addPos);
				b++;
			}
		}
		
		float[] lengths = new float[zielPos.size()];
		float[] lengths1 = new float[zielPos.size()];
		int i = 0;
		streetRows.removeAll(streetRows);
		for(ArrayList<Float> P : zielPos){
			ArrayList<Street> streetRow = new ArrayList<Street>();
			float xStart = start.getX()+40;
			float yStart = start.getY()+40;
			float x = P.get(0);
			float y = P.get(1);
			Street stree = new Street(xStart,yStart,x,y);
			streetRow.add(stree);
			int crosse = 0;
			for(int t = 0; t < P.size()-2;t += 2){
				float xCr = P.get(t);
				float yCr = P.get(t+1);
				for(int e = 0; e < crossPlaces.size();e += 2){
					float xCross = crossPlaces.get(e);
					float yCross = crossPlaces.get(e+1);
					if(xCr == xCross && yCr == yCross){
						crosse++;
					}
				}
			}
			int n = P.size()/2;
			while(n>1){
				float x1 = P.get(P.size()-(n*2));
				float y1 = P.get(P.size()-((n*2)-1));
				float x2 = P.get(P.size()-((n*2)-2));
				float y2 = P.get(P.size()-((n*2)-3));
				Street stre = new Street(x1,y1,x2,y2);
				streetRow.add(stre);
				n--;
			}
			streetRows.add(streetRow);
			float length = 0;
			for(Street st : streetRow){
				length += st.getCrossTime(speed);
			}
			float speee = (float) speed;
			length += (1/4) * (speee / 10);
			float length1 = length;
			length += crosse*averageTLWaitingTime;
			lengths[i] = length;
			lengths1[i] = length1;
			i++;
		}
		float[] sortLengths = sort(lengths);
		float[] sortLengths1 = sort(lengths1);
		
		int s = 0;
		int d = 0;
		int a = 0;
		for(ArrayList<Street> row : streetRows){
			float speee = (float) speed;
			float length = (1/4) * (speee / 10);
			int crosse = 0;
			ArrayList<Float> yetFoundCross = new ArrayList<Float>();
			float xZiel = ziel.getX()+40;
			float yZiel = ziel.getY()+40;
			float xStart = start.getX()+40;
			float yStart = start.getY()+40;
			yetFoundCross.add(xZiel);
			yetFoundCross.add(yZiel);
			yetFoundCross.add(xStart);
			yetFoundCross.add(yStart);
			for(Street st : row){
				length += st.getCrossTime(speed);
				float xCr1 = st.getX1();
				float yCr1 = st.getY1();
				float xCr2 = st.getX2();
				float yCr2 = st.getY2();
				boolean Cr1Found = false;
				boolean Cr2Found = false;
				for(int l = 0; l < yetFoundCross.size();l+=2){
					float x = yetFoundCross.get(l);
					float y = yetFoundCross.get(l+1);
					if(x == xCr1 && y == yCr1){
						Cr1Found = true;
					}
					if(x == xCr2 && y == yCr2){
						Cr2Found = true;
					}
				}
				for(int e = 0; e < crossPlaces.size();e += 2){
					float xCross = crossPlaces.get(e);
					float yCross = crossPlaces.get(e+1);
					if(xCr1 == xCross && yCr1 == yCross && !Cr1Found){
						yetFoundCross.add(xCr1);
						yetFoundCross.add(yCr1);
						crosse++;
					}
					if(xCr2 == xCross && yCr2 == yCross && !Cr2Found){
						yetFoundCross.add(xCr2);
						yetFoundCross.add(yCr2);
						crosse++;
					}
				}
			}
			float length1 = length;
			length += crosse*averageTLWaitingTime;
			if(length == sortLengths[0]){
				s = a;
			}
			if(length1 == sortLengths1[0]){
				d = a;
			}
			redLength = sortLengths[0];
			blueLength = sortLengths1[0];
			a++;
		}
		
		if(zielPos.size() > 0){
			ArrayList<Float> Posi = zielPos.get(s);
			ArrayList<Float> Posi1 = zielPos.get(d);
			float xStart = start.getX()+40;
			float yStart = start.getY()+40;
			float x = Posi.get(0);
			float y = Posi.get(1);
			float xa = Posi1.get(0);
			float ya = Posi1.get(1);
			Street stree = new Street(xStart,yStart,x,y);
			Street stree1 = new Street(xStart,yStart,xa,ya);
			streetSolution.add(stree);
			streetSolution1.add(stree1);
			int n = Posi.size()/2;
			int n1 = Posi1.size()/2;
			while(n>1){
				float x1 = Posi.get(Posi.size()-(n*2));
				float y1 = Posi.get(Posi.size()-((n*2)-1));
				float x2 = Posi.get(Posi.size()-((n*2)-2));
				float y2 = Posi.get(Posi.size()-((n*2)-3));
				Street stre = new Street(x1,y1,x2,y2);
				streetSolution.add(stre);
				n--;
			}
			while(n1>1){
				float x1 = Posi1.get(Posi1.size()-(n1*2));
				float y1 = Posi1.get(Posi1.size()-((n1*2)-1));
				float x2 = Posi1.get(Posi1.size()-((n1*2)-2));
				float y2 = Posi1.get(Posi1.size()-((n1*2)-3));
				Street stre = new Street(x1,y1,x2,y2);
				streetSolution1.add(stre);
				n1--;
			}
			return true;
		}
		return false; 
		
	}
	
	public double getLength(float x1, float y1, float x2, float y2) {
		double addition = (double) (((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
		double length = Math.pow(addition, 0.5);
		return length;
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
	
	public ArrayList<Street> getStreetSolution(){
		return streetSolution;
	}
	
	public ArrayList<Street> getStreetSolution1(){
		return streetSolution1;
	}
	
	public float getRedLength(){
		return redLength;
	}
	
	public float getBlueLength(){
		return blueLength;
	}
}
