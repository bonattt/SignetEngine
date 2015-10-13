package items;

import java.util.HashMap;

public abstract class RangedWeapon extends Weapon implements CombatItem {

	private int 
	ammoRemaining,
	ammoCapacity;
	
	public String ammoName = "ammo";
	
	public RangedWeapon(int might, int accuracy, int parry,	int weaponType, int ammo) {
		super(might, accuracy, parry, weaponType);
	}

	@Override
	public boolean isRangedWeapon(){
		return true;
	}
	@Override
	public boolean isMeleeWeapon(){
		return false;
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

	public String checkRemainingAmmo() {
		StringBuilder str = new StringBuilder();
		str.append("Your ");
		str.append(name);
		str.append(" has ");
		str.append(ammoRemaining);
		str.append(" ");
		str.append(ammoName);
		str.append(" remaining");
		return str.toString();
	}

}
