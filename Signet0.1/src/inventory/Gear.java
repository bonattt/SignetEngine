package inventory;

import items.Armor;
import items.MeleeWeapon;
import items.Weapon;
import items.WornItem;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import misc.TextTools;
import creatures.Creature;

public class Gear {
	
	private int totalWeight;
	private boolean statsUpToDate;
	private int[] stats;
	
	private HashMap<String, WornItem> clothingWorn;		// body slot (str) --> nonarmor clothing and worn magic items etc... (Item)
	private HashMap<String, Armor> armorEquipped;		// body slot (str) --> armor (armor)
	private HashMap<String, Weapon> weaponsCarried;	// slot name (str) --> item slot (ItemSlot)
	
	public Gear(HashMap<String, Weapon> weaponsSlots, HashMap<String, WornItem> clothingSlots,
			HashMap<String, Armor> armorSlots) {
		clothingWorn = clothingSlots;
		armorEquipped = armorSlots;
		weaponsCarried = weaponsSlots;
		stats = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		totalWeight = 0;
	}
	
	@Override
	public boolean equals(Object o) {
		// mainly used for testing, so perfomance of "instanceof" is not critical.
		if (! (o instanceof Gear)) {
			return false;
		}
		Gear gear = (Gear) o;
		if (gear.stats.length != this.stats.length) {
			return false;
		}
		for (int i = 0; i < this.stats.length; i++) {
			if (gear.stats[i] != this.stats[i]) {
				return false;
			}
		}
		
		// check weapons
		for (String key : gear.weaponsCarried.keySet()) {
			if (! this.weaponsCarried.containsKey(key)) {
				return false;
			}
			Weapon current = gear.weaponsCarried.get(key);
			if (current == null) {
				if (this.weaponsCarried.get(key) != null) {
					return false;
				}
			} else {
				if (! current.equals(this.weaponsCarried.get(key))) {
					return false;
				}
			}
		}
		
		// check armor
		for (String key : gear.armorEquipped.keySet()) {
			if (! this.armorEquipped.containsKey(key)) {
				return false;
			}
			Armor current = gear.armorEquipped.get(key);
			if (current == null) {
				if (this.armorEquipped.get(key) != null) {
					return false;
				}
			} else {
				if (! current.equals(this.armorEquipped.get(key))) {
					return false;
				}
			}
		}

		// check clothing
		for (String key : gear.clothingWorn.keySet()) {
			if (! this.clothingWorn.containsKey(key)) {
				return false;
			}
			WornItem current = gear.clothingWorn.get(key);
			if (current == null) {
				if (this.clothingWorn.get(key) != null) {
					return false;
				}
			} else {
				if (! current.equals(this.clothingWorn.get(key))) {
					return false;
				}
			}
		}
		
		// done checking hashMaps
		return (gear.totalWeight == this.totalWeight) &&
				(this.weaponsCarried.size() == gear.weaponsCarried.size()) &&
				(this.armorEquipped.size() == gear.armorEquipped.size()) &&
				(this.clothingWorn.size() == gear.clothingWorn.size());
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("weapons equipped: ");
		str.append(weaponsCarried.toString());
		str.append("armor equipped: ");
		str.append(armorEquipped.toString());
		str.append("clothing equipped: ");
		str.append(clothingWorn.toString());
		return str.toString();
	}
	
	public static Gear loadAlpha0_1(Scanner scanner){
		return null;
	}
	
	public void saveToFile(PrintWriter writer){
		writer.println("equipment");
		writer.println(totalWeight);
		writer.println("clothingWorn");
		saveClothingWornToFile(writer);
		saveArmorToFile(writer);
		saveEquippedWeaponsToFile(writer);
		writer.println("end equipment");
	}
	private void saveClothingWornToFile(PrintWriter writer){
		writer.println("clothing");
		
		
		writer.println("end clothing");
	}
	private void saveArmorToFile(PrintWriter writer){
		writer.println("armor");
		
		
		writer.println("end armor");
	}
	private void saveEquippedWeaponsToFile(PrintWriter writer){
		writer.println("equipped weapons");
		
		writer.println("end equipped weapons");
	}
	
	public int getWeight(){
		return totalWeight;
	}
	
	public void refreshWeight(){
		totalWeight = 0;
		for (String key : clothingWorn.keySet()){
			totalWeight += clothingWorn.get(key).getWeight();
		}
		for (String key : armorEquipped.keySet()){
			totalWeight += armorEquipped.get(key).getWeight();
		}
		for (String key : weaponsCarried.keySet()){
			totalWeight += weaponsCarried.get(key).getWeight();
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
	
	public HashMap<String, WornItem> getClothingWorn(){
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
	
	public Armor getArmor(String slot){
		return armorEquipped.get(slot);
	}
	
	
	////////
	
	public HashMap<String, Weapon> getEquippedWeapons(){
		return weaponsCarried;
	}

	public ArrayList<String> getWeaponSlots(){
		ArrayList<String> slots = new ArrayList<String>();
		for (String key : weaponsCarried.keySet()){
			slots.add(key);
		}
		return slots;
	}
	public Weapon getWeapon(String slot){
		return weaponsCarried.get(slot);
	}
	
	public boolean addWeapon(String slot, Weapon weapon){
		if (!weaponsCarried.containsKey(slot)){
			TextTools.display("ERROR weapon slot does not exist");
			return false;
		} else if ((weaponsCarried.get(slot) != null)) {
			TextTools.display(weapon.name() + " cannot be equipped because that slot is occupied");
			return false;
		} else {
			weaponsCarried.put(slot, weapon);
			return true;
		}
	}
	
	public boolean removeWeapon(String slot){
		if(weaponsCarried.get(slot) == null){
			return false;
		}
		weaponsCarried.put(slot, null);
		return true;
	}
	
	protected boolean selectLocationToEquip(Weapon newWeapon){
		String question = "Where would you like to equip the weapon";
		String[] weaponSlots = new String[weaponsCarried.size()];
		String[] answers = new String[weaponsCarried.size() + 1];
		int i = 0;
		for (String key : weaponsCarried.keySet()){
			weaponSlots[i] = key;
			Weapon currentWeapon = weaponsCarried.get(key);
			if (currentWeapon == null){
				answers[i] = key + " [empty]";
			} else {
				answers[i] = key + " [" + currentWeapon.name() + "]";
			}
			i += 1;
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (choice == 0){
			return false;
		} 
		return addWeapon(weaponSlots[choice - 1], newWeapon);
	}
}
