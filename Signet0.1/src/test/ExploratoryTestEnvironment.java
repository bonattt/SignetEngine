package test;

import misc.DeathException;
import mothers.CharacterMother;
import environment.Environment;

public class ExploratoryTestEnvironment {

	public static void main(String[] arg){
//		exploreLocationIndex1();
		testRun();
	}
	
	@SuppressWarnings("unused")
	private static void exploreLocationIndex1(){
		// TODO Environment.makeLocationIndex();
		
		System.out.println("\nindex object: " + Environment.locationIndex);
		System.out.println("index size: " + Environment.locationIndex.size());
		System.out.println("index key set: " + Environment.locationIndex.keySet());
	}
	
//	@SuppressWarnings("unused")
	private static void testRun(){
		Environment ev = new Environment("src/testLocations/sampleStartLocation");
		ev.setPlayer(CharacterMother.getDickDefenderOfLife());
		try {
			ev.choseAction();
		} catch (DeathException e) {
			System.out.println("Player died during the test.");
		}
		Environment.scanner.close();
	}
	
}
