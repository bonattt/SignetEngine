package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextTools {

	public static final int BACK_DISABLED = 0;
	public static final int BACK_ENABLED = 1;
	
//	public static int questionAsker(String question, String[] answers){
//		return questionAsker(question, answers, BACK_DISABLED);
//	}
	public static int questionAsker(String question, String[] answers, int backEnabled){
		int choice = -1;
		display(question);
		display("");
		while (true) {
			for (int i = 0; i < (answers.length-backEnabled); i++){
				display("[" + (i+1) + "] " + answers[i]);
			}
			if (backEnabled == 1){
				display("[0] " + answers[answers.length-1]);
			}
			int player_choice = verified_input(answers, backEnabled);
			if (player_choice != Integer.MIN_VALUE){
				return player_choice;
			}
			display("invalid choice");
		}
	}
	/*
	 * this private helper method calls for player input to select an option. This method flags a non-int input with Integer.MIN_VALUE
	 * and also choices out of range of the possible answers provided for the player.
	 */
	private static int verified_input(String[] answers, int backEnabled){
		int choice;
		try {
			Scanner scan = new Scanner(System.in);
			String next = scan.nextLine();
			choice = Integer.parseInt(next);
		} catch (InputMismatchException e){
			// if input is not translatable into an int, than set it to minvalue int to flag invalid input.
			choice = Integer.MIN_VALUE;
		} catch (NumberFormatException e){
			choice = Integer.MIN_VALUE;
		}
		if (choice < (answers.length+1 - backEnabled)){
			return choice;
		}
		return Integer.MIN_VALUE;
	}
	/**
	 * This method simply prints the given string. In future versions of this system, display will use a more advanced graphics system.
	 * @param str
	 */
	public static void display(String str){
		System.out.println(str);
	}
}
