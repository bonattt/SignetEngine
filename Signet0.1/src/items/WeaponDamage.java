package items;

import misc.DeathException;
import misc.DiceRoller;
import creatures.Creature;

public class WeaponDamage {
	
	protected int might, accuracy, damageType;
	protected String weaponSkill;
	
	public WeaponDamage(int might, int accuracy, int damageType, String weaponSkill) {
		this.might = might;
		this.accuracy = accuracy;
		this.damageType = damageType;
		this.weaponSkill = weaponSkill;
	}

	public void dealDamage(Creature attacker, Creature target, int netHits)
			throws DeathException {
		if (netHits <= 0) {
			return;
		}
		int damage = DiceRoller.makeRoll(netHits)[0];
		target.recieveWound(damage, damageType, netHits);
	}
	
	
}
