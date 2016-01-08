package testingMothers;

import java.util.ArrayList;
import java.util.List;

import location.Location;
import location.TravelPath;
import misc.GameEvent;

public class SampleLocation {
	
	public static Location getEmptyPlaza() {
		List<TravelPath> paths = new ArrayList<TravelPath>();
		paths.add(new TravelPath("west", "W Market St", 10, .5));
		paths.add(new TravelPath("north", "ruined street", 10, 1));
		
		List<GameEvent> events = new ArrayList<GameEvent>();
		events.add(new LootGenericChest());
		
		return new Location("empty plaza", "sampleLocationFile.signet",
				"this plaza is empty except for a chest, sitting suspiciously in the middle.",
				paths, events);
		
	}
	
}
