package inventory;

import java.util.ArrayList;
import java.util.HashMap;

import misc.TextTools;
import items.*;

public class Inventory {

	private ItemContainer backpack;
	private Gear equipment;
	private LightSource lightSource;
	private Weapon equippedWeapon;
	private int carriedWeight;
	private boolean weightUpToDate;
	
	private int weaponSlots, armorSlots, clothingSlots;
	
	
	public Inventory (){
		equipment = new Gear();
		backpack = new Bag();
		equippedWeapon = null;
//		initializeEquipmentSlots(equippedItems);
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
	
	
	public void initializeScifiEquipmentSlots(Gear gear){
		gear.createArmorSlot("armor");
		gear.createArmorSlot("helmet");
		gear.createWeaponSlot("holster");
		gear.createWeaponSlot("boot");
		gear.createClothingSlot("shirt");
		gear.createClothingSlot("pants");
		gear.createClothingSlot("hat");
		weaponSlots = 2;
		armorSlots = 2;
		clothingSlots = 3;
	}
	/**
	 * This method allows the player to access items in their inventory during explore mode.
	 * This method starts off by prompting the player to select what part of the inventory they would
	 *  like to access (armor, weapons, 
	 */
	public void accessInventoryDuringExplore(){
		String question = "What part of the inventory would you like to access?";
		String[] answers = new String[]{"backpack", "equipment", "exit inventory"};
		while(true) {
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if(choice == 0){
				break;
			}
			if (choice == 1){
				accessBackpackDuringExplore();
				continue;
			}
			if (choice == 2){
				accessEquipmentDuringExplore();
			}
		}
	}
	private void accessBackpackDuringExplore(){
		String question = "What kind of items are you looking for?";
		String[] answers = new String[]{"anything", "first-aid", "weapons", "armor", "clothing", "exit backpack"};
		
		while(true) {
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if(choice == 0){
				break;
			}
			if (choice == 1){
				// tbd ANYTHING
				continue;
			}
			if (choice == 2){
				// tbd MEDICUNE
			}
			if (choice == 3){
				// tbd WEAPONS
			}
			if (choice == 4){
				// tbd ARMOR
			}
			if (choice == 5){
				// tbd CLOTHING
			}
		}
	}
	private static void accessBackpackSelection(ArrayList<Item> itemsSelected){
		String question = "which item would you like to use?";
		String[] answers = new String[itemsSelected.size() + 1];
		for (int i = 0; i < answers.length - 1; i++){
			answers[i] = itemsSelected.get(i).name;
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (choice == 0){
			return;
		}
		itemsSelected.get(choice - 1);
	}
	private void accessWeaponsInBackpackDuringExplore(){
		
	}
	private void accessArmorInBackpackDuringExplore(){
		
	}
	private void accessClothingInBackpackDuringExplore(){
		
	}
	private void accessEquipmentDuringExplore(){
		String question = "What kind of equipment would you like you access?";
		String[] answers = new String[]{"weapons", "armor", "clothing", "exit equipment"};
		while(true) {
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if(choice == 0){
				break;
			}
			if (choice == 1){
				accessEquippedWeaponsDuringExplore();
				continue;
			}
			if (choice == 2){
				accessEquippedArmorDuringExplore();
				continue;
			}
			if (choice == 3){
				accessEquippedClothingDuringExplore();
				continue;
			}
		}
	}
	private void accessEquippedWeaponsDuringExplore(){
		
	}
	private void accessEquippedArmorDuringExplore(){
		
	}
	private void accessEquippedClothingDuringExplore(){
		
	}
	
	
	public ArrayList<CombatItem> listEquippedWeapons(){
		ArrayList<CombatItem> list = new ArrayList<CombatItem>();
		// TODO 
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
		String question = "You are alrady carrying a " + equippedWeapon.name + " what would you like to do with it?";
		String[] answers = new String[]{"swap " + equippedWeapon.name + " with " + newWeapon.name,
											"drop " + equippedWeapon.name,
											"stow " + equippedWeapon.name + " in " + backpack.name,
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
		String question = "Your " + equippedWeapon.name + " is too big to be equipped. What will you do?";
		StringBuilder stow = new StringBuilder();
		stow.append("stow ");
		stow.append(equippedWeapon.name);
		stow.append(" in ");
		stow.append(backpack.name);
		String[] answers = new String[]{"drop " + equippedWeapon.name,
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
			str.append(backpack.name);
			str.append(" to store your ");
			str.append(equippedWeapon.name);
			str.append(" would you like to drop it instead?");
			TextTools.display(str.toString());
			String[] answers = new String[]{"drop " + equippedWeapon.name, "cancel"};
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
				selection[i][1] = equipment.getEquippedWeapons().get(key).name;
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
		String question = newClothing.slotIsAlreadyOccupiedString(oldClothing.name);
		String[] answers = new String[]{"drop " + oldClothing.name, "stow " + oldClothing.name, "cancel"};
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
			String question = (notEnoughRoomInInventoryForItem(oldArmor.name));
			String[] answers = new String[]{"drop " + oldArmor.name, "cancel"};
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
			return;
		}
		String question = newArmor.slotIsAlreadyOccupiedString(oldArmor.name);
		String[] answers = new String[]{"drop " + oldArmor.name, "stow " + oldArmor.name, "cancel"};
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
			String question = (notEnoughRoomInInventoryForItem(oldArmor.name));
			String[] answers = new String[]{"drop " + oldArmor.name, "cancel"};
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				equipArmorDropOld(newArmor);
			}
		}
	}
	private String notEnoughRoomInInventoryForItem(String itemName){
		StringBuilder str = new StringBuilder();
			str.append("There is not enough room in your ");
			str.append(backpack.name);
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
	
	public boolean equipWeapon(String slot, Weapon weapon){
		return equipment.addWeapon(slot, weapon);
	}
	
	public HashMap<String, WornItem> getClothingEquipped(){
		return equipment.getClothingEquipped();
	}
	public HashMap<String, Armor> getArmorEquipped(){
		return equipment.getArmorEquipped();
	}
	public HashMap<String, Weapon> getEquippedWeapons(){
		return equipment.getEquippedWeapons();
	}
	
}

