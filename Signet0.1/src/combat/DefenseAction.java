package combat;

import misc.CreatureAction;
import creatures.Creature;

public class DefenseAction {

	private int defense, armor, timesAttacked, advantage;
	Creature defender;
	
	public DefenseAction(Creature c, int defense, int armor){
		defender = c;
		this.defense = defense;
		this.armor = armor;
		timesAttacked = 0;
		advantage = 0;
	}
	/**
	 * returns the number of defense dice rolled for this action.
	 * @return
	 */
	public int defenseDice(){
		return defense + defender.stats_adjusted.get("agility") - (timesAttacked++);
	}
	public int getAdvantage(){
		return advantage;
	}
	/**
	 * this method returns a number of gained or lost defense dice based on the attack action used.
	 * Since the default defense is not weak or strong against any specific types of attacks, this
	 * method defaults as "return 0" but can be overriden.
	 * @param atc
	 * @return
	 */
	public int adjustmentForAttack(AttackAction atc){
		return 0;
	}
	
}
