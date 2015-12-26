package items;

import inventory.Inventory;

import java.io.PrintWriter;
import java.util.ArrayList;

import misc.TextTools;
import creatures.Creature;

public abstract class Weapon extends Item {
	
	private int might, accuracy, weaponType;
	
	public Weapon(int size, int wt, int dur, int hard, int dam,
			String name, String description,
			int might, int accuracy, int weaponType) {
		super(size, wt, dur, hard, dam, name, description);
		this.might = might;
		this.accuracy = accuracy;
		this.weaponType = weaponType;
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
	
	public void saveBaseWeaponStats(PrintWriter writer) {
		writer.println(might);
		writer.println(accuracy);
		writer.println(weaponType);
	}
	
	public int getWeaponType() {
		return weaponType;
	}
	
	public int getMight() {
		return might;
	}
	
	public int getAccuracy() {
		return accuracy;
	}

	@Override
	public void useFromInventory(Inventory inv, Creature player) {
		String question = "What will you do with your " + this.name();
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
				TextTools.display("There is no room in your " + this.name() + " in you inventory");
			}
		} else if (choice == 2) {		// discard
			inv.getEquipment().removeWeapon(slot);
		} else {
			// ERROR
		}
	}
}
