package sampleWeapons;

import items.RangedWeapon;
import items.Weapon;

public class SamplePistol extends RangedWeapon {

	public static final int MIGHT = 1;
	public static final int ACCURACY = 1;
	public static final int PARRY = 0;
	public static final int WEAPON_TYPE = 1;
	public static final int AMMO_CAPACITY = 10;
	
	
	public SamplePistol() {
		super(MIGHT, ACCURACY, PARRY, WEAPON_TYPE, AMMO_CAPACITY);
		name = "sample pistol";
	}

	public String getName() {
		return name;
	}

	public boolean needsToBeHeld() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isExpendible() {
		// TODO Auto-generated method stub
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
