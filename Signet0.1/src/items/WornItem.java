package items;

import misc.TextTools;
import inventory.Gear;
import inventory.Inventory;
import inventory.ItemContainer;
import creatures.Creature;

public abstract class WornItem extends Item {

	public String slot;
	
	public WornItem(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
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
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		String question = "What would you like to do with the " + name;
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
		str.append(name);
		str.append(" because your ");
		str.append(slot);
		str.append(" slot is already occupied by ");
		str.append(oldClothingName);
		str.append(". What will you do?");
		return str.toString();
	}
}
