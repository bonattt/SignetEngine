package items;

import java.io.PrintWriter;
import java.util.Scanner;

import creatures.Creature;
import inventory.Inventory;
import inventory.InventoryException;
import inventory.ItemContainer;
import misc.DiceRoller;
import misc.TextTools;

public abstract class Item {
	
	private int size;		// measured in ??
	private int weight;		// measured in grams
	private int durability; // how much damage the item can take before breaking.
	private int hardness;	// items resistance to damage.
	private int damage;		// damage dealt to the item's durability.
	private int[] args;
	private String name;
	private String description;
	
	public Item (int size, int wt, int dur, int hard, int dam, String name, String description) {
		this.size = size;
		weight = wt;
		durability = dur;
		hardness = hard;
		damage = dam;
		this.name = name;
		this.description = description;
	}
	
	public String name() {
		return name;
	}
	public String description() {
		return description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Item)) {
			return false;
		}
		Item item = (Item) o;
		return (this.size == item.size) &&
				(this.weight == item.weight) &&
				(this.durability == item.durability) &&
				(this.hardness == item.hardness) &&
				(this.name.equals(item.name)) &&
				(this.description.equals(item.description));
	}
	
	public void inspect(Creature player) {
		TextTools.display(description);
		// TODO add a pause in text reading here.
	}
	public abstract void useFromInventory(Inventory inv, Creature player)
			throws InventoryException ;
	public abstract void handleUseWhileEquipped(Inventory inv, Creature player, int choice) 
			throws InventoryException;
	public abstract void saveToFile(PrintWriter writer);
	public static Item loadAlpha0_1(Scanner scanner) {
		String itemType = scanner.nextLine();
		return loadAlpha0_1(scanner, itemType);
	}
		
	public static Item loadAlpha0_1(Scanner scanner, String itemType) {
		if (itemType.equals("armor")) {
			return Armor.loadAlpha0_1(scanner);
		} else if (itemType.equals("bandage")) {
			return Bandage.loadBandageAlpha0_1(scanner);
		} else if (itemType.equals("light source")) {
			return LightSource.loadLightSourceAlpha0_1(scanner);
		} else if (itemType.equals("ointment")) {
			return Ointment.loadOintmentAlpha0_1(scanner);
		} else if (itemType.equals("ranged weapon")) {
			return RangedWeapon.loadRangedWeaponAlpha0_1(scanner);
		} else if (itemType.equals("melee weapon")) {
			return MeleeWeapon.loadMeleeWeaponAlpha0_1(scanner);
		} else if (itemType.equals("worn item")) {
			return WornItem.loadWornItemAlpha0_1(scanner);
		} else {
			System.out.println("Load Alpha 0.1, unrecognized item type '" + itemType + "'\n cannot load item");
			return null;
		}
	}
	private void saveDescriptionToFile(PrintWriter writer) {
		String[] desc = description.split("\n");
		for (int i = 0; i < desc.length; i++) {
			writer.println(desc[i]);
		}
		writer.println("end description");
	}
	public void saveBaseStats(PrintWriter writer) {
		writer.println(name);
		saveDescriptionToFile(writer);
		writer.println(size);
		writer.println(weight);
		writer.println(durability);
		writer.println(hardness);
		writer.println(damage);
	}
	
	public static String loadItemDescriptionAlpha0_1(Scanner scanner) {
		StringBuilder desc = new StringBuilder();
		// BANDAIDE FIX - the phantom blank line has showed up here again.
		// an extra line "" is being read from the file where one doesn't exist.
		String current = scanner.nextLine();
		boolean first = true;
		while(! current.equals("end description")) {
			if (!first) {
				desc.append("\n");
			}
			desc.append(current);
			current = scanner.nextLine();
			first = false;
		}
		return desc.toString();
	}
	
	public String toString(){
		return "Item: " + name;
	}
	
	public void useWhileEquipped(Inventory inv, Creature player) throws InventoryException {
		String question = "What would you like to do withj the " + name + " you have equipped?";
		String[] answers = new String[]{"stow", "discard", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice != 0){
			handleUseWhileEquipped(inv, player, choice);
		}
	}
	public void useFromContainer(Inventory inv, Creature player, ItemContainer container) {
		String question = "What would you like to do with the " + name;
		String[] answers = new String[]{"pick-up", "discard", "inspect", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		} else if (choice == 1) {
			if(inv.spaceRemaining() >= this.getSize()){
				inv.store(this);
				container.removeItem(this);
			} else {
				TextTools.display("You do not have enough room for that");
			}
		} else if (choice == 2) {
			container.removeItem(this);
		} else if (choice == 3) {
			this.inspect(player);
		} else {
			// TODO throw an Exception
		}
		 
	}
	
	public boolean canBeUsedInExplore(){
		return true;
	}
	public boolean isExpendible(){
		return false;
	}
	public boolean canBeUsedInCombat(){
		return false;
	}
	public boolean isWeapon(){
		return false;
	}
	public boolean isRangedWeapon(){
		return false;
	}
	public boolean isMeleeWeapon(){
		return false;
	}
	public boolean isDepletable(){
		return false;
	}
	public boolean isClothing(){
		return false;
	}
	public boolean isArmor(){
		return false;
	}
	public boolean isEquipment(){
		return false;
	}
	public boolean isLightSource(){
		return false;
	}
	public boolean isFirstAid(){
		return false;
	}
	
	public int getSize() {
		return size;
	}
	public int getWeight() {
		return weight;
	}
	public int getDurability() {
		return durability;
	}
	public int getHardness() {
		return hardness;
	}
	public int getDamage() {
		return damage;
	}
	public String checkDamage(){
		return "this item has " + damage + " damage.";
	}
	
	public boolean resistDamage(int damage, int armorPiercing) {
		//TODO handle items taking damage 
		args = new int[]{durability, 1};
		// run special abilities.
		int dicePool = (int) args[0];
		double multiplier = (double) args[1];
		int hits = DiceRoller.makeRoll(dicePool)[0];
		int damageDealt = (int) Math.ceil(damage - hits);
		if (damageDealt < 0){
			damageDealt = 0;
		}
		damage += damageDealt;
		if (damage >= durability){
			itemBreaks();
			return true;
		}
		return false;
	}
	public boolean baseEqual(Item item) {
		return (item.getSize() == size) &&
				(item.getWeight() == weight) &&
				(item.getDurability() == durability) &&
				(item.getHardness() == hardness) &&
				(item.getDamage() == damage) &&
				(item.name.equals(name)) &&
				(item.description.equals(description));
	}
	
	public void itemBreaks(){
		System.out.println("UNIMPLEMENTED [Item].itemBreaks()");
	}
}
