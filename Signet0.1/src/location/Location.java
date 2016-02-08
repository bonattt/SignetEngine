package location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import creatures.Creature;
import environment.Environment;
import misc.DeathException;
import misc.GameEvent;
import misc.GameLoadException;
import misc.TextTools;

public class Location {
	
	public String name, desc, fileName;
	private List<TravelPath> travelableLocations; // location name : path to location.
	private List<GameEvent> explorableFeatures;
	
	public Location(String name, String fileName, String desc,  List<TravelPath> paths, List<GameEvent> explorable){
		this.name = name;
		this.desc = desc;
		this.fileName = fileName;
		travelableLocations = paths;
		explorableFeatures = explorable;
	}
	
	public static Location loadLocation(String filePath, String fileName)
			throws FileNotFoundException, GameLoadException {
		Scanner scanner = new Scanner(new File(filePath + fileName));
		String version = scanner.nextLine();
		Location location;
		if (version.equals(Environment.ALPHA0_1)) {
			location = loadAlpha0_1(scanner, fileName);
		} else {
			scanner.close();
			throw new GameLoadException("unrecognized game version in class 'Location'");
		}
		scanner.close();
		return location;
	}
	
	private static Location loadAlpha0_1(Scanner scanner, String fileName)
			throws GameLoadException {
		
//		Scanner scanner = new Scanner(new File(filePath + fileName));
		String name = scanner.nextLine();
		String desc = scanner.nextLine();
		List<TravelPath> paths = loadTravelPathsAlpha0_1(scanner);
		List<GameEvent> explorable = loadExplorableEventsAlpha0_1(scanner);
		scanner.close();
		Location local = new Location(name, fileName, desc, paths, explorable);
		return local;
	}
	private static List<TravelPath> loadTravelPathsAlpha0_1(Scanner scanner) {
		List<TravelPath> paths = new ArrayList<TravelPath>();
		String line = scanner.nextLine();
		while (! line.equals("end paths")) {
			TravelPath current = TravelPath.loadAlpha0_1(line, scanner);
			paths.add(current);
			line = scanner.nextLine();
		}
		return paths;
	}
	private static List<GameEvent> loadExplorableEventsAlpha0_1(Scanner scanner) throws GameLoadException {
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		String line = scanner.nextLine();
		while (! line.equals("end events")) {
			GameEvent current = Environment.loadGameEventAlpha0_1(line, scanner);
			explorable.add(current);
			line = scanner.nextLine();
		}
		return explorable;
	}
	public void saveToFile(String filePath) throws FileNotFoundException {
		if (Environment.print_debugs) {
			System.out.println("saving " + filePath + fileName);
		}
		PrintWriter writer = new PrintWriter(filePath + fileName);
		writer.println(Environment.currentVersionName);
		writer.println(name);
		writer.println(desc);
		saveTravelPaths(writer);
		saveExploreEvents(writer);
		writer.close();
	}
	private void saveTravelPaths(PrintWriter writer) {
		int size = travelableLocations.size();
		for (int i = 0; i < size; i++) {
			travelableLocations.get(i).saveToFile(writer);
		}
		writer.println("end paths");
	}
	private void saveExploreEvents(PrintWriter writer) {
		int size = explorableFeatures.size();
		for (int i = 0; i < size; i++) {
			explorableFeatures.get(i).saveToFile(writer);
		}
		writer.println("end events");
	}
	/**
	 * this method is called when the player selects the "travel" option while exploring.
	 * this method prompts the player for input to select one of the paths stored in adjacentLocation, then returns
	 * the file path of that Location.
	 * @param player
	 * @return
	 * @throws DeathException 
	 */
	public String travelFrom(Creature player) throws DeathException{
		TravelPath destination = selectTravelDestination();
		if (destination != null) {
			player.travel(destination.travelTime, destination.exhaustionFactor);
			destination.displayTravelText();
			return destination.locationName();
		} else {
			return null;
		}
	}
	
	
	// calls for the player to select one of the available paths from this 
	private TravelPath selectTravelDestination(){
		if(travelableLocations.isEmpty()){
			TextTools.display("there is nowhere to travel to!");
			return null;
		}
		String[] answers = getTravelOptions();
		String question = "Where will you go?";
		int playerChoice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (playerChoice == 0){
			return null;
		}
		return travelableLocations.get(playerChoice - 1);
	}
	
	private String[] getTravelOptions() {
		int size = travelableLocations.size();
		String[] answers = new String[size + 1];
		for (int i = 0; i < size; i++) {
			TravelPath current = travelableLocations.get(i);
			answers[i] = current.pathName + " to " + current.locationName();
		}
		answers[answers.length - 1] = "cancel";
		return answers;
	}
	
	// calls for the player to select one of the available explorable events from this 
	public GameEvent selectExploreDestination(){
		if(explorableFeatures.isEmpty()){
			TextTools.display("there is nowhere to explore!");
			return null;
		}
		String[] answers = getExploreOptions();
		String question = "What do you want to explore?";
		int playerChoice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (playerChoice == 0){
			return null;
		}
		return explorableFeatures.get(playerChoice - 1);
	}
	private String[] getExploreOptions() {
		int size = explorableFeatures.size();
		String[] answers = new String[size + 1];
		for(int i = 0; i < size; i++) {
			GameEvent current = explorableFeatures.get(i);
			answers[i] = current.getName();
		}
		answers[answers.length - 1] = "cancel";
		return answers;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Location)) {
			return false;
		}
		Location local = (Location) obj;
		return (local.name.equals(this.name)) &&
				(local.desc.equals(this.desc)) &&
				(travelPathEqual(local.travelableLocations, this.travelableLocations)) &&
				(explorableEventsEqual(local.explorableFeatures, this.explorableFeatures));
	}
	private static boolean travelPathEqual(List<TravelPath> paths1, List<TravelPath> paths2) {
		int size = paths1.size();
		if (size != paths2.size()) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (! paths1.get(i).equals(paths2.get(i))) {
				return false;
			}
		}
		return true;
	}
	private static boolean explorableEventsEqual(List<GameEvent> events1, List<GameEvent> events2) {
		int size = events1.size();
		if (size != events2.size()) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (! events1.get(i).equals(events2.get(i))) {
				return false;
			}
		}
		return true;
	}
	
}
