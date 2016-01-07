package location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class LocationIndex {

	private HashMap<String, Location> map;
	private HashMap<String, String> fileNameMap;
	
	public LocationIndex(String filePath) throws FileNotFoundException {
		map = new HashMap<String, Location>();
		fileNameMap = new HashMap<String, String>();
	}
	
	public void saveLocations(String filePath) throws FileNotFoundException {
		for (String key : map.keySet()) {
			Location current = map.get(key);
			current.saveToFile(filePath);
		}
		map.clear();
	}
	
	public void saveFileNameMap(PrintWriter writer) {
		for(String name : fileNameMap.keySet()) {
			writer.println(name);
			writer.print(fileNameMap.get(name));
		}
	}
	
	private void initializeFileNameMap(Scanner scanner) throws FileNotFoundException {
		// TODO
	}
	
	
	
	public Location get(String name) throws FileNotFoundException {
		if(map.containsKey(name)) {
			return map.get(name);
		}
		return addNewLocation(name);
	}
	
	public Location addNewLocation(String name) throws FileNotFoundException {
		String filepath = fileNameMap.get(name);
		Location newLocation = loadLocationAlpha0_1(filepath);
		map.put(name, newLocation);
		return newLocation;
	}
	
	private Location loadLocationAlpha0_1(String filepath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filepath));
		String versionString;
		
		
		scanner.close();
		return null;
	}
	
	
	
}
