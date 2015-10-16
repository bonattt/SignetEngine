package items;

import inventory.Gear;
import inventory.Inventory;
import creatures.Creature;
import health.Wound;

public class Bandage extends Item implements FirstAidItem{
	
	private Wound woundTreated;
	private double healingMultiplier, infectionMultiplier;

	public Bandage(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
	}

	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}

	public boolean needsToBeChanged(){
		// TODO implement needs to be changed on Bandage.
		return false;
	}

	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		System.out.println("Ammo should not be used while equipped");
		// does nothing
	}
	public boolean passTime(int timePassed, double healingFactor, Wound injury) {
		// TODO Auto-generated method stub
		return false;
	}
	public double getHealingRateAdjustment(){
		return healingMultiplier;
	}
	public double getInfectionMultiplier(){
		return infectionMultiplier;
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		throw new Exception("use while exploring unimplemented for this class");
	}
}
