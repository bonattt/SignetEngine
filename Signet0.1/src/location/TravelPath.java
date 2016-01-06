package location;

import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import creatures.Creature;
import misc.GameEvent;
import misc.TextTools;

public class TravelPath {
	
	protected String pathName;
	protected String locationName;
	protected int travelTime;
	protected double exhaustionFactor;
	
	public TravelPath(String name, String location, int time, double exhaustion){
		pathName = name;
		locationName = location;
		travelTime = time;
		exhaustionFactor = exhaustion;
	}
	public String pathName() {
		return pathName;
	}
	public String locationName() {
		return locationName;
	}
	public int travelTime() {
		return travelTime;
	}
	public double exhaustion() {
		return exhaustionFactor;
	}
	
	public void displayTravelText(){
		TextTools.display("you traveled on the " + pathName);
	}
	public void saveToFile(PrintWriter writer) {
		writer.println(pathName);
		writer.println(locationName);
		writer.println(travelTime);
		writer.println(exhaustionFactor);
	}
	public TravelPath loadAlpha0_1(Scanner scanner) {
		String pathName = scanner.nextLine();
		String locationName = scanner.nextLine();
		int travelTime = scanner.nextInt();
		int exhaustionFactor = scanner.nextInt();
		scanner.nextLine();
		TravelPath path = new TravelPath(pathName, locationName, travelTime, exhaustionFactor);
		return path;
	}
	
	
}
