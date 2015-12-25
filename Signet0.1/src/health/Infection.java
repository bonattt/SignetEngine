package health;

import java.io.PrintWriter;

import misc.DiceRoller;
/**
 * Infection
 * @author bonattt
 *
 * Infections apply to wounds, causing them to be more dangerous and heal slower. Infections are randomly aquired, but usually come about
 * from not treating wounds correctly, and are always worse on untreated wounds.
 * Infections create multipliers for the negative trait of wounds, slow their healing, or even cause wounds to fester and become worse.
 * 
 * Infections require an opposed endurance test from the player every day (default timing) to resist the infection becoming worse.
 * Every test made against infection results in a reduction in the infection's dice pool as the player's immune response builds.
 * (this effect can be different in different infections. Some very dangerous infections might remove this advantage all together, meaning
 *  the player will likely require medical attention to beat the infection.)
 * 
 * A success by 2 or more net hits results in an increase or decrease in the severity of the infection. A success or loss by only 1 hit or a
 * tie means the infection remeans at the same severity for a day.
 *
 */
public class Infection {
	
	private static int DEFAULT_TIME_INCREMENT = 24000; // 24 hours
	
	private double
	healingAdjustment,
	bleedingAdjustment,
	stunAdjustment,
	fatigueAdjustment;
	
	private int
	severity,
	previousSeverity,
	dicePool,
	timeIncrement,
	timePassed;
	
	private int severityCap = Integer.MAX_VALUE; // by default, no severity cap. Severity cap must be set in subclass constructor.

	public Infection(int strength){
		this(strength, 1, 1, 1, 1);
	}
	public Infection(int strength, double healing, double bleeding, double stun, double fatigue){
		healingAdjustment = healing;
		bleedingAdjustment = bleeding;
		stunAdjustment = stun;
		fatigueAdjustment = fatigue;
		severity = 0;
		previousSeverity = Integer.MIN_VALUE;
		dicePool = strength;
		timeIncrement = DEFAULT_TIME_INCREMENT;
		timePassed = 0;
	}
	
	public void saveToFile(PrintWriter writer){
		String str = "METHOD 'saveToFile' UNIMPLIMENTED IN CLASS 'infection'";
		System.out.println(str);
		writer.println(str);
	}
	
	public double getHealingAdjustment(){
		if (healingAdjustment == Integer.MIN_VALUE){
			return 1;
		}
		return healingAdjustment / (Math.sqrt(Math.sqrt(severity)));
	}
	public double getBleedingAdjustment(){
		if (bleedingAdjustment == Integer.MIN_VALUE){
			return 1;
		}
		return bleedingAdjustment / (Math.sqrt(Math.sqrt(severity)));
	}
	public double getStunAdjustment(){
		if (fatigueAdjustment == Integer.MIN_VALUE){
			return 1;
		}
		return stunAdjustment / (Math.sqrt(Math.sqrt(severity)));
	}
	public double getFatigueAdjustment(){
		if (fatigueAdjustment == Integer.MIN_VALUE){
			return 1;
		}
		return fatigueAdjustment / (Math.sqrt(Math.sqrt(severity)));
	}
	public void setSeverityCap(int cap){
		severityCap = cap;
	}
	/**
	 * returns a completely random Infection for the given wound.
	 * TODO this method currently only returns a sample infection.
	 * @param part
	 * @return
	 */
	public static Infection getRandomInfection(Wound wound){
		//TODO implement randomness instead of just using a sample infection.
		return new Infection(10);
	}
	
	/**
	 * this method passes time on an infection. This means, the infection gets worse or better
	 * @param timePassed
	 * @param healingFactor
	 * @param wound
	 * @return
	 */
	public double[] passTime(int timePassed, double healingFactor, Wound wound) {
		double physicalDamage = 0;
		double stunDamage = 0;
		double fatigueDamage = 0;
		double woundDamage = 0; // by default, wound is not damamged.
		healingFactor *= healingAdjustment;
		timePassed *= healingFactor;
		while (timePassed > (timeIncrement - this.timePassed)){
			timePassed -= (timeIncrement - this.timePassed);
			this.timePassed = 0;
			int netHits = resistInfection(healingFactor);
			if (netHits > 1){
				previousSeverity = severity;
				severity -= 1;
			} else if (netHits < -1){
				previousSeverity = severity;
				severity += 1;
			} else {
				if (previousSeverity != Integer.MIN_VALUE){
					previousSeverity = severity;
				}
			}
			
			// immune build
			immuneResponse();
		}
		return new double[]{physicalDamage, stunDamage, fatigueDamage};
	}
	/**
	 * reduces the dice pool of the infection.
	 */
	private void immuneResponse(){
		if (dicePool > 0){
				dicePool -= 1;
			}
	}
	
	/**
	 * makes the opposed test for the player resisting the infection.
	 * derives the player's dice pool from the player's healing factor.
	 * @param healingFactor
	 * @return
	 */
	private int resistInfection(double healingFactor){
		int estimatedEndurance = (int) Math.ceil((healingFactor - 0.4) * 10);
		return (DiceRoller.makeRoll(estimatedEndurance)[0]) - (DiceRoller.makeRoll(dicePool)[0]);
	}
	
	/**
	 * returns a boolean of whether the infection should be removed.
	 * @return
	 */
	public boolean isRecovered(Wound w){
		// if previousSeverity is MIN_VALUE than the infection is still incubating.
		if (previousSeverity == Integer.MIN_VALUE){
			return false;
		}
		boolean isRecovered = severity<=0;
		if(isRecovered){
			printFullRecovery(w);
		}
		return isRecovered;
	}
	private void printFullRecovery(Wound w){
		//TODO make this better.
		System.out.println("your infection seems to have cleared up.");
	}
	private void printRecovering(Wound w){
		//TODO make this better.
		System.out.print("your infection seems to have gotten better.");
		if (severity > 6){
			
		}
	}
	private void printGettingWorse(Wound w){
		//TODE make this better.
		System.out.println("your infection seems to have gotten worse.");
	}
}
