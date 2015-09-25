package items;

import java.util.HashMap;

public abstract class RangedWeapon extends Weapon implements CombatItem {

	private int 
	ammoRemaining,
	ammoCapacity;
	
	public RangedWeapon(int might, int accuracy, int parry,	int weaponType, int ammo) {
		super(might, accuracy, parry, weaponType);
	}
	public boolean useAmmo(int ammoUsed){
		if (ammoUsed > ammoRemaining){
			return false; // return false if not enough ammo.
		}
		ammoRemaining -= ammoUsed;
		return true;
	}
	public boolean reload(int ammoLoaded){
		if (ammoLoaded + ammoRemaining > ammoCapacity){
			return false;
		}
		ammoRemaining += ammoLoaded;
		return true;
	}
	public abstract String checkRemainingAmmo();

}
