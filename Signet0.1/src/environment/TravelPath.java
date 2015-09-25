package environment;

import java.util.Random;

import creatures.Creature;
import misc.GameEvent;

public class TravelPath {
	
	public String name;
	protected String fileName;
	protected int travelTime;
	protected int exhaustionFactor;
	private GameEvent[] randomTravelEncounters;
	private int encounterOdds;
	
	public TravelPath(String file, int time, int exhaustion, GameEvent[] encounters, int odds){
		fileName = file;
		travelTime = time;
		exhaustionFactor = exhaustion;
		encounterOdds = odds;
		randomTravelEncounters = encounters;
	}
	public GameEvent getRandomEncounter(){
		Random r = new Random();
		if (r.nextInt(100) > encounterOdds) {
			return randomTravelEncounters[ r.nextInt(randomTravelEncounters.length) ];
		}		
		return null;
	}
	public String getTravelText(){
		
		return "you traveled on the " + name;
	}
}
