package combat;

import items.Weapon;
import misc.CreatureAction;
import creatures.Creature;

public class CombatAction implements CreatureAction {

	public AttackAction attack;
	public DefenseAction defense;
	private Creature combatant;
	public String name;
	
	public CombatAction(Creature combatant, AttackAction atc, DefenseAction def){
		this.combatant = combatant;
		attack = atc;
		defense = def;
	}
	public void setNewWeapon(Weapon newWeapon){
		// TODO 
		System.out.println("setNewWeapon method has not yet been defined.");
	}
//	public void setNewTarget(Creature target){
//		// TODO 
//		System.out.println("setNewTarget method has not yet been defined.");
//	}
	
	@Override
	public boolean takeAction() {
		combatant.setAttack(attack);
		if (defense != null){
			combatant.setDefense(defense);
		}
		return true;
	}
	
	/**
	 * this method is overriden in custom combat actions. It allows some actions to have a requirements be filled for an attack to be availible.
	 * this method can take the argument "null" in place of a combat object, this signifies that there is no combat going on.
	 * This should be used as a signal to enable surprise attacks, and such.
	 * @return
	 */
	public boolean canBeUsed(){
		return true;
	}
}
