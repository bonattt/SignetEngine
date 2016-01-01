package health;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import misc.DiceRoller;
import items.Bandage;
import items.Ointment;

public class Wound {
	
	protected static final int INFECTION_FREQUENCY = 24000; // 24 hours

	private int 
	recoveryClock,			// time passed since wound was dealt or since wound reached current severity
	bleedingClock,			// time left until bleeding damage is taken again.
	infectionClock;			// time left until infection may occur again.
	
	private double[] pain;	// pain inflicted by the wound. 
	
	private String[] names; // names used at different severities.
	
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
	
	private Ointment ointment;
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
	public Wound(int severity, int dt, String[] names, BodyPart woundLocation, int[] health, int[] stun, int[] fatigue, int[] rate,
			int[] instHealth, int[] instStun, int[] instFatigue, int[] healTime, int[] infection, double[] pain, int[] cripple){
		this(true, severity, dt, names, woundLocation, health, stun, fatigue, rate, instHealth, instStun, instFatigue,
				healTime, infection, pain, cripple);
	}
	public Wound(boolean randomize, int severity, int dt, String[] names, BodyPart woundLocation, int[] health,
			int[] stun, int[] fatigue, int[] rate, int[] instHealth, int[] instStun, int[] instFatigue, int[] healTime,
			int[] infection, double[] pain, int[] cripple){
	
		
		if (severity > 7){
			throw new IllegalArgumentException("you cannot have a wound severity higher than 7");
		}
		this.names = names;
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
		/* recovery time must be a shallow copy so the healing time required can be slightly randomized and 
		   different between different wounds */
		recoveryTime = healTime.clone();
		chanceOfInfection = infection;
		this.pain = pain;
		crippling = cripple;
		if (randomize) {
			randomizeHealingTimes();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Wound)) {
			return false;
		} 
		Wound wound = (Wound) o;
		
		if(this.ointment == null) {
			if (wound.ointment != null) {
				return false;
			}
		} else {
			if (! this.ointment.equals(wound.ointment)) {
				return false;
			}
		}
		
		if(this.bandage == null) {
			if (wound.bandage != null) {
				return false;
			}
		} else {
			if (! this.bandage.equals(wound.bandage)) {
				return false;
			}
		}
		
		if(this.infection == null) {
			if (wound.infection != null) {
				return false;
			}
		} else {
			if (! this.infection.equals(wound.infection)) {
				return false;
			}
		}
		
		return (Arrays.equals(this.names, (wound.names))) &&
				(this.severity == wound.severity) &&
				(this.originalSeverity == wound.originalSeverity) &&
				(this.damageType == wound.damageType) &&
				(this.location == wound.location) &&
				(Arrays.equals(this.healthDamage, (wound.healthDamage))) &&
				(Arrays.equals(this.stunDamage, (wound.stunDamage))) &&
				(Arrays.equals(this.fatigueDamage, (wound.fatigueDamage))) &&
				(Arrays.equals(this.damageRate, (wound.damageRate))) &&
				(Arrays.equals(this.instantHealthDamage, wound.instantHealthDamage)) &&
				(Arrays.equals(this.instantStun, wound.instantStun)) &&
				(Arrays.equals(this.instantFatigue, wound.instantFatigue)) &&
				(Arrays.equals(this.recoveryTime, wound.recoveryTime)) &&
				(Arrays.equals(this.chanceOfInfection, wound.chanceOfInfection)) &&
				(Arrays.equals(this.pain, wound.pain)) &&
				(Arrays.equals(this.crippling, wound.crippling));
				
	}
	
	public static Wound loadAlpha1_0fromFile(Scanner scanner, BodyPart woundLocation){
		String[] names = loadStrArray(scanner);
		int severity = scanner.nextInt();
		int originalSeverity = scanner.nextInt();
		int damageType = scanner.nextInt();
		int[] healthDamage, stunDamage, fatigueDamage, damageRate, instHealthDamage, instStun,
			instFatigue, healTime, infection, cripple;
		double[] pain;
		healthDamage = loadIntArray(scanner);
		stunDamage = loadIntArray(scanner);
		fatigueDamage = loadIntArray(scanner);
		damageRate = loadIntArray(scanner);
		instHealthDamage = loadIntArray(scanner);
		instStun = loadIntArray(scanner);
		instFatigue = loadIntArray(scanner);
		healTime = loadIntArray(scanner);
		infection = loadIntArray(scanner);
		cripple = loadIntArray(scanner);
		pain = loadDoubleArray(scanner);
		
		int recoveryClock, bleedingClock, infectionClock;
		recoveryClock = scanner.nextInt();
		bleedingClock = scanner.nextInt();
		infectionClock = scanner.nextInt();
		scanner.nextLine();
		boolean randomize = false;
		Wound wound = new Wound(randomize, originalSeverity, damageType, names, woundLocation, healthDamage,
				stunDamage, fatigueDamage, damageRate, instHealthDamage, instStun, instFatigue, healTime,
				infection, pain, cripple);
		return wound;
	}
	
	private static String[] loadStrArray(Scanner scanner) {
		String[] array = new String[scanner.nextInt()];
		scanner.nextLine();
		for (int i = 0; i < array.length; i++) {
			array[i] = scanner.nextLine();
		}
		return array;
	}
	
	private static int[] loadIntArray(Scanner scanner) {
		int[] array = new int[scanner.nextInt()];
		for (int i = 0; i < array.length; i++) {
			array[i] = scanner.nextInt();
		}
		return array;
	}
	private static double[] loadDoubleArray(Scanner scanner) {
		double[] array = new double[scanner.nextInt()];
		for (int i = 0; i < array.length; i++) {
			array[i] = scanner.nextDouble();
		}
		return array;
	}
	
	public String name() {
		return names[severity - 1];
	}
	
	public void saveToFile(PrintWriter writer){
		saveStrArray(writer, names);
		writer.println(severity);
		writer.println(originalSeverity);
		writer.println(damageType);
		saveIntArray(writer, healthDamage);
		saveIntArray(writer, stunDamage);
		saveIntArray(writer, fatigueDamage);
		saveIntArray(writer, damageRate);
		saveIntArray(writer, instantHealthDamage);
		saveIntArray(writer, instantStun);
		saveIntArray(writer, instantFatigue);
		saveIntArray(writer, recoveryTime);
		saveIntArray(writer, chanceOfInfection);
		saveIntArray(writer, crippling);
		saveDoubleArray(writer, pain);
		
		writer.println(recoveryClock);
		writer.println(bleedingClock);
		writer.println(infectionClock);
		
		if(ointment == null){
			writer.println("no ointment");
		} else {
			ointment.saveToFile(writer);
		}
		
		if (bandage == null){
			writer.println("no bandage");
		} else {
			bandage.saveToFile(writer);
		}
		
		if (infection == null){
			writer.println("no infection");
		} else {
			infection.saveToFile(writer);
		}
	}
	private static void saveStrArray(PrintWriter writer, String[] array) {
		writer.println(array.length);
		for (int i = 0; i < array.length; i++) {
			writer.println(array[i]);
		}
	}
	
	private static void saveIntArray(PrintWriter writer, int[] array) {
		writer.print(array.length);
		for (int i = 0; i < array.length; i++) {
			writer.print(" " + array[i]);
		}
		writer.println();
	}
	private static void saveDoubleArray(PrintWriter writer, double[] array) {
		writer.print(array.length);
		for (int i = 0; i < array.length; i++) {
			writer.print(" " + array[i]);
		}
		writer.println();
	}
	
	public boolean isBandaged(){
		return bandage != null;
	}
	public boolean hasOintment(){
		return ointment != null;
	}
	public boolean isTreated(){
		return (ointment != null) || (bandage != null);
	}
	
	private void randomizeHealingTimes(){
		Random r = DiceRoller.rand;
		for (int i = 0; i < recoveryTime.length-1; i++){
			double numb = (r.nextDouble() * .30) + .85; // random number from .85 to 1.15
			recoveryTime [i] *= numb;
		}
	}

	public int getSeverity(){
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
		Wound wound = Injuries.getNewWound(damage, dt, woundLocation);
		woundLocation.addNewWound(wound);
		return wound.getInstantDamage();
	}
	public void setTreatment(Ointment arg){
		ointment = arg;
		arg.setWound(this);
	}
	public void setBandage(Bandage arg) {
		bandage = arg;
		arg.setWound(this);
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
		} if (bandage != null){
			healingFactor *= bandage.getHealingRateAdjustment();
			infectionRate *= bandage.getInfectionMultiplier();
			// TODO bandage.passTime(timePassed, healingFactor, this);
			if (bandage.needsToBeChanged()){
				System.out.println("your bandage needs to be changed");
			}
		} if (ointment != null){
			healingFactor *= ointment.getHealingRateAdjustment();
			infectionRate *= ointment.getInfectionMultiplier();
	//		if(!medication.passTime(timePassed, healingFactor, this)){
	//			medication = null;
	//		}
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
			for (int i = 0; i < numbChecks; i++){
				checkIfWoundGetsInfected(infectionRate);
			}
		}
		return damage;
	}
	protected double getInfectionRate(){
		return severity-1;
	}
	
	private double[] dealDamageOverTime(int timePassed){
		if (severity != originalSeverity){
			return new double[]{0, 0, 0};
		}
		double damageMultiplier = 1;
		if (bandage != null){
			damageMultiplier *= bandage.getDamageMultiplier();
		} if (ointment != null){
			damageMultiplier *= ointment.getDamageMultiplier();
		}
		
		double[] damage = new double[3];
		int damageCount = timePassed / damageRate[severity - 1];	// int division, gets completed number of damage increments
		bleedingClock = timePassed % damageRate[severity - 1];		// gets remainder and stores it in bleedingClock
		damage[0] = healthDamage[severity-1] * damageCount * damageMultiplier;
		damage[1] = stunDamage[severity-1] * damageCount * damageMultiplier;
		damage[2] = fatigueDamage[severity-1] * damageCount * damageMultiplier;
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
	
	public void checkIfWoundGetsInfected(double infectionRate){
		System.out.println("METHOD 'checkIfWoundGetsInfected' IN CLASS 'Wound' IS UNIMPLEMENTED");
	}
}
