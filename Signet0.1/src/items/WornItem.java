package items;

import java.io.PrintWriter;
import java.util.Scanner;

import misc.TextTools;
import inventory.Inventory;
import creatures.Creature;

public class WornItem extends Item {

	private String slot;
	
	public WornItem(int size, int wt, int dur, int hard, int dam, String clothingSlot,
			String name, String descrip) {
		super(size, wt, dur, hard, dam, name, descrip);
		this.slot = clothingSlot;
	}
	
	public String getSlot() {
		return slot;
	}

	@Override
	public boolean isClothing(){
		return true;
	}
	@Override
	public boolean isEquipment(){
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (! (o instanceof WornItem)) {
			return false;
		}
		WornItem item = (WornItem) o;
		return (item.name().equals(this.name())) &&
				(item.slot.equals(this.slot)) &&
				(item.description().equals(this.description())) &&
				(item.getSize() == getSize()) &&
				(item.getWeight() == getWeight()) &&
				(item.getDurability() == getDurability()) &&
				(item.getHardness() == getHardness()) &&
				(item.getDamage() == getDamage());
	}
	
	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("worn item");
		saveBaseStats(writer);
		writer.println(slot);
	}
	
	public static WornItem loadWornItemAlpha0_1(Scanner scanner) {
		int size, weight, durability, hardness, damage;
		String name = scanner.nextLine();
		String desc = Item.loadItemDescriptionAlpha0_1(scanner);
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		scanner.nextLine(); // realign the scanner to read strings after .nextInt()
		String slot = scanner.nextLine();
		WornItem clothing = new WornItem(size, weight, durability, hardness, damage, slot, name, desc);
		return clothing;
	}
	
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		if(choice == 1) {				// Stow
			if(inv.spaceRemaining() >= getSize()){
				inv.getEquipment().removeClothing(slot);
				inv.store(this);
			} else {
				TextTools.display("There is no room in your " + this.name() + " in you inventory");
			}
		} else if (choice == 2) {		// discard
			inv.getEquipment().removeClothing(slot);
		} else {
			// ERROR
		}
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		String question = "What would you like to do with the " + this.name();
		String[] answers = new String[]{"equip", "discard", "inspect", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		} else if (choice == 1){
			inv.tryToEquipClothing(this);
		} else if (choice == 2) {
			inv.discardItem(this);
		} else if (choice == 3) {
			inspect(character);
		}
	}
	
	public void modifyStats(int[] stats){
		// by default, does nothing.
	}
	public String slotIsAlreadyOccupiedString(String oldClothingName){
		StringBuilder str = new StringBuilder();
		str.append("You cannot equip your " );
		str.append(this.name());
		str.append(" because your ");
		str.append(slot);
		str.append(" slot is already occupied by ");
		str.append(oldClothingName);
		str.append(". What will you do?");
		return str.toString();
	}
}
