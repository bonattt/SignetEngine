package location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import misc.GameLoadException;

public class LocationIndex {

	private static final String INDEX_FILE = "index.signet";
	
	private String filePath;
	private HashMap<String, Location> map;
	private HashMap<String, String> fileNameMap;
	
	public LocationIndex(String filePath) throws FileNotFoundException {
		map = new HashMap<String, Location>();
		fileNameMap = new HashMap<String, String>();
		this.filePath = filePath;
		initializeFileNameMap();
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
	
	private void initializeFileNameMap() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath + INDEX_FILE));
		String name = scanner.nextLine();
		while(! name.equals("end")) {
			String fileName = scanner.nextLine();
			fileNameMap.put(name, fileName);
			name = scanner.nextLine();
		}
		scanner.close();
	}
	public Location get(String name) throws FileNotFoundException, GameLoadException {
		if(map.containsKey(name)) {
			return map.get(name);
		}
		return addNewLocation(name);
	}
	
	public Location addNewLocation(String name) throws FileNotFoundException, GameLoadException {
		String fileName = fileNameMap.get(name);
		Location newLocation = Location.loadLocation(filePath, fileName);
		map.put(name, newLocation);
		return newLocation;
	}
}
