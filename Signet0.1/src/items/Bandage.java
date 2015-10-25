package items;

import inventory.Inventory;
import creatures.Creature;
import health.Wound;

public class Bandage extends Item implements FirstAidItem{
	
	private Wound woundTreated;
	private double healingMultiplier, infectionMultiplier;

	private static final int SIZE = 1;
	private static final int WEIGHT = 1;
	private static final int DURABILITY = 5;
	private static final int HARDNESS = 0;
	private static final int DAMAGE = 0;

	public Bandage(double healingRate, double infectionRate, Wound wound, String name) {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE);
		healingMultiplier = healingRate;
		infectionMultiplier = infectionRate;
		woundTreated = wound;
		this.name = name;
	}
	public Bandage(double healingRate, double infectionRate, Wound wound) {
		this(healingRate, infectionRate, wound, "bandage");
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
		System.out.println("Bandage should not be used while equipped");
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
