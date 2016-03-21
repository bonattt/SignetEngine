package main;

import inventory.InventoryException;

import java.io.FileNotFoundException;
import java.util.Scanner;

import misc.DeathException;
import misc.GameLoadException;
import testingMothers.CharacterMother;
import creatures.PlayerCharacter;
import environment.Environment;

public class GameRunner {
	
	private static Scanner inputScanner;
	public static int BACK_DISABLED = 0;
	public static int BACK_ENABLED = 1;

	public static void main(String[] args) throws InventoryException, DeathException {
		inputScanner =  new Scanner(System.in);
		try {
			Environment env = new Environment("src/saveFiles");
			PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
			env.startGame(player, "location1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GameLoadException e) {
			e.printStackTrace();
		}
		inputScanner.close();
	}
}
