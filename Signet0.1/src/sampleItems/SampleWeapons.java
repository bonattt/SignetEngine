package sampleItems;

import items.RangedWeapon;
import items.MeleeWeapon;

public class SampleWeapons {

	public static RangedWeapon getSampleAssaultRifle() {
		RangedWeapon weapon = new RangedWeapon("sample assault rifle", "this is a typical assault rifle",
				100, 100, 100, 10, 0, 3, 3, 0, 2, 32);
		return weapon;
	}
	
	public static MeleeWeapon getSampleCombatKnife() {
		MeleeWeapon weapon = new MeleeWeapon(1, 1, 1, 0, "sample combat knife", "this is a combat knife");
		return weapon;
	}
	
	public static RangedWeapon getSamplePistol() {
		RangedWeapon weapon = new RangedWeapon("sample pistol", "this is the pistol",
				25, 25, 50, 10, 0, 1, 1, 0, 1, 10);
		return weapon;
	}
	
	public static MeleeWeapon getSampleSword() {
		MeleeWeapon weapon = new MeleeWeapon(2, 1, 1, MeleeWeapon.ONE_HANDED, "sample sword", "this is a longsword");
		return weapon;
	}
	
}
