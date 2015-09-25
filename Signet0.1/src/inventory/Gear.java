package inventory;

import items.Armor;
import items.Item;
import items.WornItem;

import java.util.HashMap;

import creatures.Creature;

public class Gear {
	
	private int totalWeight;
	private boolean statsUpToDate;
	private int[] stats;
	
	private HashMap<String, WornItem> clothing;		// body slot (str) --> nonarmor clothing and worn magic items etc... (Item)
	private HashMap<String, Armor> armorEquipped;		// body slot (str) --> armor (armor)
	private HashMap<String, ItemSlot> equippedWeapons;	// slot name (str) --> item slot (ItemSlot)
	
	public Gear(){
		// TODO implement constructor
		clothing = new HashMap<String, WornItem>();
		armorEquipped = new HashMap<String, Armor>();
		equippedWeapons = new HashMap<String, ItemSlot>();
		totalWeight = 0;
	}
	public void refreshWeight(){
		totalWeight = 0;
		for (String key : clothing.keySet()){
			totalWeight += clothing.get(key).getWeight();
		}
		
		for (String key : armorEquipped.keySet()){
			totalWeight += armorEquipped.get(key).getWeight();
		}
		
		for (String key : equippedWeapons.keySet()){
			totalWeight += equippedWeapons.get(key).getWeight();
		}		
	}
	public HashMap<String, WornItem> getClothing(){
		return clothing;
	}
	public HashMap<String, Armor> getArmorEquipped(){
		return armorEquipped;
	}
	public HashMap<String, ItemSlot> getEquippedWeapons(){
		return equippedWeapons;
	}
	
	public int[] getStatMods(){
		if(!statsUpToDate){
			stats = new int[Creature.ABILITIES.length];
			for (String key : clothing.keySet()){
				clothing.get(key).modifyStats(stats);
			}
			
			for (String key : armorEquipped.keySet()){
				armorEquipped.get(key).modifyStats(stats);
			}
		}
		return stats;
	}
	
}
