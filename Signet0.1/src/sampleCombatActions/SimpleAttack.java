package sampleCombatActions;

import items.Weapon;
import misc.DamageType;
import combat.AttackAction;
import creatures.Creature;

public class SimpleAttack extends AttackAction {

	public SimpleAttack(Creature attacker, Weapon weaponUsed, Creature target) {
		super(attacker, weaponUsed, target, 4, 1, 1, DamageType.blunt);
	}

}
