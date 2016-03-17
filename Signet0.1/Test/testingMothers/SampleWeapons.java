package testingMothers;

import health.DamageType;
import items.RangedWeapon;
import items.MeleeWeapon;

public class SampleWeapons {
	
	public static final String ASLTRFL_NAME = "sample assault rifle";
	public static final String ASLTRFL_DESC = "this is a typical assault rifle";
	public static final int ASLTRFL_SIZE = 100;
	public static final int ASLTRFL_WT = 100;
	public static final int ASLTRFL_DUR = 100;
	public static final int ASLTRFL_HARD = 10;	
	public static final int ASLTRFL_DAM = 0;
	public static final int ASLTRFL_MT = 6;
	public static final int ASLTRFL_ACC = 3;
	public static final int ASLTRFL_RNG = 0;
	public static final int ASLTRFL_WEPTYP = RangedWeapon.TWO_HANDED;
	public static final int ASLTRFL_DAMTYP = DamageType.PIERCING;
	public static final int ASLTRFL_AMMO = 32;
	public static final int ASLTRFL_AMREM = 32;
	
	public static RangedWeapon getSampleAssaultRifle() {
		RangedWeapon weapon = new RangedWeapon(ASLTRFL_NAME, ASLTRFL_DESC,
				ASLTRFL_SIZE, ASLTRFL_WT, ASLTRFL_DUR, ASLTRFL_HARD, ASLTRFL_DAM,
				ASLTRFL_MT, ASLTRFL_ACC,
				ASLTRFL_RNG, ASLTRFL_WEPTYP, ASLTRFL_DAMTYP, ASLTRFL_AMMO, ASLTRFL_AMREM);
		return weapon;
	}
	
	public static final String KNIFE_NAME = "sample combat knife";
	public static final String KNIFE_DESC = "it is pointy";
	public static final int KNIFE_SIZE = 5;
	public static final int KNIFE_WT = 5;
	public static final int KNIFE_DUR = 100;
	public static final int KNIFE_HARD = 10;	
	public static final int KNIFE_DAM = 0;
	public static final int KNIFE_MT = 3;
	public static final int KNIFE_ACC = 3;
	public static final int KNIFE_WEPTYP = RangedWeapon.TWO_HANDED;
	public static final int KNIFE_DAMTYP = DamageType.PIERCING;
	public static final int KNIFE_PARRY = 0;	
	
	public static MeleeWeapon getSampleCombatKnife() {
		MeleeWeapon weapon = new MeleeWeapon(KNIFE_SIZE, KNIFE_WT, KNIFE_DUR, KNIFE_HARD, KNIFE_DAM,
				KNIFE_NAME, KNIFE_DESC,
				KNIFE_WEPTYP, KNIFE_PARRY, KNIFE_ACC, KNIFE_MT, KNIFE_DAMTYP);
		return weapon;
	}

	public static final String PISTOL_NAME = "sample pistol";
	public static final String PISTOL_DESC = "this is a typical pistol";
	public static final int PISTOL_SIZE = 25;
	public static final int PISTOL_WT = 25;
	public static final int PISTOL_DUR = 50;
	public static final int PISTOL_HARD = 10;	
	public static final int PISTOL_DAM = 0;
	public static final int PISTOL_MT = 4;
	public static final int PISTOL_ACC = 1;
	public static final int PISTOL_RNG = 0;
	public static final int PISTOL_WEPTYP = RangedWeapon.ONE_HANDED;
	public static final int PISTOL_DAMTYP = DamageType.PIERCING;
	public static final int PISTOL_AMMO = 18;
	public static final int PISTOL_AMREM = 18;
	
	public static RangedWeapon getSamplePistol() {
		RangedWeapon weapon = new RangedWeapon(PISTOL_NAME, PISTOL_DESC,
				PISTOL_SIZE, PISTOL_WT, PISTOL_DUR, PISTOL_HARD, PISTOL_DAM,
				PISTOL_MT, PISTOL_ACC,
				PISTOL_RNG, PISTOL_WEPTYP, PISTOL_DAMTYP, PISTOL_AMMO, PISTOL_AMREM);
		return weapon;
	}
	

	public static final String SWORD_NAME = "sample combat knife";
	public static final String SWORD_DESC = "it is long AND pointy";
//	public static final int SWORD_SIZE = 25;
//	public static final int SWORD_WT = 25;
//	public static final int SWORD_DUR = 100;
//	public static final int SWORD_HARD = 10;	
//	public static final int SWORD_DAM = 0;
	public static final int SWORD_MT = 3;
	public static final int SWORD_ACC = 3;
	public static final int SWORD_WEPTYP = MeleeWeapon.TWO_HANDED;
	public static final int SWORD_DAMTYP = DamageType.SLASHING;
	public static final int SWORD_PARRY = 0;	
	
	public static MeleeWeapon getSampleSword() {
		MeleeWeapon weapon = new MeleeWeapon(SWORD_MT, SWORD_ACC, SWORD_PARRY, SWORD_WEPTYP, SWORD_DAMTYP,
				SWORD_NAME, SWORD_DESC);
		return weapon;
	}
	
}
