package environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import creatures.Creature;
import creatures.PlayerCharacter;
import misc.GameEvent;
import misc.TextTools;

public class Location {
	
	public String name;
	
	private String fileName;	
	private HashMap<String, TravelPath> adjacentLocations; // location name : path to location.
	private HashMap<String, GameEvent> explorableFeatures;
	
	public Location(String fileName){
		adjacentLocations = new HashMap<String, TravelPath>();
		explorableFeatures = new HashMap<String, GameEvent>();
		loadFromFile(fileName);
	}
	
	protected HashMap<String, TravelPath> getAdjacent(){
		return adjacentLocations;
	}
	/**
	 * This is a helper called in the constructor that reads the file given in the constructor.
	 * the first line in the file is set as the location's name and not handled by the helper.
	 * Every subsequent line is passed into a helper that parses them and takes appropriate action
	 * to load either a new travel path or new explorable object.
	 * @param fileName
	 */
	public void loadFromFile(String fileName){
		int tries = 0;
		while(tries < 5){
			try {
			    BufferedReader reader = new BufferedReader( new FileReader (fileName));
			    String line;
			    int lineNumber = 1;
				while( ( line = reader.readLine() ) != null ) {
					if (Environment.getInstance().print_debugs){
						System.out.print("location >>> read file line '"+line+"', ");
					}
					if (lineNumber == 1){
						name = line;
						if (Environment.getInstance().print_debugs){
							System.out.println("action: add as name");
						}
						lineNumber++;
						continue;
					} 
					parseLineInLoad(line, reader);
					lineNumber++;
				}
				reader.close();
				return;
			} catch (IOException e) {
				System.err.print(e);
				if (tries == 5){
					e.printStackTrace();
				}
				// roll back creation of the location.
				adjacentLocations = new HashMap<String, TravelPath>();
				explorableFeatures = new HashMap<String, GameEvent>();
			}
			tries++;
		}
		// TODO throw an exception
	}
	/**
	 * This private helper method reads the line of text given to it and calls the appropriate set up for the declared setup Op.
	 * The first file line should be a location name, and should not be handled by this method.
	 * ADJACENT setup op should call a helper to add a TravelPath object to this location.
	 * FEATURE setup op should call a helper to add an explorable object (an event) to this location.
	 * empty lines will be gracefully ignored.
	 * any other line not handled within a declared op will cause the code to shout at you in the console, but will be ignored.
	 * @param line
	 * @throws IOException 
	 */
	private void parseLineInLoad(String line, BufferedReader reader) throws IOException{
		String debugPrint = "";
		
		if (line.equals("")){
			debugPrint = "action: no action";
		}
		else if (line.toLowerCase().equals("adjacent")){
			debugPrint = "action: add new path";
			handleAddPath(reader);
		} else if (line.toLowerCase().equals("features")) {
			debugPrint = "action: add explorable objects (events)";
			handleAddExplorable(reader);
		} else {
			System.err.println("unreadable line '" + line + "'in location file was omitted");
		}
		
		if (Environment.getInstance().print_debugs){
			System.out.println(debugPrint);
		}
	}
	// builds a TravelPath object and adds it to the HashMap field containing travelPaths.
	private void handleAddPath(BufferedReader reader) throws IOException{
		String pathName = reader.readLine();
		String filePath = reader.readLine();
		
		String[] lines = reader.readLine().split(", ");
		int travelTime = Integer.parseInt((lines[0]));
		int exhaustionFactor = Integer.parseInt((lines[1]));
		
		String[] eventNames = reader.readLine().split(", ");
		GameEvent[] events = new GameEvent[]{null};
		// TODO
		int chanceOfRandomEncounter = Integer.parseInt(reader.readLine());
		
		TravelPath newPath = new TravelPath(filePath, travelTime, exhaustionFactor, events, chanceOfRandomEncounter);
		newPath.name = pathName;
		adjacentLocations.put(pathName, newPath);
	}
	// 
	private void handleAddExplorable(BufferedReader reader) throws IOException{
		String line = reader.readLine();
		while(! line.toLowerCase().equals("end features")){
			String[] args = line.split(", ");
			HashMap<String, GameEvent> eventIndex = Environment.eventIndex;
			GameEvent event = eventIndex.get(args[0]);
			if (event == null){
				System.err.println("ommited a non-registered event '" + line + "'");
				line = reader.readLine();
				continue;
			}
			
			explorableFeatures.put(line, event);
			if (Environment.getInstance().print_debugs){
				System.out.println("added explorable event '" + line + "' : " + event);
			}
			line = reader.readLine();
		}
	}
	
	/**
	 * this should save the location to a file with the same conventions used by the loadFromFile method in this class.
	 * @return
	 */
	public boolean saveToFile(){
		//TODO implement saveToFile
		return false;
	}
	/**
	 * this method is called when the player selects the "travel" option while exploring.
	 * this method prompts the player for input to select one of the paths stored in adjacentLocation, then returns
	 * the file path of that Location.
	 * @param player
	 * @return
	 */
	public String travel(Creature player){
		
		TravelPath path = selectTravelDestination();
		
		if (path != null){
			player.travel(path.travelTime, path.exhaustionFactor);
			TextTools.display(path.getTravelText());
			path.getRandomEncounter();
			return path.fileName;
		}
		return null;
	}
	// calls for the player to select one of the available paths from this 
	private TravelPath selectTravelDestination(){
		if(adjacentLocations.isEmpty()){
			TextTools.display("there is nowhere to travel to!");
			return null;
		}
		String[] answers = new String[adjacentLocations.size() + 1];
		TravelPath[] paths = new TravelPath[adjacentLocations.size()];
		int i = 0;
		for (String key : adjacentLocations.keySet()){
			TravelPath path = adjacentLocations.get(key);
			answers[i] = path.name;
			paths[i] = path;
			i++;
		}
		answers[answers.length-1] = "cancel";
		String question = "Where will you go?";
		int playerChoice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (playerChoice == 0){
			return null;
		}
		return paths[playerChoice - 1];
	}
	// calls for the player to select one of the available explorable events from this 
	protected GameEvent selectExploreDestination(){
		if(explorableFeatures.isEmpty()){
			TextTools.display("there is nowhere to explore!");
			return null;
		}
		String[] answers = new String[explorableFeatures.size() + 1];
		GameEvent[] events = new GameEvent[explorableFeatures.size()];
		int i = 0;
		for (String key : explorableFeatures.keySet()){
			GameEvent event = explorableFeatures.get(key);
			answers[i] = event.getName();
			events[i] = event;
			i++;
		}
		answers[answers.length-1] = "cancel";
		String question = "What do you want to explore?";
		int playerChoice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (playerChoice == 0){
			return null;
		}
		return events[playerChoice - 1];
	}
	
}
