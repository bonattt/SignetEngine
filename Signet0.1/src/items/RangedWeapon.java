package items;

import java.io.PrintWriter;
import java.util.Scanner;

import misc.TextTools;

public class RangedWeapon extends Weapon {

	public static final int LIGHT = 3;
	public static final int ONE_HANDED = 4;
	public static final int TWO_HANDED = 5;
	public static final int HEAVY = 6;
	
	private int 
	ammoRemaining,
	ammoCapacity,
	range;
	
	public String ammoName = "ammo";
	
	public RangedWeapon(String name, String description,
			int size, int wt, int dur, int hard, int dam,
			int might, int accuracy, int range, int weaponType, int damageType, int ammoCap, int ammoRem) {
		super(size, wt, dur, hard, dam, name, description,
				might, accuracy, weaponType, damageType);
		this.range = range;
		ammoCapacity = ammoCap;
		ammoRemaining = ammoRem;
	}
	
	public int getRange() {
		return range;
	}
	public int getAmmoCapacity() {
		return ammoCapacity;
	}
	public int getAmmoRemaining() {
		return ammoRemaining;
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
			TextTools.display("not enough ammo");
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
		str.append(this.name());
		str.append(" has ");
		str.append(ammoRemaining);
		str.append(" ");
		str.append(ammoName);
		str.append(" remaining");
		return str.toString();
	}
	
	public int getRemainingAmmo() {
		return ammoRemaining;
	}

	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("ranged weapon");
		saveBaseStats(writer);
		saveBaseWeaponStats(writer);
		writer.println(range);
		writer.println(ammoCapacity);
		writer.println(ammoRemaining);
	}
	
	public static RangedWeapon loadRangedWeaponAlpha0_1(Scanner scanner) {
		int size, weight, durability, hardness, damage, weaponType, damageType, accuracy, might, range, ammoCap, ammoRem;
		String name = scanner.nextLine();
		String description = Item.loadLongStringAlpha0_1(scanner);
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		might = scanner.nextInt();
		accuracy = scanner.nextInt();
		weaponType = scanner.nextInt();
		damageType = scanner.nextInt();
		range = scanner.nextInt();
		ammoCap = scanner.nextInt();
		ammoRem = scanner.nextInt();
		scanner.nextLine(); // realign the scanner to read lines after performing nextInt()
		RangedWeapon weapon = new RangedWeapon(name, description,
				size, weight, durability, hardness, damage, might, accuracy,
				range, weaponType, damageType, ammoCap, ammoRem);
		return weapon;
	}
	
	@Override
	public boolean equals(Object o){
		if (! (o instanceof RangedWeapon)) {
			return false;
		}
		RangedWeapon weapon = (RangedWeapon) o;
		return ((weapon.name().equals(this.name()) &&
				(weapon.getAccuracy() == this.getAccuracy()) &&
				(weapon.getMight() == this.getMight()) &&
				(weapon.damageTypesEqual(this)) &&
				(weapon.getSize() == this.getSize()) && 
				(weapon.getRange() == this.getRange()) && 
				(weapon.getAmmoCapacity() == this.getAmmoCapacity()) &&
				(weapon.getAmmoRemaining() == this.getAmmoRemaining()) &&
				(weapon.getWeaponType() == this.getWeaponType()) &&
				(weapon.getWeight() == this.getWeight()) &&
				(weapon.description().equals(this.description()))));
	}

}
