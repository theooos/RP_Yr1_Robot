package Testing.Localisation;



import java.util.ArrayList;

import lejos.geom.Point;
import lejos.nxt.LCD;
import rp.robotics.mapping.GridMap;

public class ProbableLocations {

	private GridMap map;
	private float coordRange;
	private float threshold = 6.0f;
	private float appropriateRange = 50.0f;
	ArrayList<Point> locs = new ArrayList<Point>();
	public ProbableLocations(GridMap map){
		this.map=map;
		for(int i=0;i<=map.getXSize();i++){
		
			for(int j=0;j<=map.getYSize();j++){
				if(!map.isObstructed(i, j)){
					locs.add(new Point(i,j));
				
				}
			}
		}
	}
	
	public void setLocations(float heading,float range){
		for(int i=0;i<map.getXSize();i++){
			for(int j=0;j<map.getYSize();j++){
				if(!map.isObstructed(i, j)){
					coordRange=map.rangeToObstacleFromGridPosition(i, j, heading)*100;
					//LCD.drawString(coordRange+" -r-  "+range, 0, i);
						if(range > appropriateRange){
							if(coordRange <= range - threshold){
								locs.remove(new Point(i,j));
								System.out.println("*i = " + i + "; j = " + j + " coordRange: " + coordRange);
							}
						}
						else if(!(coordRange<=range+threshold && coordRange>=range-threshold)){
							locs.remove(new Point(i,j));
						System.out.println("**i = " + i + "; j = " + j + " coordRange: " + coordRange);
						}
				}else{
					locs.remove(new Point(i,j));
					System.out.println("***i = " + i + "; j = " + j + " coordRange: " + coordRange);
				}
			}
		}
		}
	
	public Point getPoints(int i){
		return locs.get(i);
	}
	
	public int size(){
		return locs.size();
	}
	
	public void updateLocations(float heading){
		if(heading==180)
			for(int i=0;i<locs.size();i++){
				locs.set(i,new Point((float)locs.get(i).getX()-1,(float)locs.get(i).getY()));
			}
		else if(heading==0)
			for(int i=0;i<locs.size();i++){
				locs.set(i,new Point((float)locs.get(i).getX()+1,(float)locs.get(i).getY()));
			}
		else if(heading==90)
			for(int i=0;i<locs.size();i++){
				locs.set(i,new Point((float)locs.get(i).getX(),(float)locs.get(i).getY()+1));
			}
		else if(heading==-90)
			for(int i=0;i<locs.size();i++){
				locs.set(i,new Point((float)locs.get(i).getX()-1,(float)locs.get(i).getY()-1));
			}
		else 
			assert false : "Unknown value for enumeration";
	}
	
	public void clear(){
		locs.clear();
	}
}
