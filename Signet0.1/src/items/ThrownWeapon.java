package items;

public abstract class ThrownWeapon extends RangedWeapon{

	public ThrownWeapon(int might, int accuracy, int parry, int weaponType,
			int ammo) {
		super(might, accuracy, parry, weaponType, ammo);
	}
	public boolean isExpendible(){
		return true;
	}
}
