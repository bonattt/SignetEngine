package environment;

import indices.*;

//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
//import java.util.stream.Stream;

import sampleEvents.LootGenericChest;
import misc.DeathException;
import misc.GameEvent;
import misc.TextTools;
import creatures.PlayerCharacter;

public class Environment {
	
	public static Environment instance;
	
	public static final String indexFilePath = "src/environment/locationIndex";
	
	public static boolean print_debugs = true;
	public static final HashMap<String, GameEvent> eventIndex = getEventIndex();	// event name : event
	public static final LocationIndex locationIndex = new LocationIndex(indexFilePath);							// file path : Location
	public static Scanner scanner;
	
	private GameEvent[] events;
	
	private Weather weather; // percipitation, season.
	private Location location;
	private Terrain terrain;
	private GameClock clock;
	private PlayerCharacter player;

	private static final int default_start_time = 8000;
	private static final int default_start_date = 13;
	private static final int default_start_month = 5;
	
	public Environment(String startLocation){
		this(startLocation, default_start_time, default_start_date, default_start_month);
	}
	public Environment(String startLocation, int startTime, int startDate, int startMonth){
		scanner = new Scanner(System.in);
		location = locationIndex.get(startLocation);
		clock = new GameClock(startTime, startDate, startMonth);
	}
	public void setPlayer(PlayerCharacter player){
		this.player = player;
	}
	
	public static Environment getInstance() {
		return instance;
	}
	
	public void choseAction() throws DeathException {
		TextTools.display("\n\n\n\n\n\n\n\n\nwelcome to signet!\n");
		while (true) {
			String question = "You are in " + location.name + " what will you do?";
			String[] answers = new String[]{"travel", "explore", "inventory", "save and quit"};
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				travel();
				
			} else if (choice == 2){
				GameEvent event = location.selectExploreDestination();
				event.triggerEvent(player);
			} else if (choice == 3) {
				player.getInventory().accessInventoryDuringExplore(player);
			} else if (choice == 0){
				choice = TextTools.questionAsker("Are you sure?", new String[]{"yes", "no"}, TextTools.BACK_ENABLED);
				if (choice == 1){
					TextTools.display("Saving game...\nGame saved! (not really)");
					break;
				}
			} else {
				System.err.println("player chose an invalid choice");
			}
		}
	}
	
	public void travel() throws DeathException{
		String fileName = location.travel(player);
		if (locationIndex.containsKey(fileName)){
			location = locationIndex.get(fileName);
		} else {
			location = new Location(fileName);
		}
	}
	public void explore(){
		GameEvent event = location.selectExploreDestination();
		if (event != null){
			try {
				event.triggerEvent(player);
			} catch (DeathException e) {
				TextTools.display("You died.");
			}
		}
	}
	
	private int exploreOptions(){
		
		return Integer.MIN_VALUE;
	}
	
	public GameEvent getEvent(int index){
		return events[index];
	}
	
	
	
	private static HashMap<String, GameEvent> getEventIndex(){
		HashMap<String, GameEvent> events = new HashMap<String, GameEvent>();
		events.put("Loot Chest", new LootGenericChest());
		return events;
	}
	
	
	
}
