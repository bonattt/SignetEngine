package sampleCombatActions;

import misc.CreatureAction;
import combat.DefenseAction;
import creatures.Creature;

public class DefaultDefense extends DefenseAction{

	public DefaultDefense(Creature c) {
		super(c, 0, 0);
	}
}
