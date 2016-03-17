package unitTests.location;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import location.Location;
import location.TravelPath;
import misc.GameEvent;

import org.junit.Before;
import org.junit.Test;

import testingMothers.SampleLocation;

public class UnitTestLocation {

	Location local1, local2;
	
	@Before
	public void setup() {
		local1 = SampleLocation.getEmptyPlaza();
		local2 = SampleLocation.getEmptyPlaza();
	}
	
	@Test
	public void equalsTrue() {
		assertEquals(local1, local2);
	}
	
	@Test
	public void notEqualsDifferentName()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = Location.class.getDeclaredField("name");
		f.setAccessible(true);
		f.set(local2, "different");
		assertNotEquals(local1, local2);
	}
	
	@Test
	public void notEqualsDifferentDesc()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = Location.class.getDeclaredField("desc");
		f.setAccessible(true);
		f.set(local2, "different");
		assertNotEquals(local1, local2);
	}
	
	@Test
	public void notEqualsDifferentPaths()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = Location.class.getDeclaredField("travelableLocations");
		f.setAccessible(true);
		f.set(local2, new ArrayList<TravelPath>());
		assertNotEquals(local1, local2);
	}
	@Test
	public void notEqualsDifferentEvents()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = Location.class.getDeclaredField("explorableFeatures");
		f.setAccessible(true);
		f.set(local2, new ArrayList<GameEvent>());
		assertNotEquals(local1, local2);
	}


}
