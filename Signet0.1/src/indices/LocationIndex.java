package indices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import environment.Environment;
import environment.Location;

public class LocationIndex extends HashMap<String,Location>{

	public LocationIndex(String indexString){
		boolean unsuccessfulLoad = makeLocationIndex(indexString);
		if(unsuccessfulLoad){
			if(Environment.getInstance().print_debugs){
				System.out.println("Failed to load location index.");
			}
		}
	}
		
	public boolean makeLocationIndex(String indexFilePath){
	    try {
		    BufferedReader reader = new BufferedReader( new FileReader (indexFilePath));
		    String locationFilePath;

			while( ( locationFilePath = reader.readLine() ) != null ) {
				if(Environment.getInstance().print_debugs){
					System.out.println("adding '"+locationFilePath+"' to the location hash index.");
				}
			    this.put(locationFilePath, new Location(locationFilePath));
			}
			reader.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
