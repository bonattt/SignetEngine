package items;

import inventory.Inventory;
import inventory.InventoryException;

import java.io.PrintWriter;
import java.util.ArrayList;

import misc.DeathException;
import misc.DiceRoller;
import misc.TextTools;
import creatures.Creature;

public abstract class Weapon extends Item {
	
	private int weaponType;
	private String weaponSkill;
	private WeaponDamage wpnDmg;
	
	public Weapon(int size, int wt, int dur, int hard, int dam,
			String name, String description,
			int might, int accuracy, int weaponType, int damageType) {
		super(size, wt, dur, hard, dam, name, description);
		wpnDmg = new WeaponDamage(might, accuracy, damageType, description);
//		this.might = might;
//		this.accuracy = accuracy;
//		this.weaponType = weaponType;
//		this.damageType = damageType;
		this.weaponSkill = "";
	}
	
	public Weapon(int[] itemStats, String name, String description, int weaponType, WeaponDamage wpnDmg) {
		super(itemStats[0], itemStats[1], itemStats[2], itemStats[3], itemStats[4], name, description);
		this.wpnDmg = wpnDmg;
		this.weaponSkill = "";
	}
	
	public int getDamageType(int damageDealt) {
		return wpnDmg.damageType;
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
	
	public void dealDamage(Creature attacker, Creature target, int netHits) throws DeathException {
		wpnDmg.dealDamage(attacker, target, netHits);
	}
	
	
	public void makeAttack(Creature attacker, Creature target) throws DeathException {
		int netHits = checkIfAttackHits(attacker, target);
		dealDamage(attacker, target, netHits);
	}
	
	public int checkIfAttackHits(Creature attacker, Creature target) {
		int[] defenseTest = target.makeAttributeTest(new String[]{"agl", "rec"});
		int[] netHitsAttacker = attacker.makeTest(weaponSkill, defenseTest[0]);
		return netHitsAttacker[0];
	}
	
	public void saveBaseWeaponStats(PrintWriter writer) {
		writer.println(wpnDmg.might);
		writer.println(wpnDmg.accuracy);
		writer.println(weaponType);
		writer.println(wpnDmg.damageType);
	}
	
	public int getWeaponType() {
		return weaponType;
	}
	
	public int getMight() {
		return wpnDmg.might;
	}
	
	public int getAccuracy() {
		return wpnDmg.accuracy;
	}

	@Override
	public void useFromInventory(Inventory inv, Creature player)  throws InventoryException {
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
	public boolean damageTypesEqual(Weapon wep) {
		return wep.wpnDmg.damageType == this.wpnDmg.damageType;
	}
	
	private void tryToCarry(Inventory inv, Creature player) throws InventoryException  {
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
