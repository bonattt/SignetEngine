package misc;

import java.util.Random;

import main.GameRunner;

public class DiceRoller {
	
	public static Random rand = new Random();

	public static int[] makeRoll(int dicePool){
		int[] results = new int[]{0, 0};
		
		for (int i = 0; i< dicePool; i++){
			int roll = rand.nextInt(6);
			if (roll == 0){
				results[1] += 1;
			}
			else if (roll == 4 || roll == 5){
				results[0] += 1;
			}
		}
		return results;
	}
	
	public static boolean detectFumble(int dicePool, int zeroes){
		return (zeroes >= (dicePool / 2));
	}
	
}
