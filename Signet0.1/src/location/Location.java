package location;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import creatures.Creature;
import misc.DeathException;
import misc.GameEvent;
import misc.TextTools;

public class Location {
	
	public String name;
	private List<TravelPath> travelableLocations; // location name : path to location.
	private List<GameEvent> explorableFeatures;
	
	public Location(String name, List<TravelPath> paths, List<GameEvent> explorable){
		this.name = name;
		travelableLocations = paths;
		explorableFeatures = explorable;
	}
	
	/**
	 * This is a helper called in the constructor that reads the file given in the constructor.
	 * the first line in the file is set as the location's name and not handled by the helper.
	 * Every subsequent line is passed into a helper that parses them and takes appropriate action
	 * to load either a new travel path or new explorable object.
	 * @param fileName
	 */
	public void loadAlpha0_1(Scanner scanner){
		String name = scanner.nextLine();
	}
	private List<TravelPath> loadTravelPathsAlpha0_1(Scanner scanner) {
		List<TravelPath> paths = new ArrayList<TravelPath>();
		String line = scanner.nextLine();
		while (! line.equals("end")) {
			
			line = scanner.nextLine();
		}
		
		return paths;
	}
	
	
	
	/**
	 * this should save the location to a file with the same conventions used by the loadFromFile method in this class.
	 * @return
	 */
	public void saveToFile(PrintWriter writer) {
		writer.println(name);
		saveTravelPaths(writer);
		saveExploreEvents(writer);
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
		}
		return destination.locationName();
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
}
