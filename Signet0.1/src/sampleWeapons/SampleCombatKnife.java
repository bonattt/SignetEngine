package sampleWeapons;

import items.Weapon;

public class SampleCombatKnife extends Weapon {

	public static final int MIGHT = 1;
	public static final int ACCURACY = 1;
	public static final int PARRY = 1;
	public static final int WEAPON_TYPE = 0;
	
	public SampleCombatKnife() {
		super(MIGHT, ACCURACY, PARRY, WEAPON_TYPE);
		name = "sample combat knife";
	}

	public String getName() {
		return name;
	}

	public boolean needsToBeHeld() {
		return true;
	}

	public boolean isExpendible() {
		return false;
	}

	public Weapon asWeapon() {
		return this;
	}


	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
		
	}

}
