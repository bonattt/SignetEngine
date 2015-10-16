package items;

import inventory.Gear;
import inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

import creatures.Creature;
import misc.TextTools;

public abstract class Weapon extends Item implements CombatItem {

	public static final int LIGHT = 0;
	public static final int ONE_HANDED = 1;
	public static final int TWO_HANDED = 2;
	
	private HashMap<String, Integer> utility;
	private int 
	weaponType,
	parry,		// stat used as a bonus to active defense test while using this weapon.
	accuracy,	// bonus applied to 
	might;		// amount of damage dealt by a hit with this weapon.
	
	public Weapon(int might, int accuracy, int parry, int weaponType) {
		super(getWeaponSize(weaponType), getWeaponWeight(weaponType), getWeaponDurability(weaponType), getWeaponHardness(weaponType), 0);
		this.weaponType = weaponType;
		this.parry = parry;
		this.accuracy = accuracy;
		this.might = might;
	}
	/**
	 * puts all availible combat actions into 
	 * @param combat
	 * @return
	 */
	
	public int getWeaponType(){
		return weaponType;
	}

	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		String slot = null;
		ArrayList<String> slots = inv.getEquipment().getWeaponSlots();
		for (int i = 0; i < slots.size(); i++){
			String current = slots.get(i);
			if(inv.getEquipment().getWeapon(current) == this){
				slot = current;
				break;
			}
		}
		
		if(choice == 1) {				// Stow
			if(inv.spaceRemaining() >= getSize()){
				inv.getEquipment().removeWeapon(slot);
				inv.store(this);
			} else {
				TextTools.display("There is no room in your " + name + " in you inventory");
			}
		} else if (choice == 2) {		// discard
			inv.getEquipment().removeWeapon(slot);
		} else {
			// ERROR
		}
	}
	@Override
	public boolean isEquipment(){
		return true;
	}
	@Override
	public boolean isExpendible(){
		return false;
	}
	@Override
	public boolean canBeUsedInCombat(){
		return false;
	}
	@Override
	public boolean isWeapon(){
		return true;
	}
	@Override
	public boolean isMeleeWeapon(){
		return true;
	}
	
	@Override
	public String checkDamage(){
		String s = super.checkDamage();
		return s;
	}
	public void setUtility(){
		
	}
	
	public int getUtilityBonus(String task){
		if(utility.containsKey(task)){
			return utility.get(task);
		}
		return -1;
	}
	
	public static int getWeaponWeight(int type){
		if (type == Weapon.LIGHT){
			return 5;
		}
		else if (type == ONE_HANDED){
			return 15;
		}
		else if (type == TWO_HANDED){
			return 25;
		}
		return Integer.MIN_VALUE;
	}
	public static int getWeaponSize(int type){
		if (type == LIGHT){
			return 5;
		}
		else if (type == ONE_HANDED){
			return 15;
		}
		else if (type == TWO_HANDED){
			return 25;
		}
		return Integer.MIN_VALUE;
	}
	public static int getWeaponDurability(int type){
		if (type == LIGHT){
			return 100;
		}
		else if (type == ONE_HANDED){
			return 125;
		}
		else if (type == TWO_HANDED){
			return 150;
		}
		return Integer.MIN_VALUE;
	}
	public static int getWeaponHardness(int type){
		if (type == LIGHT){
			return 5;
		}
		else if (type == ONE_HANDED){
			return 5;
		}
		else if (type == TWO_HANDED){
			return 5;
		}
		return Integer.MIN_VALUE;
	}
	
	public int getMight(){
		return might;
	}
	public int getAccuracy(){
		return accuracy;
	}
	public int getParry(){
		return parry;
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		throw new Exception("useFromInventory is not defined in this class");
	}
}
