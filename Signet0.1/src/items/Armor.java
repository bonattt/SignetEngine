package items;

import inventory.Gear;
import inventory.Inventory;

import java.util.HashMap;

import misc.TextTools;
import creatures.Creature;

public abstract class Armor extends Item {
	
	private static final double ARMOR_INDIRECT_DAMAGE_REDUCTION = 2/3;
	// damage recieved by armor while blocking attacks is reduced.

	private HashMap<Integer, Integer> damageResistance;
	private HashMap<Integer, Integer> typeConversion;
	public String slot;
	
	public Armor(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
	}
	@Override
	public boolean isArmor(){
		return true;
	}
	@Override
	public boolean isEquipment(){
		return false;
	}
	public void initializeArmorStats(String armorSlot, HashMap<Integer, Integer> resistances, HashMap<Integer, Integer> typeconversion){
		damageResistance = resistances;
		slot = armorSlot;
		typeConversion = typeconversion;
	}
	
	public void modifyStats(int[] stats){
		// by default, does nothing.
	}
	
	/**
	 * mutates an array containing the damage might and type to reflect any protection this armor would provide.
	 * @param dmg
	 */
	public void modifyDamage(int[] dmg){
		int might = dmg[0];
		
		if (damageResistance.containsKey(dmg[1])){
			dmg[0] = (might - damageResistance.get(dmg[1]));
		}
		if (typeConversion.containsKey(dmg[1])){
			dmg[1] = typeConversion.get(dmg[1]);
		}
		
		if ((int) dmg[0] <= 0){
			dmg[0] = 0;
			might *= 2;
		}
		// when being hit
		resistDamage((int)(might*ARMOR_INDIRECT_DAMAGE_REDUCTION), 0);
	}

	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		if(choice == 1) {				// Stow
			if(inv.spaceRemaining() >= getSize()){
				inv.getEquipment().removeArmor(slot);
				inv.store(this);
			} else {
				TextTools.display("There is no room in your " + name + " in you inventory");
			}
		} else if (choice == 2) {		// discard
			inv.getEquipment().removeArmor(slot);
		} else {
			// ERROR
		}
	}
	
	@Override
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		String question = "What would you like to do with the " + name;
		String[] answers = new String[]{"equip", "discard", "inspect", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		} else if (choice == 1){
			inv.tryToEquipArmor(this);
		} else if (choice == 2) {
			inv.discardItem(this);
		} else if (choice == 3) {
			inspect(character);
		}
	}
//	private void temp(Inventory inv){
//		Gear equipment = inv.getEquipment();
//		if (equipment.getArmorEquipped().get(this.slot) == null){
//			equipment.equipArmor(this);
//			return;
//		}
//		Armor oldArmor = equipment.getArmorEquipped().get(this.slot);
//		String question = slotIsAlreadyOccupiedString(oldArmor.name);
//		String[] answers = new String[]{"drop " + oldArmor.name,
//										"stow " + oldArmor.name,
//										"leave " + name + " in inventory"};
//		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
//		if(choice == 0) {
//			return;
//		}
//		else if (choice == 1) {
//			equipment.removeArmor(slot);
//			equipment.equipArmor(this);
//		} else if (choice == 2){
//			if (inv.spaceRemaining() >= this.getSize()){
//				inv.store(oldArmor);
//				inv.getEquipment().removeArmor(slot);
//				inv.getEquipment().equipArmor(this);
//			} else {
//				int secondChoice = TextTools.questionAsker("There is no room to stow " + oldArmor.name,
//						new String[]{"drop " + oldArmor.name, "cancel"},
//						TextTools.BACK_ENABLED);
//				if (secondChoice == 1){
//					
//				}
//			}
//		}
//	}
	
	public String slotIsAlreadyOccupiedString(String oldArmorName){
		StringBuilder str = new StringBuilder();
		str.append("You cannot equip your " );
		str.append(name);
		str.append(" because your ");
		str.append(slot);
		str.append(" slot is already occupied by ");
		str.append(oldArmorName);
		str.append(". What will you do?");
		return str.toString();
	}
}
 