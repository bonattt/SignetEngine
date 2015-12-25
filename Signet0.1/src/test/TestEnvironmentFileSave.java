package test;

import testingMothers.CharacterMother;
import environment.Environment;

public class TestEnvironmentFileSave {

	public static void main(String[] args){
		Environment ev = new Environment("src/testLocations/sampleStartLocation");
		ev.setPlayer(CharacterMother.getDickDefenderOfLife());
		ev.saveGameToFile("src/saveFiles/testSaveFile");
	}
}
