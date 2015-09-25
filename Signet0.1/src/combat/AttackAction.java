package combat;

import items.Weapon;
import misc.CreatureAction;
import creatures.Creature;
import misc.DeathException;
import misc.DiceRoller;
import misc.DamageType;

public class AttackAction {
		
	public DamageType type;
	private Creature target;
	private Creature attacker;
	private Weapon weapon;
	private int baseMight, baseHit, attacksMade, advantage, advantageAdded, successfulHits;
	private int[][] attackResults;
	// {netHits, might}
	boolean attackHit;
	
	public AttackAction(Creature attacker, Weapon weaponUsed, Creature target, int might, int hit, int numberOfAttacks, DamageType dt){
		this.target = target;
		this.attacker = attacker;
		weapon = weaponUsed;
		baseMight = might + weaponUsed.getMight();
		baseHit = hit + weaponUsed.getAccuracy();
		attacksMade = 0;
		advantage = 0; 
		advantageAdded = 0;
		attackHit = false;
		attackResults = new int[numberOfAttacks][2];
		successfulHits = 0;
	}
	public void setTarget(Creature c){
		target = c;
	}
	
	public void setNewWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public boolean takeAction() throws DeathException{
		for (int i = 0; i < attackResults.length; i++){ //attackResults.length = number of attacks to be made.
			int attackDice = baseHit + attacker.stats_adjusted.get("agl");
			attackDice -= attackResults.length; // null pointer exception
			attackDice -= ((attacksMade++));
			
			int defenseDice = target.getDefenseDice(this);
			int[] attackTest = DiceRoller.makeOpposedTest(attackDice, defenseDice);
			int netHits = attackTest[0];
			int might = baseMight + attacker.getWeaponMight();

			attackResults[i][0] = netHits;
			
			if (netHits == 0 && might >= 1){
				attackResults[i][1] = 1;
				successfulHits++;
			} else {
				attackResults[i][1] = might;
				successfulHits++;
			}
		}
		try {
			target.recieveWound(attackResults, attacker.getWeaponType());
		} catch (DeathException e) {
			e.finishingBlow = this;
			throw e;
		}
		return true;
	}
	
	public int getAdvantage(){
		return advantageAdded;
	}
	
	public int getSuccessfulHits(){
		return successfulHits;
	}
}
