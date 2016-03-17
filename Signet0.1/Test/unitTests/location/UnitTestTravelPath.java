package unitTests.location;

import static org.junit.Assert.*;
import location.TravelPath;

import org.junit.Test;

public class UnitTestTravelPath {

	@Test
	public void equalsTrue() {
		TravelPath path1 = new TravelPath("_name_", "_location_", 10, 1.2);
		TravelPath path2 = new TravelPath("_name_", "_location_", 10, 1.2);
		assertEquals(path1, path2);
	}
	@Test
	public void notEqualsName() {
		TravelPath path1 = new TravelPath("different", "_location_", 10, 1.2);
		TravelPath path2 = new TravelPath("_name_", "_location_", 10, 1.2);
		assertNotEquals(path1, path2);
	}
	@Test
	public void notEqualsLocation() {
		TravelPath path1 = new TravelPath("_name_", "_location_", 10, 1.2);
		TravelPath path2 = new TravelPath("_name_", "different", 10, 1.2);
		assertNotEquals(path1, path2);
	}
	@Test
	public void notEqualsTime() {
		TravelPath path1 = new TravelPath("_name_", "_location_", 100, 1.2);
		TravelPath path2 = new TravelPath("_name_", "_location_", 1, 1.2);
		assertNotEquals(path1, path2);
	}
	@Test
	public void notEqualsExhaustion() {
		TravelPath path1 = new TravelPath("_name_", "_location_", 10, 100);
		TravelPath path2 = new TravelPath("_name_", "_location_", 10, .5);
		assertNotEquals(path1, path2);
	}


}
