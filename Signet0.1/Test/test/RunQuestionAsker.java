package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import environment.Environment;
import misc.GameLoadException;
import misc.TextTools;

public class RunQuestionAsker {
	
	public static void main(String[] args) throws FileNotFoundException, GameLoadException{
//		Environment ev = new Environment("WHATEVER", "src/testLocations/sampleStartLocation");
		System.out.println("\n");
		String input;
		while(true){
			System.out.println("would you like a back option? [y/n] or q to quit.");
			input = TextTools.input.nextLine();
			if (input.startsWith("y")){
				yesBackOption();
			} else if (input.startsWith("n")) {
				noBackOption();
			} else if (input.startsWith("q")){
				System.out.println("exiting test runner! Goodbye!");
				break;
			} else {
				System.out.print("invalid input: ");
				System.out.print("'" + input +"'");
			}
			System.out.println();
		}	
	}
	
	private static void noBackOption(){
		String question = "What option would you like? (no back option)";
		String[] answers = {"one", "two", "three", "four"};
		int backEnabled = 0;
		int choice = TextTools.questionAsker(question, answers, backEnabled);
		System.out.println();
		System.out.println("You selected " + answers[choice-1]);
	}
	
	private static void yesBackOption(){
		String question = "What option would you like? (back option availible)";
		String[] answers = {"one", "two", "three", "four", "back"};
		int backEnabled = 1;
		int choice = TextTools.questionAsker(question, answers, backEnabled);
		System.out.println();
		String selection;
		if(choice == 0) {
			selection = answers[answers.length-1];
		} else {
			selection = answers[choice-1];
		}
		System.out.println("You selected " + selection);
	}
}
