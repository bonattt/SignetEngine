package items;

import combat.AttackAction;

import creatures.Creature;

public class Ammo extends Item{

	private int unitValue; // how many shots a single unit of this ammo is worth.
	
	public Ammo(int unitValue) {
		super(0, 0, 10, 5, 0);
		this.unitValue = unitValue;
	}
	public Ammo() {
		super(0, 0, 10, 5, 0);
		unitValue = 0;
	}


	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}
	
	public void modifyAttackDamage(AttackAction atc){
		// TODO nothing
	}
	
	public void specialEffect(Creature target){
		// TODO do nothing by default.
	}

}
