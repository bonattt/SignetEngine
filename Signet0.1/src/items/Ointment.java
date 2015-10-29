package items;

import misc.TextTools;
import inventory.Inventory;
import creatures.Creature;
import health.Wound;


public class Ointment extends FirstAidItem {

	private Wound woundTreated;
	private double healingMultiplier, infectionMultiplier, damageMultiplier;

	private static final int SIZE = 1;
	private static final int WEIGHT = 1;
	private static final int DURABILITY = 5;
	private static final int HARDNESS = 0;
	private static final int DAMAGE = 0;

	private static final double INFECTION_RATE = .5;
	private static final double DAMAGE_MULTIPLIER = .5;
	private static final double HEALING_RATE = 1.2;
	

	public Ointment() {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, HEALING_RATE, DAMAGE_MULTIPLIER, HEALING_RATE);
		this.name = "Simple Ointment";
	}
	public Ointment(double healingRate, double infectionRate, double damageMultiplier, String name) {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, healingRate, infectionRate, damageMultiplier);
		this.name = name;
	}
	public boolean passTime(int timePassed, double healingFactor, Wound wound){
		// TODO implement passTime		
		return false;
	}
	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}
	@Override
	public void treatWound(Inventory inv, Wound injury) {
		if (injury.isBandaged()){
			TextTools.display("That wound is already bandaged");
			return;
		} else if (injury.hasOintment()) {
			TextTools.display("That wound has already been treated with ointment");
			return;
		}
		inv.discardItem(this);
		injury.setTreatment(this);
		TextTools.display("you treated the wound with a " + name);
	}
}
