package environment;

import inventory.InventoryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import testingMothers.LootGenericChest;
import location.Location;
import location.LocationIndex;
import misc.DeathException;
import misc.GameEvent;
import misc.GameLoadException;
import misc.TextTools;
import creatures.PlayerCharacter;

public class Environment {
	
	public static Environment instance;
	public static final Scanner playerInput = new Scanner(System.in);
	
	public static final String indexFilePath = "src/environment/locationIndex";
	
	public static boolean print_debugs = true;
	public static final HashMap<String, GameEvent> eventIndex = getEventIndex();	// event name : event
	
	private GameEvent[] events;
	
	private Weather weather; // percipitation, season.
	private Location location;
	private Terrain terrain;
	private GameClock clock;
	private PlayerCharacter player;
	private LocationIndex locationIndex;

	private static final int default_start_time = 8000;
	private static final int default_start_date = 13;
	private static final int default_start_month = 5;
	
	public static final String currentVersionName = "SIGNET alpha version 0.1";
	
	public static final String ALPHA0_1 = "SIGNET alpha version 0.1";
	
	private static final String SAVEFILE_ROOTPATH = "src/savedFiles/";
	private static final String SAVE01 = "save01/";
	private static final String MAIN_SAVE_FILE = "main.sigsav";
	
	public Environment(String filePath)
			throws FileNotFoundException, GameLoadException{
		this(filePath, default_start_time, default_start_date, default_start_month);
	}
	public Environment(String filePath, int startTime, int startDate, int startMonth)
			throws FileNotFoundException, GameLoadException{
		locationIndex = initializeLocationIndex(filePath);
		clock = new GameClock(startTime, startDate, startMonth);
	}
	private static LocationIndex initializeLocationIndex(String filePath) throws FileNotFoundException {
		LocationIndex index = new LocationIndex(filePath);
		return index;
	}
	
	private static String compileFullSaveFilePath(String saveName) {
		return SAVEFILE_ROOTPATH + saveName + MAIN_SAVE_FILE;
	}
	private static String compileLocationSaveRootFilePath(String saveName) {
		return SAVEFILE_ROOTPATH + saveName;
	}
	
	public void startGame(PlayerCharacter player, String startLocation)
			throws FileNotFoundException, GameLoadException, DeathException, InventoryException {
		this.player = player;
		location = locationIndex.get(startLocation);
		choseAction();
	
	}
	
	public void setPlayer(PlayerCharacter player){
		this.player = player;
	}
	
	public static Environment getInstance() {
		return instance;
	}
	
	public void choseAction() throws DeathException, InventoryException {
		TextTools.display("\n\n\n\n\n\n\n\n\nwelcome to signet!\n");
		while (true) {
			String question = "You are in " + location.name + " what will you do?";
			String[] answers = new String[]{"travel", "explore", "inventory", "save and quit"};
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				travel();
			} else if (choice == 2){
				explore();
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
	public static void loadGame(String saveName){
		loadGameFromFile(compileFullSaveFilePath(saveName));
	}	
	public void saveGame(String saveName){
		saveGameToFile(compileFullSaveFilePath(saveName));
	}
	public static void loadGameFromFile(String filePath){
		try {
			Scanner scanner = new Scanner(new File(filePath));
			String versionStr = scanner.nextLine();
			if(versionStr.equals(ALPHA0_1)){
				loadAlpha0_1(scanner);
			} else {
				System.out.println("Unknown game version \nGame load canceled.");
			}
			scanner.close();			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR laoding file - file not found");
			e.printStackTrace();
		} catch (GameLoadException e) {
			TextTools.display("Error in loading save data, save data corrupted.");
		}
	}
	public static void loadAlpha0_1(Scanner scanner) throws GameLoadException{
//		weather; 
//		location;
//		terrain;
//		clock;
		PlayerCharacter player = PlayerCharacter.loadAlpha0_1(scanner);
	}
	
	public void saveGameToFile(String filePath){
		try {
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			writer.println(currentVersionName);
			player.saveToFile(writer);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileNotFoundException exception in save game");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException exception in save game");
		} 
	}
	public void travel() throws DeathException{
		String locationName = location.travelFrom(player);
		if (locationName != null) {
			try {
				location = locationIndex.get(locationName);
			} catch (FileNotFoundException e) {
				TextTools.display(String.format("ERROR %s is inaccessable due to a file load error.", locationName));
			} catch (GameLoadException e) {
				TextTools.display("failed to load new location.");
			}
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
	
	public GameEvent getEvent(int index){
		return events[index];
	}
	
	private static HashMap<String, GameEvent> getEventIndex(){
		HashMap<String, GameEvent> events = new HashMap<String, GameEvent>();
		events.put("Loot Chest", new LootGenericChest());
		return events;
	}
	
	public static GameEvent loadGameEventAlpha0_1(Scanner scanner) throws GameLoadException {
		String lineHeader = scanner.nextLine();
		return loadGameEventAlpha0_1(lineHeader, scanner);
	}
	
	public static GameEvent loadGameEventAlpha0_1(String lineHeader, Scanner scanner) throws GameLoadException {
		if (lineHeader.equals("loot generic chest event")) {
			return LootGenericChest.loadAlpha0_1(scanner);
		}
		throw new GameLoadException("unrecognized game event");
	}
	
	
	
}
