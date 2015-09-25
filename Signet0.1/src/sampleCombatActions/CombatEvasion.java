package sampleCombatActions;

import items.Weapon;
import misc.CreatureAction;
import misc.DamageType;
import combat.AttackAction;
import combat.CombatAction;
import combat.DefenseAction;
import creatures.Creature;
import misc.DeathException;

public class CombatEvasion extends CombatAction {

	public CombatEvasion(Creature combatant) {
		super(combatant, new EvasionAttack(combatant), new EvasionDefense(combatant));
		name = "Evade";
	}
}

class EvasionDefense extends DefenseAction {

	private static int DEFENSE = 4;
	private static int ARMOR = 0;
	
	public EvasionDefense(Creature c) {
		super(c, DEFENSE, ARMOR);
	}
}
class EvasionAttack extends AttackAction implements CreatureAction {

	public EvasionAttack(Creature attacker) {
		super(attacker, null, null, 0, 0, 0, null);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean takeAction() {
		// this action does nothing becaues Evasion stops the user from attacking.
		return false;
	}
	
}