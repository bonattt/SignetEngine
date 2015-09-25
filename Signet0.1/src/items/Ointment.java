package items;

import health.Wound;


public class Ointment extends Item implements FirstAidItem {

	private Wound woundTreated;
	private double healingMultiplier;
	private double infectionMultiplier;	// applies to protection from infection and to speeding recovery from infections.
	
	public Ointment(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
	}
	public boolean passTime(int timePassed, double healingFactor, Wound wound){
		// TODO implement passTime		
		return false;
	}

	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub

	}
	
	public double getHealingRateAdjustment(){
		return healingMultiplier;
	}
	public double getInfectionMultiplier(){
		return infectionMultiplier;
	}

}
