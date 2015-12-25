package items;

import inventory.Gear;
import inventory.Inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import creatures.Creature;
import misc.TextTools;

public class Weapon extends Item {

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
	public Weapon(int size, int weight, int durability, int hardness, int damage,
			int weaponType, int parry, int accuracy, int might) {
		super(size, weight, durability, hardness, damage);
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
	public void saveToFile(PrintWriter writer) {
		writer.println("weapon");
		saveBaseStats(writer);
		writer.println(weaponType);
		writer.println(parry);
		writer.println(accuracy);
		writer.println(might);
	}
//	private void saveDescriptionToFile(PrintWriter writer){
//		String[] desc = description.split("\n");
//		writer.println(desc.length);
//		for (int i = 0; i < desc.length; i++) {
//			writer.println(desc[i]);
//		}
//	}
	public static Weapon loadAlpha0_1(Scanner scanner) {
		int size, weight, durability, hardness, damage, weaponType, parry, accuracy, might;
		String name = scanner.nextLine();
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		String description = Item.loadItemDescriptionAlpha0_1(scanner);
		weaponType = scanner.nextInt();
		parry = scanner.nextInt();
		accuracy = scanner.nextInt();
		might = scanner.nextInt();
		Weapon weapon =  new Weapon(size, weight, durability, hardness, damage,
				weaponType, parry, accuracy, might);
		weapon.name = name;
		weapon.description = description;
		return weapon;
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
	public void useFromInventory(Inventory inv, Creature player) {
		String question = "What will you do with your " + name;
		String[] answers = new String[]{"carry", "equip", "discard", "inspect", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		} else if (choice == 1){
			tryToCarry(inv, player);
		} else if (choice == 2){ 
			tryToEquip(inv, player);
		} else if (choice == 3){
			inv.discardItem(this);
		} else if (choice == 4){
			this.inspect(player);
		} else {
			System.out.println("ERROR");
		}
	}
	private void tryToCarry(Inventory inv, Creature player){
		if(inv.getWeapon() == null){
			if(inv.tryToCarryWeapon(inv, this)){
				inv.discardItem(this);
			}
		}
	}
	private void tryToEquip(Inventory inv, Creature player){
		System.out.println("UNIMPLEMENTED - weapon.\"tryToEquip\"(...)");
	}
	@Override
	public boolean equals(Item item){
		Weapon weapon;
		try {
			weapon = (Weapon) item;
		} catch (ClassCastException e) {
			return false;
		}
		return (super.equals(weapon)) &&
				(weapon.name.equals(name)) &&
				((weapon.getAccuracy() == getAccuracy())) &&
				((weapon.getMight() == getMight())) &&
				((weapon.getParry() == weapon.getParry())) &&
				((weapon.getSize() == weapon.getSize())) && 
				((weapon.getWeaponType() == getWeaponType())) &&
				((weapon.getWeight() == getWeight())) &&
				(weapon.description.equals(description));
	}
}
