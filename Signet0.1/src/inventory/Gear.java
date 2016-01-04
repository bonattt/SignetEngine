package inventory;

import items.Armor;
import items.Item;
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
	
	public Gear(HashMap<String, Weapon> weaponSlots, HashMap<String, WornItem> clothingSlots,
			HashMap<String, Armor> armorSlots) {
		clothingWorn = clothingSlots;
		armorEquipped = armorSlots;
		weaponsCarried = weaponSlots;
		stats = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		updateWeight();
	}
	
	private void updateWeight() {
		totalWeight = 0;
		for (String key : clothingWorn.keySet()) {
			WornItem item = clothingWorn.get(key);
			if (item != null) {
				totalWeight += item.getWeight();
			}
		}
		for (String key : armorEquipped.keySet()) {
			Armor item = armorEquipped.get(key);
			if (item != null) {
				totalWeight += item.getWeight();
			}
		}
		for (String key : weaponsCarried.keySet()) {
			Weapon item = weaponsCarried.get(key);
			if (item != null) {
				totalWeight += item.getWeight();

			}
		}
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
				WornItem other = this.clothingWorn.get(key);
				if (! current.equals(other)) {
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
		
		int totalWeight = scanner.nextInt();
		scanner.nextLine();
		HashMap<String, WornItem> clothing = loadClothingAlpha0_1(scanner);
		HashMap<String, Armor> armor = loadArmorAlpha0_1(scanner);
		HashMap<String, Weapon> weapons = loadWeaponAlpha0_1(scanner);
		Gear gear = new Gear(weapons, clothing, armor);
		return gear;
	}

	private static HashMap<String, WornItem> loadClothingAlpha0_1(Scanner scanner) {
		HashMap<String, WornItem> clothing = new HashMap<String, WornItem>();
		String line = scanner.nextLine();
		while(! line.equals("end clothing")) {
			String itemType = scanner.nextLine();
			WornItem current;
			if (itemType.equals("null")){
				current = null;
			} else {
				current = (WornItem) Item.loadAlpha0_1(scanner, itemType);
			}
			clothing.put(line, current);
			line = scanner.nextLine();
		}
		return clothing;
	}

	private static HashMap<String, Armor> loadArmorAlpha0_1(Scanner scanner) {
		HashMap<String, Armor> armor = new HashMap<String, Armor>();
		String line = scanner.nextLine();
		while(! line.equals("end armor")) {
			Armor current;
			String itemType = scanner.nextLine();
			if (itemType.equals("null")) {
				current = null;
			} else {
				current = (Armor) Item.loadAlpha0_1(scanner, itemType);
			}
			armor.put(line, current);
			line = scanner.nextLine();
		}
		return armor;
	}
	
	private static HashMap<String, Weapon> loadWeaponAlpha0_1(Scanner scanner) {
		HashMap<String, Weapon> armor = new HashMap<String, Weapon>();
		String line = scanner.nextLine();
		while(! line.equals("end weapons")) {
			Weapon current;
			String itemType = scanner.nextLine();
			if (itemType.equals("null")) {
				current = null;
			} else {
				current = (Weapon) Item.loadAlpha0_1(scanner, itemType);
			}
			armor.put(line, current);
			line = scanner.nextLine();
		}
		return armor;
	}
	
	public void saveToFile(PrintWriter writer){
		writer.println(totalWeight);
		saveClothingWornToFile(writer);
		saveArmorToFile(writer);
		saveEquippedWeaponsToFile(writer);
	}
	private void saveClothingWornToFile(PrintWriter writer){
		for(String key : clothingWorn.keySet()) {
			writer.println(key);
			WornItem item = clothingWorn.get(key);
			if (item == null) {
				writer.println("null");
			} else {
				item.saveToFile(writer);
			}
		}
		writer.println("end clothing");
	}
	private void saveArmorToFile(PrintWriter writer){
		for(String key : armorEquipped.keySet()) {
			writer.println(key);
			Armor item = armorEquipped.get(key);
			if (item == null) { 
				writer.println("null");
			} else {
				item.saveToFile(writer);
			}
		}
		writer.println("end armor");
	}
	private void saveEquippedWeaponsToFile(PrintWriter writer){
		for(String key : weaponsCarried.keySet()) {
			writer.println(key);
			Weapon item = weaponsCarried.get(key);
			if (item == null) {
				writer.println("null");
			} else {
				item.saveToFile(writer);
			}
		}
		writer.println("end weapons");
	}
	
	public int getWeight() {
		return totalWeight;
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
	
	public boolean equipClothing(WornItem clothing) throws InventoryException {
		if (!clothingWorn.containsKey(clothing.getSlot())) {
			throw new InventoryException("ERROR in gear.equipClothing: clothing slot does not exist");
		} else if (clothingWorn.get(clothing.getSlot()) != null){
			TextTools.display("You are already wearing something in the " + clothing.getSlot() + " slot.");
			return false;
		}
		clothingWorn.put(clothing.getSlot(), clothing);
		totalWeight += clothing.getWeight();
		return true;
	}
	
	public WornItem removeClothing(String slot) throws InventoryException {
		if(!clothingWorn.containsKey(slot)) {
			throw new InventoryException("ERROR in Gear.removeClothing: clothing slot does not exist");
		} else if (clothingWorn.get(slot) == null){
			TextTools.display("Your " + slot + " slot is already empty");
			return null;
		}
		
		WornItem removedClothing = clothingWorn.get(slot);
		totalWeight -= removedClothing.getWeight();
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
	
	public boolean equipArmor(Armor armor) throws InventoryException {
		if (!armorEquipped.containsKey(armor.getSlot())) {
			throw new InventoryException("ERROR in Gear.equipArmor: armor slot does not exist.");
		} else if (armorEquipped.get(armor.getSlot()) == null){
			armorEquipped.put(armor.getSlot(), armor);
			totalWeight += armor.getWeight();
			return true;
		} else {
			TextTools.display("you already have armor equipped there");
			return false;
		}
	}
	
	public Armor removeArmor(String slot) throws InventoryException{
		if (!armorEquipped.containsKey(slot)){
			throw new InventoryException("ERROR in Gear.removeArmor: armor slot does not exist");
		}
		else if (armorEquipped.get(slot) == null){
			TextTools.display("You are not wearing any armor there.");
			return null;
		}
		Armor armorRemoved = armorEquipped.get(slot);
		armorEquipped.put(slot, null);
		totalWeight -= armorRemoved.getWeight();
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
	
	public boolean addWeapon(String slot, Weapon weapon) throws InventoryException {
		if (!weaponsCarried.containsKey(slot)){ 
			throw new InventoryException("ERROR in Gear.addWeapon: weapon slot does not exist");
		} else if ((weaponsCarried.get(slot) != null)) {
			TextTools.display(weapon.name() + " cannot be equipped because that slot is occupied");
			return false;
		} else {
			weaponsCarried.put(slot, weapon);
			totalWeight += weapon.getWeight();
			return true;
		}
	}
	
	public Weapon removeWeapon(String slot){
		Weapon removed = weaponsCarried.get(slot);
		if (removed != null) {
			totalWeight -= removed.getWeight();
		}
		weaponsCarried.put(slot, null);
		return removed;
	}
	
	protected boolean selectLocationToEquip(Weapon newWeapon) throws InventoryException {
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
