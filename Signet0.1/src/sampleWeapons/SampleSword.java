package sampleWeapons;

import java.util.ArrayList;
import java.util.HashMap;

import items.CombatItem;
import items.Weapon;

public class SampleSword extends Weapon implements CombatItem {
	
	private static final int MIGHT = 2;
	private static final int ACCURACY = 1;
	private static final int PARRY = 1;

	public SampleSword() {
		super(MIGHT, ACCURACY, PARRY, Weapon.ONE_HANDED);
		super.name = "longsword";
		HashMap<String, Integer> utility = new HashMap<String, Integer>();
		utility.put("cutting", 0);
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

	public boolean needsToBeHeld() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		return name;
	}

}
