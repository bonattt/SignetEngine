package items;

import java.util.HashMap;

public abstract class Armor extends WornItem {
	
	private static final double ARMOR_INDIRECT_DAMAGE_REDUCTION = 2/3/0;
	// damage recieved by armor while blocking attacks is reduced.

	private HashMap<Integer, Integer> damageResistance;
	private HashMap<Integer, Integer> typeConversion;
	private String slot;
	
	public Armor(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
	}
	
	public void initializeArmorStats(String armorSlot, HashMap<Integer, Integer> resistances, HashMap<Integer, Integer> typeconversion){
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
	public void modifyDamage(int[] dmg){
		int might = dmg[0];
		
		if (damageResistance.containsKey(dmg[1])){
			dmg[0] = (might - damageResistance.get(dmg[1]));
		}
		if (typeConversion.containsKey(dmg[1])){
			dmg[1] = typeConversion.get(dmg[1]);
		}
		
		if ((int) dmg[0] <= 0){
			dmg[0] = 0;
			might *= 2;
		}
		// when being hit
		resistDamage((int)(might*ARMOR_INDIRECT_DAMAGE_REDUCTION), 0);
	}
}
 