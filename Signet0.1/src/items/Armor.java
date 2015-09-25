package items;

import health.Damage;

import java.util.HashMap;

import misc.DamageType;

public abstract class Armor extends WornItem {
	
	private static final double ARMOR_INDIRECT_DAMAGE_REDUCTION = 2/3/0;
	// damage recieved by armor while blocking attacks is reduced.

	private HashMap<DamageType, Integer> damageResistance;
	private HashMap<DamageType, DamageType> typeConversion;
	private String slot;
	
	public Armor(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
	}
	
	public void initializeArmorStats(String armorSlot, HashMap<DamageType, Integer> resistances, HashMap<DamageType, DamageType> typeconversion){
		damageResistance = resistances;
		slot = armorSlot;
		typeConversion = typeconversion;
	}
	
	public void modifyStats(int[] stats){
		// by default, does nothing.
	}
	
	/**
	 * mutates an array containing the damage might and type to reflect any protection this armor would provide.
	 * @param dmg
	 */
	public void modifyDamage(Damage dmg){
		
		int might = dmg.ammount;
		DamageType dt = dmg.type;
		
		if (damageResistance.containsKey(dmg.type)){
			dmg.ammount = (might - damageResistance.get(dt));
		}
		if (typeConversion.containsKey(dt)){
			dmg.type = typeConversion.get(dt);
		}
		
		if ((int) dmg.ammount <= 0){
			dmg.ammount = 0;
			might *= 2;
		}
		// when being hit
		resistDamage((int)(might*ARMOR_INDIRECT_DAMAGE_REDUCTION), 0);
	}
}
 