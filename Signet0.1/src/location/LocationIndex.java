package location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class LocationIndex {

	private String filePath;
	private HashMap<String, Location> map;
	private HashMap<String, String> fileNameMap;
	
	public LocationIndex(String filePath) throws FileNotFoundException {
		this.filePath = filePath;
		map = new HashMap<String, Location>();
		fileNameMap = new HashMap<String, String>();
	}
	
	public void save() throws FileNotFoundException {
		saveLocations(filePath);
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
			writer.println(fileNameMap.get(name));
		}
		writer.println("end");
	}
	
	public void initializeFileNameMap(Scanner scanner) throws FileNotFoundException {
		String name = scanner.nextLine();
		while(! name.equals("end")) {
			String fileName = scanner.nextLine();
			fileNameMap.put(name, fileName);
			name = scanner.nextLine();
		}
	}
	public Location get(String name) throws FileNotFoundException {
		if(map.containsKey(name)) {
			return map.get(name);
		}
		return addNewLocation(name);
	}
	
	public Location addNewLocation(String name) throws FileNotFoundException {
		String fileName = fileNameMap.get(name);
		Location newLocation = loadLocationAlpha0_1(fileName);
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
