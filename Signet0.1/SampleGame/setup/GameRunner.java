package setup;

import inventory.InventoryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import location.Location;
import location.TravelPath;
import misc.DeathException;
import misc.GameEvent;
import misc.GameLoadException;
import misc.TextTools;
import creatures.PlayerCharacter;
import environment.Environment;

public class GameRunner {

	private static final String DATFILE = "/saves.dat";
	private static final String FILEPATH = "sampleGame/saveFile";
	
	public static void main(String[] args)
				throws FileNotFoundException, GameLoadException, DeathException, InventoryException {
		while(true) {
			String question = "Select a save file to load";
			List<String> saveFiles = getSaveStrings(FILEPATH + DATFILE);
			int choice = TextTools.questionAsker(question, saveFiles, TextTools.BACK_DISABLED);
			String selectedSave = saveFiles.get(choice);
			useSaveString(selectedSave, FILEPATH);
		}
	}
	
	private static boolean useSaveString(String saveStr, String filePath)
				throws GameLoadException, FileNotFoundException, DeathException, InventoryException {
		String saveFilename = saveStr.split(" ")[0];
		String code = saveStr.split(" ")[1];
		if (code.equals("[empty]")) {
			useNewSave(saveFilename, filePath);
		} else {
			useExistingSave(saveFilename, filePath);
		}
		return false;
	}
	
	private static boolean useNewSave(String saveName, String filePath)
				throws GameLoadException, FileNotFoundException, DeathException, InventoryException {
		String question = "";
		String[] answers = new String[]{"new game", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		
		if (choice == 1) {
			startNewGame(filePath);
			return false;
		} else if (choice == 0) {
			// do nothing.
			return true;
		} else {
			throw new GameLoadException("non-existant option selected in 'useSaveString'");
		}
	}
	
	private static boolean useExistingSave(String saveName, String filePath)
				throws GameLoadException, FileNotFoundException, DeathException, InventoryException {
		String question = "";
		String[] answers = new String[]{"continue", "cancel", "delete"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		
		if (choice == 1) {
			continueGame(filePath + saveName);
			return false;
		} else if (choice == 2) {
			// do nothing
			return true;
		} else if (choice == 0) {
			deleteSave(saveName, filePath);
			return true;
		} else {
			throw new GameLoadException("non-existant option selected in 'useExistingSave'");
		}
	}
	
	private static void deleteSave(String saveName, String filePath) throws FileNotFoundException {
		List<String> lines = readDatFileLines(filePath+ DATFILE);
		String[][] fileData = parseFileDataAndDelete(lines, saveName);
		rewriteSaveDat(filePath + DATFILE, fileData);
	}
	
	private static List<String> readDatFileLines(String datFilepath) throws FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(datFilepath));
		String line = scanner.nextLine();
		while(! line.equals("end")) {
			lines.add(line);
			line = scanner.nextLine();
		}
		scanner.close();
		return lines;
	}
	
	private static String[][] parseFileDataAndDelete(List<String> lines, String saveName) {
		String[][] fileData = new String[lines.size()][2];
		for (int i = 0; i < lines.size(); i++) {
			fileData[i] = lines.get(i).split(" ");
			if(fileData[i][0].equals(saveName)) {
				fileData[i][1] = "[empty]";
			}
		}
		return fileData;
	}
	
	private static void rewriteSaveDat(String datFilepath, String[][] saveDat) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(datFilepath);
		for (String[] line : saveDat) {
			writer.printf("%s %s", line[0], line[1]);
		}
		writer.close();
	}
	
	private static List<String> getSaveStrings(String datFilepath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(datFilepath));
		String line = scanner.nextLine();
		List<String> lines = new ArrayList<String>();
		while(! line.equals("end")) {
			lines.add(line);
			line = scanner.nextLine();
		}
		scanner.close();
		return lines;
	}
	
	private static void startNewGame(String filePath)
				throws FileNotFoundException, GameLoadException, DeathException, InventoryException {
		setupLocation(filePath);
		// TODO more stuff
		continueGame(filePath);
	}
	
	private static void continueGame(String filePath)
				throws FileNotFoundException, GameLoadException, DeathException, InventoryException {
		Environment ev = new Environment(filePath);
		PlayerCharacter player = loadPlayer(filePath);
		String startLocation = "";
		ev.startGame(player, startLocation);
	}
	
	private static PlayerCharacter loadPlayer(String filePath) {
		return null;
	}
	
	public static void setupLocation(String filepath) throws FileNotFoundException {
		List<Location> locs = new ArrayList<Location>(12);
		addAllLocations(locs);
		Location.saveAll(locs, filepath);
	}
	
	private static void addAllLocations(List<Location> locs) {
		locs.add(getCargoBay());
		locs.add(getBridge());
		locs.add(getCaptainsQuarters());
		locs.add(getCrewBathrooms());
		locs.add(getDeckC());
		locs.add(getDeckO());
		locs.add(getEngineering());
		locs.add(getMessHall());
		locs.add(getOfficerBathrooms());
		locs.add(getOfficerMess());
		locs.add(getRecHall());
	}
	
	private static Location getBridge() {
		String name = "the bridge";
		String filename = "/bridge.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}
	
	private static Location getDeckC() {
		String name = "C-deck hallway";
		String filename = "/see_deck.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}
	
	private static Location getCaptainsQuarters() {
		String name = "captains quarters";
		String filename = "/quarters.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}
	
	private static Location getDeckO() {
		String name = "O-deck hallway";
		String filename = "/oh_deck.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}
	
	private static Location getCargoBay() {
		String name = "cargo bay";
		String filename = "/cargo_bay.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}

	private static Location getOfficerMess() {
		String name = "officers' mess";
		String filename = "/of_mess.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}

	private static Location getMessHall() {
		String name = "mess hall";
		String filename = "/mess.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}
	
	private static Location getCrewBathrooms() {
		String name = "crew bathrooms";
		String filename = "/crew_bath.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}

	private static Location getOfficerBathrooms() {
		String name = "officers' bathroom";
		String filename = "/of_bath.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}

	private static Location getEngineering() {
		String name = "engineering";
		String filename = "/engi.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}

	private static Location getRecHall() {
		String name = "rec hall";
		String filename = "/rec_hall.sig";
		String desc = "";
		List<TravelPath> paths = new ArrayList<TravelPath>();
		List<GameEvent> explorable = new ArrayList<GameEvent>();
		
		return new Location(name, filename, desc, paths, explorable);
	}
}
