package items;

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
}
