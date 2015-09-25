package inventory;

import java.util.ArrayList;
import java.util.HashMap;

import items.*;

public class Inventory {

	private ItemContainer backpack;
	private Gear equippedItems;
	private int carriedWeight;
	private Weapon equippedWeapon;
	private boolean weightUpToDate;
	
	
	public Inventory (){
		// TODO
	}
	
	public ArrayList<CombatItem> listEquippedWeapons(){
		ArrayList<CombatItem> list = new ArrayList<CombatItem>();
		// TODO 
		return list;
	}
	public Weapon getWeapon(){
		return equippedWeapon;
	}
	
	public double getCarriedWeight(){
		if(!weightUpToDate){
			updateCarriedWeight();
		}
		return carriedWeight;
	}
	private void updateCarriedWeight(){
		// get backpack weight
		// get equipment weight
		// get weapons weight.
		weightUpToDate = true;
	}
	public HashMap<String, WornItem> getEquipment(){
		return equippedItems.getClothing();
	}
	public HashMap<String, Armor> getArmorEquipped(){
		return equippedItems.getArmorEquipped();
	}
	public HashMap<String, ItemSlot> getEquippedWeapons(){
		return equippedItems.getEquippedWeapons();
	}
	
}

