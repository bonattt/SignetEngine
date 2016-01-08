package unitTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import location.Location;
import location.LocationIndex;
import location.TravelPath;
import misc.GameEvent;
import misc.GameLoadException;

import org.junit.Before;
import org.junit.Test;

public class UnitTestLocationIndex {

	private static final String filePath = "src/unitTests/testingData/";
	private static final String LOCAL1 = "location1";
	private static final String LOCAL2 = "location2";
	private static final String LOCAL1_FILE = "location1.signet";
	private static final String LOCAL2_FILE = "location2.signet";
	
	private LocationIndex index;
	private Location local1, local2;
	
	
	@Before
	public void setup() throws FileNotFoundException {
		local1 = new Location(LOCAL1, LOCAL1_FILE, "this is a test location",
				new ArrayList<TravelPath>(), new ArrayList<GameEvent>());
		local2 = new Location(LOCAL2, LOCAL2_FILE, "this is a test location",
				new ArrayList<TravelPath>(), new ArrayList<GameEvent>());
		local1.saveToFile(filePath);
		local2.saveToFile(filePath);
		index = new LocationIndex(filePath);
	}
	
	@Test
	public void getGetsLocal1() throws FileNotFoundException, GameLoadException {
		assertEquals(local1, index.get(LOCAL1));
	}
	@Test
	public void getGetsLocal2() throws FileNotFoundException, GameLoadException {
		assertEquals(local2, index.get(LOCAL2));
	}
	@Test
	public void saveFiles() throws FileNotFoundException, GameLoadException {
		Location local = index.get(LOCAL1);
		local.desc = "anpdniakw";
		index.saveLocations(filePath);
		LocationIndex newIndex = new LocationIndex(filePath);
		assertEquals(local, newIndex.get(LOCAL1));
	}
	
}
