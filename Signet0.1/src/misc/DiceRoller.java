package misc;

import java.util.Random;

public class DiceRoller {
	
	public static Random rand = new Random();

	public static int getRandInt(int i) {
		return rand.nextInt(i);
	}
	
	// returns int[]{hits, zeroes}
	public static int[] makeRoll(int dicePool){
		int hits = 0;
		int zeroes = 0;
		for (int i = 0; i< dicePool; i++){
			int roll = rand.nextInt(6);
			if (roll == 0){
				zeroes += 1; // rolls of 0
			}
			else if (roll == 4 || roll == 5){
				hits += 1; // rolls of 5 or 6
			}
		}
		return new int[]{hits, zeroes};
	}
	
	public static boolean detectFumble(int dicePool, int zeroes){
		return (zeroes >= (dicePool / 2));
	}
	
}
