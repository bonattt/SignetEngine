package inventory;

import items.Armor;
import items.Item;
import items.Weapon;
import items.WornItem;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import misc.TextTools;
import creatures.Creature;

public class Gear {
	
	private int totalWeight;
	private boolean statsUpToDate;
	private int[] stats;
	
	private HashMap<String, WornItem> clothingWorn;		// body slot (str) --> nonarmor clothing and worn magic items etc... (Item)
	private HashMap<String, Armor> armorEquipped;		// body slot (str) --> armor (armor)
	private HashMap<String, Weapon> equippedWeapons;	// slot name (str) --> item slot (ItemSlot)
	
	public Gear(){
		clothingWorn = new HashMap<String, WornItem>();
		armorEquipped = new HashMap<String, Armor>();
		equippedWeapons = new HashMap<String, Weapon>();
		totalWeight = 0;
		
	}
	
	public void refreshWeight(){
		totalWeight = 0;
		for (String key : clothingWorn.keySet()){
			totalWeight += clothingWorn.get(key).getWeight();
		}
		
		for (String key : armorEquipped.keySet()){
			totalWeight += armorEquipped.get(key).getWeight();
		}
		
		for (String key : equippedWeapons.keySet()){
			totalWeight += equippedWeapons.get(key).getWeight();
		}		
	}
	
	public int[] getStatMods(){
		if(!statsUpToDate){
			stats = new int[Creature.ABILITIES.length];
			for (String key : clothingWorn.keySet()){
				clothingWorn.get(key).modifyStats(stats);
			}
			
			for (String key : armorEquipped.keySet()){
				armorEquipped.get(key).modifyStats(stats);
			}
		}
		return stats;
	}
	
	
	///////////
	
	public HashMap<String, WornItem> getClothingEquipped(){
		return clothingWorn;
	}
	
	public ArrayList<String> getClothingSlots(){
		ArrayList<String> slots = new ArrayList<String>();
		for (String key : clothingWorn.keySet()){
			slots.add(key);
		}
		return slots;
	}
	
	public boolean equipClothing(WornItem clothing){
		if (clothingWorn.get(clothing.slot) != null){
			TextTools.display("You are already wearing something in the " + clothing.slot + " slot.");
			return false;
		}
		clothingWorn.put(clothing.slot, clothing);
		return true;
	}
	
	public WornItem removeClothing(String slot){
		if(clothingWorn.get(slot) == null){
			TextTools.display("Your " + slot + " slot is already empty");
			return null;
		}
		
		WornItem removedClothing = clothingWorn.get(slot);
		clothingWorn.put(slot, null);
		return removedClothing;
	}
	
	public WornItem getClothing(String clothingSlot){
		return clothingWorn.get(clothingSlot);
	}
	
	protected boolean createClothingSlot(String slot){
		if (clothingWorn.containsKey(slot)){
			TextTools.display("Clothing slot " + slot + " already exists!");
			return false;
		}
		clothingWorn.put(slot, null);
		return true;
	}
	
	///////////
	
	public HashMap<String, Armor> getArmorEquipped(){
		return armorEquipped;
	}
	
	public ArrayList<String> getArmorSlots(){
		ArrayList<String> slots = new ArrayList<String>();
		for (String key : armorEquipped.keySet()){
			slots.add(key);
		}
		return slots;
	}
	
	public boolean equipArmor(Armor armor){
		if (armorEquipped.get(armor.slot) == null){
			armorEquipped.put(armor.slot, armor);
			return true;
		} else {
			TextTools.display("you already have armor equipped there");
			return false;
		}
	}
	
	public Armor removeArmor(String slot){
		if (!armorEquipped.containsKey(slot)){
			TextTools.display("ERROR That body slot does not exist.");
			return null;
		}
		else if (armorEquipped.get(slot) == null){
			TextTools.display("You are not wearing any armor there.");
			return null;
		}
		Armor armorRemoved = armorEquipped.get(slot);
		armorEquipped.put(slot, null);
		return armorRemoved;
	}
	
	protected boolean createArmorSlot(String slot){
		if(armorEquipped.containsKey(slot)){
			TextTools.display("The armor slot " + slot + " already exists.");
			return false;
		}
		armorEquipped.put(slot, null);
		return true;
	}
	
	public Armor getArmor(String slot){
		return armorEquipped.get(slot);
	}
	
	
	////////
	
	public HashMap<String, Weapon> getEquippedWeapons(){
		return equippedWeapons;
	}

	public ArrayList<String> getWeaponSlots(){
		ArrayList<String> slots = new ArrayList<String>();
		for (String key : equippedWeapons.keySet()){
			slots.add(key);
		}
		return slots;
	}
	public Weapon getWeapon(String slot){
		return equippedWeapons.get(slot);
	}
	
	public boolean addWeapon(String slot, Weapon weapon){
		if (!equippedWeapons.containsKey(slot)){
			TextTools.display("ERROR weapon slot does not exist");
			return false;
		} else if ((equippedWeapons.get(slot) != null)) {
			TextTools.display(weapon.name + " cannot be equipped because that slot is occupied");
			return false;
		} else {
			equippedWeapons.put(slot, weapon);
			return true;
		}
	}
	
	public boolean removeWeapon(String slot){
		if(equippedWeapons.get(slot) == null){
			return false;
		}
		equippedWeapons.put(slot, null);
		return true;
	}
	
	protected boolean createWeaponSlot(String slot){
		if(equippedWeapons.containsKey(slot)){
			TextTools.display("The weapon slot " + slot + " already exists.");
			return false;
		}
		equippedWeapons.put(slot, null);
		return true;
	}
	private void selectLocationToEquip(Weapon newWeapon){
		String question = "Where would you like to equip the weapon";
		String[] weaponSlots = new String[equippedWeapons.size()];
		String[] answers = new String[equippedWeapons.size() + 1];
		int i = 0;
		for (String key : equippedWeapons.keySet()){
			weaponSlots[i] = key;
			Weapon currentWeapon = equippedWeapons.get(key);
			if (currentWeapon == null){
				answers[i] = key + " [empty]";
			} else {
				answers[i] = key + " [" + currentWeapon.name + "]";
			}
			i += 1;
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (choice == 0){
			return;
		} 
		addWeapon(weaponSlots[choice - 1], newWeapon);
	}
}
