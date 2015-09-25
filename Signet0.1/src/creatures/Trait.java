package creatures;

import java.util.HashMap;

public abstract class Trait {
	
	int duration;
	boolean permanent;
	// all activation flags default to false. These flags signal to the creature class which trait groups to add the trait to.
	// in other words, these pointers determine what situations the trait is called in.
	public boolean
	combatEffect,
	statCalulationEffect,
	painCalculationEffect,
	infectionEffect;
	/**
	 * constructs a temporary trait 
	 * @param durationRemaining
	 */
	public Trait(int durationRemaining){
		permanent = false;
		// sets all flags to false.
		combatEffect = false;
		statCalulationEffect = false;
		painCalculationEffect = false;
		infectionEffect = false;
	}
	/**
	 * constructs a permanent trait, and no activation flags.
	 */
	public Trait(){
		this(Integer.MIN_VALUE);
		permanent = true;
	}
	/**
	 * removes the trait from all of the approprate list in the given creature.
	 * @param c
	 */
	public void removeFrom(Creature c){
		// TODO implement this
	}
	/**
	 * by default, this method just runs the duration on a non-permenenat trait, and returns a false if the duration has expired,
	 * and also removes the trait from the given character.
	 * @param timePassed
	 * @return
	 */
	public boolean passTime(int timePassed, Creature c){
		if (permanent){
			return true;
		}
		duration -= timePassed;
		if (duration <= 0){
			removeFrom(c);
			return false;
		}
		return true;
	}
	/*
	 * below here are default non-effect methods that can be called in ANY situation, and will have no effect.
	 * These methods are defined to avoid needing to implement a neutral method in EVERY extention of Trait
	 * these methods must be overriden in a subclass to add an effect to the trait in a specific situation.
	 */
	
	public int infectionChanceEffect(int infectionChance){
		//should return a new infection chance. (or the same one)
		return infectionChance;
	}
	public void statCalculationEffect(HashMap<String, Integer> base_stats, HashMap<String, Integer> modified_stats){
		// do nothing.
	}
	
}