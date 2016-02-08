package app;

import inventory.InventoryException;

import java.io.FileNotFoundException;

import creatures.PlayerCharacter;
import testingMothers.CharacterMother;
import misc.DeathException;
import misc.GameLoadException;
import environment.Environment;

public class GameRunnerTest {
	
	public static void main(String[] args)
			throws FileNotFoundException, GameLoadException, DeathException, InventoryException {
		Environment envir = new Environment("src/unitTests/testingData/");
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		envir.startGame(player, "location1");
	}
	
}
