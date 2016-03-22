package app;

import inventory.InventoryException;

import java.io.FileNotFoundException;

import creatures.PlayerCharacter;
import testingMothers.CharacterMother;
import misc.DeathException;
import misc.GameLoadException;
import environment.Environment;

public class GameRunnerTest {

	public static void main(String[] args) throws InventoryException, DeathException {
		try {
			Environment env = new Environment("src/saveFiles");
			PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
			env.startGame(player, "location1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GameLoadException e) {
			e.printStackTrace();
		}
	}
}
