package main;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import misc.TextTools;

public class GameRunner {
	
	private static Scanner inputScanner;
	public static int BACK_DISABLED = 0;
	public static int BACK_ENABLED = 1;
	private static boolean scannerValid = false;

	public static void main(String[] args){
		inputScanner =  new Scanner(System.in);
		scannerValid = true;
		TextTools.display("class not implemented yet");

		
		
		
		inputScanner.close();
	}
	
	
	public static int questionAsker(String question, String[] answers){
		return TextTools.questionAsker(question, answers, BACK_DISABLED);
	}
//	public static int questionAsker(String question, String[] answers, int backEnabled){
//		
//		
//
//		int choice = -1;
//		System.out.println("question");
//		System.out.println();
//		while (true) {
//			for (int i = 0; i < (answers.length-backEnabled); i++){
//				System.out.println("[" + (i+1) + "] " + answers[i]);
//			}
//			if (backEnabled == 1){
//				System.out.println("[0] " + answers[answers.length-1]);
//			}
//			int player_choice = verified_input(answers, backEnabled);
//			if (player_choice != Integer.MIN_VALUE){
//				if (!scannerValid){
//					inputScanner.close();	
//				}
//				return player_choice;
//			}
//			System.out.println("invalid choice");
//		}
//	}
	/*
	 * this private helper method calls for player input to select an option. This method flags a non-int input with Integer.MIN_VALUE
	 * and also choices out of range of the possible answers provided for the player.
	 */
	private static int verified_input(String[] answers, int backEnabled){
		int choice;
		try {
			String next = inputScanner.nextLine();
			inputScanner.close();
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
}
