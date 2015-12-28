package inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import creatures.Creature;
import misc.TextTools;
import items.*;

public class Inventory {
	
	public static final int STARTING_BAG_SIZE = 1000;
	public static final int STARTING_BAG_WEIGHT = 10;

	private ItemContainer backpack;
	private Gear equipment;
	private LightSource lightSource;
	private Weapon equippedWeapon;
	private int carriedWeight;
	
	public Inventory(){
		HashMap<String, Weapon> weaponSlots = new HashMap<String, Weapon>();
		HashMap<String, WornItem> clothingSlots = new HashMap<String, WornItem>();
		HashMap<String, Armor> armorSlots = new HashMap<String, Armor>();
		initializeEquipmentSlots(weaponSlots, clothingSlots, armorSlots);
		equipment = new Gear(weaponSlots, clothingSlots, armorSlots);
		backpack = getStartingBackpack();
		equippedWeapon = null;
	}
	
	private static void initializeEquipmentSlots(HashMap<String, Weapon> weaponSlots,
			HashMap<String, WornItem> clothingSlots, HashMap<String, Armor> armorSlots) {
		weaponSlots.put("boot-holster", null);
		weaponSlots.put("hip-holster", null);
		clothingSlots.put("shirt", null);
		clothingSlots.put("pants", null);
		clothingSlots.put("hat", null);
		armorSlots.put("main armor", null);
		armorSlots.put("helmet", null);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Inventory)) {
			return false;
		}
		Inventory inv = (Inventory) o;
		
		if (this.lightSource == null) {
			if (inv.lightSource != null) {
				return false;
			}
		} else {
			if (! inv.lightSource.equals(this.lightSource)) {
				return false;
			}
		}
		
		if (this.equippedWeapon == null) {
			if (inv.equippedWeapon != null) {
				return false;
			}
		} else {
			if (! inv.equippedWeapon.equals(this.equippedWeapon)) {
				return false;
			}
		}
		
		return (inv.backpack.equals(this.backpack)) &&
				(inv.equipment.equals(this.equipment)) &&
				(inv.carriedWeight == this.carriedWeight);
	}
	
	public LightSource getLightSource(){
		return lightSource;
	}
	
	public static HashMap<String, Item> convertHashMapWeaponsToItems(HashMap<String, Weapon> oldItems){
		HashMap<String, Item> newItems = new HashMap<String, Item>();
		for (String key : oldItems.keySet()){
			newItems.put(key, oldItems.get(key));
		}
		return newItems;
	}
	public static ItemContainer getStartingBackpack(){
		return new ItemContainer(STARTING_BAG_WEIGHT, STARTING_BAG_SIZE, "backpack");
	}
	
	public boolean tryToCarryWeapon(Inventory inv, Weapon weapon){
		if(equippedWeapon == null){
			equippedWeapon = weapon;
			return true;
		}
		String question = "you are already carrying a " + equippedWeapon.name() + " what would you like to do?";
		String[] answers = new String[]{"stow " + equippedWeapon.name(),
										"equip " + equippedWeapon.name(),
										"cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 1){
			return tryToStowCarriedWeaponToEquipNewWeapon(inv, weapon);
		} else if (choice == 2){
			return tryToEquipCarriedWeaponToEquipNewWeapon(inv, weapon);
		}
		return false;
	}
	public static Inventory loadAlpha1_0fromFile (Scanner scanner){
		return null;
	}
	
	public void saveToFile(PrintWriter writer){
		writer.println("inventory");
		writer.println(carriedWeight);
		backpack.saveToFile(writer);
		equipment.saveToFile(writer);
		
		if(lightSource == null){
			writer.println("no light source");
		} else {
			lightSource.saveToFile(writer);
		}
		
		if (equippedWeapon == null){
			writer.println("no equipped weapon");
		} else {
			equippedWeapon.saveToFile(writer);
		}
		
		writer.println("end inventory");
	}
	private boolean tryToEquipCarriedWeaponToEquipNewWeapon(Inventory inv, Weapon weapon){
		if(inv.getEquipment().selectLocationToEquip(weapon)){
			inv.discardItem(weapon);
			return true;
		}
		return false;
	}
	private boolean tryToStowCarriedWeaponToEquipNewWeapon(Inventory inv, Weapon weapon){
		if(inv.spaceRemaining() >= (equippedWeapon.getSize() - weapon.getSize())){
			inv.discardItem(weapon);
			inv.store(equippedWeapon);
			equippedWeapon = weapon;
			return true;
		}
		return false;
	}
	
	public static HashMap<String, Item> convertHashMapArmorToItems(HashMap<String, Armor> oldItems){
		HashMap<String, Item> newItems = new HashMap<String, Item>();
		for (String key : oldItems.keySet()){
			newItems.put(key, oldItems.get(key));
		}
		return newItems;
	}
	public static HashMap<String, Item> convertHashMapClothingToItems(HashMap<String, WornItem> oldItems){
		HashMap<String, Item> newItems = new HashMap<String, Item>();
		for (String key : oldItems.keySet()){
			newItems.put(key, oldItems.get(key));
		}
		return newItems;
	}
	public ArrayList<Item> store(ArrayList<Item> items){
		while(items.size() > 0){
			if (!store(items.get(0))) {
				return items;
			}
			items.remove(0);
		}
		return items;
	}
	public boolean store(Item item){
		return backpack.addItem(item);
	}
	
	public void displayBackpack(){
		TextTools.display(backpack.toString());
	}
	public int spaceRemaining(){
		return backpack.getSpaceLeft();
	}
	/*
	 * FOR TESTING PURPOSES
	 */
	public Gear getEquipment(){
		return equipment;
	}
	
	public Item selectItemFromBackpack(String question){
		ArrayList<Item> items = backpack.getAllItems();
		String[] answers = new String[items.size() + 1];
		for (int i = 0; i < items.size(); i++){
			answers[i] = items.get(i).name();
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return null;
		}
		return items.get(choice - 1);
	}
	/**
	 * This method allows the player to access items in their inventory during explore mode.
	 * This method starts off by prompting the player to select what part of the inventory they would
	 *  like to access (armor, weapons, 
	 */
	public void accessInventoryDuringExplore(Creature player){
		String question = "What part of the inventory would you like to access?";
		String[] answers = new String[]{"backpack", "equipment", "exit inventory"};
		while(true) {
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if(choice == 0){
				break;
			} else if (choice == 1){
				accessBackpackDuringExplore(player);
			} else if (choice == 2){
				accessEquipmentDuringExplore(player);
			}
		}
	}
	private void accessBackpackDuringExplore(Creature player){
		String question = "What kind of items are you looking for?";
		String[] answers = new String[]{"anything", "first-aid", "weapons", "armor", "clothing", "exit backpack"};
		
		while(true) {
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if(choice == 0){
				break;
			}
			else if (choice == 1){
				backpack.accessSelectedItems(backpack.getAllItems(), this, player);
			}
			else if (choice == 2){
				backpack.accessSelectedItems(backpack.getMedicine(), this, player);
			}
			else if (choice == 3){
				backpack.accessSelectedItems(backpack.getWeapons(), this, player);
			}
			else if (choice == 4){
				backpack.accessSelectedItems(backpack.getArmor(), this, player);
			}
			else if (choice == 5){
				backpack.accessSelectedItems(backpack.getClothing(), this, player);
			}
		}
	}
//	private void accessWeaponsInBackpackDuringExplore(){
//		
//	}
//	private void accessArmorInBackpackDuringExplore(){
//		
//	}
//	private void accessClothingInBackpackDuringExplore(){
//		
//	}
	private void accessEquipmentDuringExplore(Creature player){
		String question = "What kind of equipment would you like you access?";
		String[] answers = new String[]{"weapons", "armor", "clothing", "exit equipment"};
		while(true) {
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if(choice == 0){
				break;
			}
			if (choice == 1){
				accessEquippedWeaponsDuringExplore(player);
				continue;
			}
			if (choice == 2){
				accessEquippedArmorDuringExplore(player);
				continue;
			}
			if (choice == 3){
				accessEquippedClothingDuringExplore(player);
				continue;
			}
		}
	}
	private void accessEquippedWeaponsDuringExplore(Creature player){
		String question = "Which equipped weapon would you like to";
		HashMap<String, Weapon> weapons = equipment.getEquippedWeapons();
		accessGenericEquipmentDuringExplore(player, convertHashMapWeaponsToItems(weapons), question);
	}
	private void accessEquippedArmorDuringExplore(Creature player){
		String question = "Which equipped armor would you like to";
		HashMap<String, Armor> armor = equipment.getArmorEquipped();
		accessGenericEquipmentDuringExplore(player, convertHashMapArmorToItems(armor), question);
	}
	private void accessEquippedClothingDuringExplore(Creature player){
		String question = "Which equipped clothing would you like to use?";
		HashMap<String, WornItem> clothingWorn = equipment.getClothingWorn();
		accessGenericEquipmentDuringExplore(player, convertHashMapClothingToItems(clothingWorn), question);
	}
		
	private void accessGenericEquipmentDuringExplore(Creature player, HashMap<String, Item> equippedItems, String question) {
		String[] clothingSlots = new String[equippedItems.size()];
		String[] answers = new String [clothingSlots.length + 1];
		int i = 0;
		for(String key : equippedItems.keySet()){
			Item current = equippedItems.get(key);
			if(current != null){
				answers[i] = current.name() + " [" + key + "]";
			}
				else {
				answers[i] = "empty [" + key + "]";
			}
			clothingSlots[i] = key;
			i += 1;
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		}
		Item targetItem = equippedItems.get(clothingSlots[choice - 1]);
		if (targetItem == null){
			TextTools.display("That slot is empty!");
			return;
		}
		targetItem.useWhileEquipped(this, player);
	}
	
	public ArrayList<Weapon> listEquippedWeapons(){
		ArrayList<Weapon> list = new ArrayList<Weapon>();
		for (String key : equipment.getEquippedWeapons().keySet()){
			list.add(equipment.getEquippedWeapons().get(key));
		}
		return list;
	}
	
	public Weapon getWeapon(){
		return equippedWeapon;
	}
	public boolean discardItem(Item item){
		return backpack.removeItem(item);
	}
	public boolean pickUpWeapon(Weapon newWeapon){
		// TODO add player input
		if(equippedWeapon != null){
			return false;
		}
		equippedWeapon = newWeapon;
		return true;
	}
	
	/**
	 * Prompts the player to select a weapon from the weapons he has equipped 
	 * @param question
	 * @param backEnabled
	 * @return
	 */
	public void switchWeapons(){
		
		String weaponSlot;
		Weapon newWeapon;
		while(true){
			weaponSlot = selectWeaponSlot("What weapon would you like to draw?", TextTools.BACK_ENABLED);
			if (weaponSlot == null){
				return;
			}
			newWeapon = equipment.getWeapon(weaponSlot);
			if(newWeapon == null){
				TextTools.display(weaponSlot + " slot is empty, select another weapon.");
				continue;
			}
			break;
		}
		if (equippedWeapon.getWeaponType() < 2){
			equipNewWeaponWhileCarryingSmallWeapon(weaponSlot, newWeapon);
		}else if(spaceRemaining() >= equippedWeapon.getSize()){
			equipNewWeaponWhileCarryingLargeWeapon(weaponSlot, newWeapon);
		} else {
			
		}
	}	
	private void equipNewWeaponWhileCarryingSmallWeapon(String weaponSlot, Weapon newWeapon){
		String question = "You are alrady carrying a " + equippedWeapon.name() + " what would you like to do with it?";
		String[] answers = new String[]{"swap " + equippedWeapon.name() + " with " + newWeapon.name(),
											"drop " + equippedWeapon.name(),
											"stow " + equippedWeapon.name() + " in " + backpack.name(),
											"cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		// if (choice == 0) do nothing
		if (choice == 1){
			switchWeaponSwapOld(weaponSlot, newWeapon);
		} else if (choice == 2) {
			switchWeaponsDropOld(weaponSlot, newWeapon);
		} else if (choice == 3) {
			switchWeaponsStowOld(weaponSlot, newWeapon);
		}
 	}	
	private void equipNewWeaponWhileCarryingLargeWeapon(String weaponSlot, Weapon newWeapon){
		String question = "Your " + equippedWeapon.name() + " is too big to be equipped. What will you do?";
		StringBuilder stow = new StringBuilder();
		stow.append("stow ");
		stow.append(equippedWeapon.name());
		stow.append(" in ");
		stow.append(backpack.name());
		String[] answers = new String[]{"drop " + equippedWeapon.name(),
										stow.toString(),
										"cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		// if (choice == 0) do nothing
		if (choice == 1) {
			switchWeaponsDropOld(weaponSlot, newWeapon);
		} else if (choice == 2) {
			switchWeaponsStowOld(weaponSlot, newWeapon);
		}
	}
	private void switchWeaponsStowOld(String weaponSlot, Weapon newWeapon){
		if(equippedWeapon.getSize() <= spaceRemaining()){
			backpack.addItem(equippedWeapon);
			equipment.removeWeapon(weaponSlot);
			equippedWeapon = newWeapon;
		} else {
			StringBuilder str = new StringBuilder();
			str.append("There is not enough room in your ");
			str.append(backpack.name());
			str.append(" to store your ");
			str.append(equippedWeapon.name());
			str.append(" would you like to drop it instead?");
			TextTools.display(str.toString());
			String[] answers = new String[]{"drop " + equippedWeapon.name(), "cancel"};
			int choice = TextTools.questionAsker("", answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				switchWeaponsDropOld(weaponSlot, newWeapon);
			}
		}
	}
	private void switchWeaponsDropOld(String weaponSlot, Weapon newWeapon){
		equippedWeapon = newWeapon;
		equipment.removeWeapon(weaponSlot);
	}
	private void switchWeaponSwapOld(String weaponSlot, Weapon newWeapon){
		equipment.removeWeapon(weaponSlot);
		equipment.addWeapon(weaponSlot, equippedWeapon);
		equippedWeapon = newWeapon;
	}
	
//	public Weapon selectWeapon(String question, int backEnabled){
//		String weaponSlot = selectWeaponSlot(question, backEnabled);
//		if(weaponSlot == null){
//			return null;
//		}
//		return equipment.getWeapon(weaponSlot);
//	}
	
	
	public String selectWeaponSlot(String question, int backEnabled){
		String[][] options = availibleWeapons2dArray();
		String[] answers = getWeaponSelectionChoicesBackEnabled(options);
		int choice = TextTools.questionAsker(question, answers, backEnabled);
		if (choice == 0){
			return null;
		}
		return options[choice - 1][0];
	}
//	private String[] getWeaponSelectionChoicesBackDisabled(String[][] options){
//		String[] answers = new String[options.length];
//		for (int i = 0; i < answers.length; i++){
//			answers[i] = options[i][1] + " [" + options[i][0] + "]";
//		}
//		return answers;
//	}
	private String[] getWeaponSelectionChoicesBackEnabled(String[][] options){
		String[] answers = new String[options.length  + 1];
		for (int i = 0; i < answers.length - 1; i++){
			answers[i] = options[i][1] + " [" + options[i][0] + "]";
		}
		answers[answers.length - 1] = "cancel";
		return answers;
	}
	
	private String[][] availibleWeapons2dArray(){
		
		String[][] selection = new String[equipment.getEquippedWeapons().size()][2];
		int i = 0;
		for (String key : equipment.getEquippedWeapons().keySet()){
			selection[i][0] = key;
			if (equipment.getEquippedWeapons().get(key) == null){
				selection[i][1] = "empty";
			} else {
				selection[i][1] = equipment.getEquippedWeapons().get(key).name();
			}
			i += 1;
		}
		return selection;
	}
	public void tryToEquipClothing(WornItem newClothing){
		WornItem oldClothing = equipment.getClothing(newClothing.slot);
		if(oldClothing == null){
			equipment.equipClothing(newClothing);
			return;
		}
		String question = newClothing.slotIsAlreadyOccupiedString(oldClothing.name());
		String[] answers = new String[]{"drop " + oldClothing.name(), "stow " + oldClothing.name(), "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 1){
			equipClothingDropOld(newClothing);
		} else if (choice == 2){
			equipClothingStowOld(newClothing);
		} else if (choice == 0){
			return; // do nothing
		} else {
			// TODO throw Exception
		}
	}
	
	private void equipClothingStowOld(WornItem newClothing){
		Armor oldArmor = equipment.getArmor(newClothing.slot);
		if((equipment.getClothing(newClothing.slot).getSize() - newClothing.getSize()) <= spaceRemaining()){
			backpack.addItem(equipment.getArmor(newClothing.slot));
			equipment.removeClothing(newClothing.slot);
			equipment.equipClothing(newClothing);
			if(backpack.contains(newClothing)){
				backpack.removeItem(newClothing);
			}
		} else {
			String question = (notEnoughRoomInInventoryForItem(oldArmor.name()));
			String[] answers = new String[]{"drop " + oldArmor.name(), "cancel"};
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				equipClothingDropOld(newClothing);
			}
		}
	}
	private void equipClothingDropOld(WornItem newClothing){
		equipment.removeClothing(newClothing.slot);
		equipment.equipClothing(newClothing);
		if(backpack.contains(newClothing)){
			backpack.removeItem(newClothing);
		}
	}
	public void tryToEquipArmor(Armor newArmor){
		Armor oldArmor = equipment.getArmor(newArmor.slot);
		if(oldArmor == null){
			equipment.equipArmor(newArmor);
			if(backpack.contains(newArmor)){
				backpack.removeItem(newArmor);
			}
			return;
		}
		String question = newArmor.slotIsAlreadyOccupiedString(oldArmor.name());
		String[] answers = new String[]{"drop " + oldArmor.name(), "stow " + oldArmor.name(), "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 1){
			equipArmorDropOld(newArmor);
		} else if (choice == 2){
			equipArmorStowOld(newArmor);
		} else if (choice == 0){
			// do nothing
		} else {
			// TODO throw Exception
		}
	}
	
	private void equipArmorStowOld(Armor newArmor){
		Armor oldArmor = equipment.getArmor(newArmor.slot);
		if((equipment.getArmor(newArmor.slot).getSize() - newArmor.getSize()) <= spaceRemaining()){
			backpack.addItem(equipment.getArmor(newArmor.slot));
			equipment.removeArmor(newArmor.slot);
			equipment.equipArmor(newArmor);
			if(backpack.contains(newArmor)){
				backpack.removeItem(newArmor);
			}
		} else {
			String question = (notEnoughRoomInInventoryForItem(oldArmor.name()));
			String[] answers = new String[]{"drop " + oldArmor.name(), "cancel"};
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				equipArmorDropOld(newArmor);
			}
		}
	}
	private String notEnoughRoomInInventoryForItem(String itemName){
		StringBuilder str = new StringBuilder();
			str.append("There is not enough room in your ");
			str.append(backpack.name());
			str.append(" to store your ");
			str.append(itemName);
			str.append(" would you like to drop it instead?");
			return str.toString();
	}
	
	private void equipArmorDropOld(Armor newArmor){
		equipment.removeArmor(newArmor.slot);
		equipment.equipArmor(newArmor);
		if(backpack.contains(newArmor)){
			backpack.removeItem(newArmor);
		}
	}
	public int getCarriedWeight(){
		updateCarriedWeight();
		return carriedWeight;
	}
	private void updateCarriedWeight(){
		carriedWeight = backpack.getWeight();
		carriedWeight += equipment.getWeight();
		if(equippedWeapon != null){
			carriedWeight += equippedWeapon.getWeight();
		} if (lightSource != null){
			carriedWeight += lightSource.getWeight();
		}
	}
	
	public boolean equipWeapon(String slot, Weapon weapon){
		return equipment.addWeapon(slot, weapon);
	}
	
	public HashMap<String, WornItem> getClothingEquipped(){
		return equipment.getClothingWorn();
	}
	public HashMap<String, Armor> getArmorEquipped(){
		return equipment.getArmorEquipped();
	}
	public HashMap<String, Weapon> getEquippedWeapons(){
		return equipment.getEquippedWeapons();
	}
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("carried weight: ");
		str.append(carriedWeight);
		str.append("\nEquipped Weapon: ");
		str.append(equippedWeapon);
		str.append("\nLight Source: ");
		str.append(lightSource);
		str.append("\n\nItem Container:\n");
		str.append(backpack.toString());
		str.append("\n\nEquipment:\n");
		str.append(equipment.toString());
		return str.toString();
	}
}

