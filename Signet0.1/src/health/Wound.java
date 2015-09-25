package health;

import java.util.Random;

import injuries.BluntWound;
import injuries.BurnWound;
import injuries.PiercingWound;
import injuries.SlashingWound;
import items.Bandage;
import items.Ointment;
import misc.DamageType;

public class Wound {
	
	protected static final int INFECTION_FREQUENCY = 24000; // 24 hours
	
	
	public String name;
	
	private int 
	recoveryClock,			// time passed since wound was dealt or since wound reached current severity
	bleedingClock,			// time left until bleeding damage is taken again.
	infectionClock;			// time left until infection may occur again.
	
	private double[] pain;	// pain inflicted by the wound. 
	
	private int[]
	recoveryTime,			// time required for the wound to heal and reduce in severity by 1.
	chanceOfInfection,		// percent chance the wound will become infected each day.
	damageRate,				// the frequency at which damage is dealt by this wound.
	instantHealthDamage,	// instant health damage applied when the wound is recieved (only if the wound is top level)
	instantStun,			// stun damage instantly inflicted by the wound.
	instantFatigue,			// fatigue damage instantly inflicted by the wound.
	healthDamage,			// health damage dealt at a constant rate.
	stunDamage,				// stun damage dealt at a constant rate.
	fatigueDamage,			// fatigue caused at a constant rate 
	crippling;				// this )
	
	private Ointment medication;
	private Bandage bandage;
	private Infection infection;
	private BodyPart location;
	private int damageType;
	private int severity, originalSeverity;	
	/*
	 * wound severity by number:
	 * 1 - trivial wound:	(ie. a paper cut)
	 * 2 - minor wound:		none threatening, but hurts a little. (ie. a minor cut)
	 * 3 - wound:			A serious wound. Not life threatening by itself, and medical treament is not required, but it is recommended.
	 * 4 - nasty wound:		This wound requires medical attention, but not urgently. Becomes life threatening if left un-treated.
	 * 5 - severe wound:	This wound requires immediate medical attention, or it will become life threatening.
	 * 6 - terminal wound:	unless treated immediately, will lead to death within minutes (ie. evisceration) 
	 * 7 - instant death:	(ie. broken neck, crushed skull, beheading)
	 */	
	public Wound(int severity, int dt, BodyPart woundLocation, int[] health, int[] stun, int[] fatigue, int[] rate,
			int[] instHealth, int[] instStun, int[] instFatigue, int[] healTime, int[] infection, double[] pain, int[] cripple){
		if (severity > 7){
			throw new IllegalArgumentException("you cannot have a wound severity higher than 7");
		}
		this.severity = severity;
		originalSeverity = severity;
		damageType = dt;
		location = woundLocation; 
		
		healthDamage = health;
		stunDamage = stun;
		fatigueDamage = fatigue;
		damageRate =  rate;
		instantHealthDamage = instHealth; 
		instantStun = instStun;
		instantFatigue = instFatigue;
		recoveryTime = healTime;
		chanceOfInfection = infection;
		this.pain = pain;
		crippling = cripple;
		randomizeHealingTimes();
				
		woundLocation.addNewWound(this);
	}
	private void randomizeHealingTimes(){
		Random r = new Random();
		for (int i = 0; i < recoveryTime.length; i++){
			double numb = (r.nextDouble() / 2) + .75; // random number from .75 to 1.25
			recoveryTime [i] *= numb;
		}
	}

	public int getCurrentSeverity(){
		return severity;
	}
	public int[] getInstantDamage(){
		int healthDamage = (int) (instantHealthDamage[severity - 1] * location.damageMultiplier);
		int stunDamage = (int) (instantStun[severity - 1] * location.damageMultiplier);
		int fatigueDamage = (int) (instantFatigue[severity - 1] * location.damageMultiplier);
		return new int[] {healthDamage, stunDamage, fatigueDamage};
	}	
	public double getPain(){
		return pain[severity - 1];
	}
	/**
	 * 
	 * @param damage
	 * @param dt
	 * @param woundLocation
	 * @return
	 */
	public static int[] addNewWound(int damage, int dt, BodyPart woundLocation){
		if (dt == DamageType.SLASHING){
			Wound w = new SlashingWound(damage, woundLocation);
			return w.getInstantDamage();
		} else if (dt == DamageType.BLUNT) {
			Wound w = new BluntWound(damage, woundLocation);
			return w.getInstantDamage();
		} else if (dt == DamageType.PIERCING) {
			Wound w = new PiercingWound(damage, woundLocation);
			return w.getInstantDamage();
		} else if (dt == DamageType.FIRE) {
			Wound w = new BurnWound(damage, woundLocation);
			return w.getInstantDamage();
		} else {
			throw new IllegalArgumentException("That damage type has not been implemented yet!!");
		}
	}
	public void setTreatment(Ointment arg){
		medication = arg;
	}
	public boolean isBandaged(){
		if (bandage == null){
			return false;
		}
		return true;
	}
	public void setBandage(Bandage arg) {
		bandage = arg;
	}
	public int getCrippling(){
		return crippling[severity - 1];
	}
	
	/**
	 * this method returns a less severe version of the wound (or null if the wound is completely recovered)
	 * this new wound should be added to the injury in place of this wound.
	 * @return a less severe wound.
	 */
	public Wound recoverFrom(){
		// TODO implement.
		return this;
	}
	/**
	 * returns an int reflecting how much time is left before the wound heals.
	 * A nonnegative result means the wound should "recover" to a less severe wound,
	 * and the less severe wound should have the number returned as time already spent in recovery.
	 * @return
	 */
	public boolean isRecovered(){
		return (severity <= 0);
	}
	/**
	 * This method passes time on the injury, recording damage caused by bleeding from the wound, and also natural healing to the wound.
	 * This method calls passTime on the infection, medication, and bandage applied to the wound, if those fields are non-null.
	 * This method returns a double array representing damage taken equal to {physicalDamage, stunDamage, fatigueDamage}
	 * @param timePassed
	 * @param healingFactor
	 * @param resting
	 * @return
	 */
	public double[] passTime(int timePassed, double healingFactor, boolean resting){
		double[] damage;
		double infectionRate = chanceOfInfection[severity - 1];
		
		if (resting){
			healingFactor *= 1.1;
		}
		
		damage = dealDamageOverTime(timePassed);
		healWoundOverTime(timePassed, healingFactor);
		
		if (infection != null){
			healingFactor *= infection.getHealingAdjustment();
			double[] infectionDamage = infection.passTime(timePassed, healingFactor, this);
			if(infection.isRecovered(this)){
				infection = null;
			}
			damage[0] += infectionDamage[0];
			damage[1] += infectionDamage[1];
			damage[2] += infectionDamage[2];
		} else {
			infectionClock += timePassed;
			// integer division, truncates to 0 checks to avoid infection if clock < 1 day
			int numbChecks = infectionClock / INFECTION_FREQUENCY; 
			for (int i = 0; i < infectionClock; i++){
				// TODO avoid Infection
			}
		}
		
		if (bandage != null){
			healingFactor *= bandage.getHealingRateAdjustment();
			infectionRate *= bandage.getInfectionMultiplier();
			// TODO bandage.passTime(timePassed, healingFactor, this);
			if (bandage.needsToBeChanged()){
				System.out.println("your bandage needs to be changed");
			}
		}
		
		if (medication != null){
			healingFactor *= medication.getHealingRateAdjustment();
			infectionRate *= medication.getInfectionMultiplier();
//			if(!medication.passTime(timePassed, healingFactor, this)){
//				medication = null;
//			}
		}
		return damage;
	}
	private double[] dealDamageOverTime(int timePassed){
		double[] damage = new double[3];
		timePassed += bleedingClock;
		int damageCount = timePassed / damageRate[severity - 1];	// int division, gets completed number of damage increments
		bleedingClock = timePassed % damageRate[severity - 1];		// gets remainder and stores it in bleedingClock
		damage[0] = healthDamage[severity-1] * damageCount;
		damage[1] = stunDamage[severity-1] * damageCount;
		damage[2] = fatigueDamage[severity-1] * damageCount;
		return damage;
	}
	public void healWoundOverTime(int timePassed, double healingFactor){
		recoveryClock += timePassed * healingFactor;
		while(recoveryClock > recoveryTime[severity - 1]){
			recoveryClock -= recoveryTime[severity - 1];
			severity--;
			if (severity <= 0){
				break;
			}
		}
	}
	
	public void avoidGettingInfected(Infection infection){
		// TODO implement this
	}
}
