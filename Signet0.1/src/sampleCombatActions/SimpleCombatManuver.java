package sampleCombatActions;

import combat.AttackAction;
import combat.CombatAction;
import combat.DefenseAction;
import creatures.Creature;

public class SimpleCombatManuver extends CombatAction {

	public SimpleCombatManuver(Creature combatant) {
		super(combatant, new SimpleAttack(combatant, null, combatant), new DefaultDefense(combatant));
		// TODO Auto-generated constructor stub
	}
	
}
