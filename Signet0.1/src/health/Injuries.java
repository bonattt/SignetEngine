package health;

public class Injuries {

	private static final String[] SLASHING_NAMES = new String[]{"slash1", "slash2", "slash3", "slash4", "slash5", "slash6", "slash7"};
	private static final int[] SLASHING_HEALTH_DAMAGE = new int[]{0, 0, 2, 4, 8, 13, 19};
	private static final int[] SLASHING_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] SLASHING_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] SLASHING_DAMAGE_RATE = new int[]{1, 1, 1000, 1000, 500, 17, 1};
	private static final int[] SLASHING_INST_HEALTH_DAMAGE = new int[]{0, 0, 0, 5, 10, 15, 5000};
	private static final int[] SLASHING_INST_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] SLASHING_INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] SLASHING_HEALING_TIME = new int[]{24000, 96000, 168000, 168000, 168000, 168000, Integer.MIN_VALUE};
	private static final int[] SLASHING_CHANCE_OF_INFECTION = new int[]{1, 5, 15, 25, 35, 80, 0};
	private static final double[] SLASHING_PAIN = new double[]{0, 0.008, 0.16, 0.63, 1.25, 2.5, 5};
	private static final int[] SLASHING_CRIPPLING = new int[]{0, 0, 1, 1, 2, 3, Integer.MAX_VALUE};

	private static final String[] STAB_NAMES = new String[]{"stab1", "stab2", "stab3", "stab4", "stab5", "stab6", "stab7"};
	private static final int[] STAB_HEALTH_DAMAGE = new int[]{0, 0, 1, 2, 3, 13, 19};
	private static final int[] STAB_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] STAB_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] STAB_DAMAGE_RATE = new int[]{1, 1, 2000, 1000, 500, 17, 1};
	private static final int[] STAB_INST_HEALTH_DAMAGE = new int[]{0, 0, 8, 18, 35, 50, 1000};
	private static final int[] STAB_INST_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] STAB_INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] STAB_HEALING_TIME = new int[]{24000, 120000, 168000, 192000, 168000, 192000, Integer.MIN_VALUE};
	private static final int[] STAB_CHANCE_OF_INFECTION = new int[]{1, 2, 10, 15, 25, 70, 100};
	private static final double[] STAB_PAIN = new double[]{0, 0.008, 0.16, 0.63, 1.25, 2.5, 5};
	private static final int[] STAB_CRIPPLING = new int[]{0, 0, 1, 1, 2, 3, Integer.MAX_VALUE};
	
	private static final String[] BURN_NAMES = new String[]{"n/a", "1st degree burn", "minor 2nd degree burn", "severe 2nd degree burn",
		"3rd degree burn", "4th degree burn", "cooked"};
	private static final int[] BURN_HEALTH_DAMAGE = new int[]{0, 0, 1, 1, 3, 5, Integer.MAX_VALUE};
	private static final int[] BURN_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BURN_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BURN_DAMAGE_RATE = new int[]{1, 1, 6000, 3000, 1000, 1000, 0};
	private static final int[] BURN_INST_HEALTH_DAMAGE = new int[]{0, 3, 6, 13, 25, 50, Integer.MAX_VALUE};
	private static final int[] BURN_INST_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BURN_INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BURN_HEALING_TIME = new int[]{0, 168000, 336000, 840000, 1440000, Integer.MAX_VALUE, -1};
	private static final int[] BURN_CHANCE_OF_INFECTION = new int[]{0, 0, 20, 30, 80, 95, 0};
	private static final double[] BURN_PAIN = new double[]{0, 1, 2, 5, 0, 0, 0};
	private static final int[] BURN_CRIPPLING = new int[]{0, 0, 1, 2, 3, 4, -1};

	private static final String[] BLUNT_NAMES = new String[]{"blunt1", "blunt2", "blunt3", "blunt4", "blunt5", "blunt6", "blunt7"};
	private static final int[] BLUNT_HEALTH_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BLUNT_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BLUNT_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BLUNT_DAMAGE_RATE = new int[]{1, 1, 1, 1, 1, 1, 1};
	private static final int[] BLUNT_INST_HEALTH_DAMAGE = new int[]{0, 0, 0, 5, 10, 15, 5000};
	private static final int[] BLUNT_INST_STUN_DAMAGE = new int[]{1, 5, 10, 15, 20, 25, 5000};
	private static final int[] BLUNT_INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BLUNT_HEALING_TIME = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] BLUNT_CHANCE_OF_INFECTION = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final double[] BLUNT_PAIN = new double[]{0, 0.008, 0.16, 0.63, 1.25, 2.5, 5};
	private static final int[] BLUNT_CRIPPLING = new int[]{0, 0, 0, 4, 5, 6, Integer.MAX_VALUE};
	
	public static Wound getBluntWound(int severity, BodyPart bodypart, boolean randomize) {
		Wound wound = new Wound(randomize, severity, DamageType.BLUNT, BLUNT_NAMES, bodypart, BLUNT_HEALTH_DAMAGE,
				BLUNT_STUN_DAMAGE, BLUNT_FATIGUE_DAMAGE, BLUNT_DAMAGE_RATE, BLUNT_INST_HEALTH_DAMAGE,
				BLUNT_INST_STUN_DAMAGE,	BLUNT_INST_FATIGUE_DAMAGE, BLUNT_HEALING_TIME,
				BLUNT_CHANCE_OF_INFECTION, BLUNT_PAIN,BLUNT_CRIPPLING);
		return wound;
	}
	public static Wound getBluntWound(int severity, BodyPart bodypart) {
		return getBluntWound(severity, bodypart, true);
	}
	
	public static Wound getBurnWound(int severity, BodyPart bodypart, boolean randomize) {
		Wound wound = new Wound(randomize, severity, DamageType.FIRE, BURN_NAMES, bodypart, BURN_HEALTH_DAMAGE,
				BURN_STUN_DAMAGE, BURN_FATIGUE_DAMAGE, BURN_DAMAGE_RATE, BURN_INST_HEALTH_DAMAGE,
				BURN_INST_STUN_DAMAGE, BURN_INST_FATIGUE_DAMAGE, BURN_HEALING_TIME, BURN_CHANCE_OF_INFECTION,
				BURN_PAIN, BURN_CRIPPLING);
		return wound;
	}
	public static Wound getBurnWound(int severity, BodyPart bodypart) {
		return getBurnWound(severity, bodypart, true);
	}
	
	
	public static Wound getStabWound(int severity, BodyPart bodypart, boolean randomize) {
		Wound wound = new Wound(randomize, severity, DamageType.PIERCING, STAB_NAMES, bodypart, STAB_HEALTH_DAMAGE,
				STAB_STUN_DAMAGE, STAB_FATIGUE_DAMAGE, STAB_DAMAGE_RATE, STAB_INST_HEALTH_DAMAGE,
				STAB_INST_STUN_DAMAGE, STAB_INST_FATIGUE_DAMAGE, STAB_HEALING_TIME, STAB_CHANCE_OF_INFECTION,
				STAB_PAIN, STAB_CRIPPLING);
		return wound;
	}
	public static Wound getStabWound(int severity, BodyPart bodypart) {
		return getStabWound(severity, bodypart, true);
	}
	
	public static Wound getSlashingWound(int severity, BodyPart bodypart, boolean randomize) {
		Wound wound = new Wound(randomize, severity, DamageType.SLASHING, SLASHING_NAMES, bodypart, SLASHING_HEALTH_DAMAGE,
				SLASHING_STUN_DAMAGE, SLASHING_FATIGUE_DAMAGE, SLASHING_DAMAGE_RATE, SLASHING_INST_HEALTH_DAMAGE,
				SLASHING_INST_STUN_DAMAGE, SLASHING_INST_FATIGUE_DAMAGE, SLASHING_HEALING_TIME, SLASHING_CHANCE_OF_INFECTION,
				SLASHING_PAIN, SLASHING_CRIPPLING);
		return wound;
	}
	public static Wound getSlashingWound(int severity, BodyPart bodypart) {
		return getSlashingWound(severity, bodypart, true);
	}
	
	public static Wound getNewWound(int severity, int damageType, BodyPart bodypart, boolean randomize) {
		Wound wound;
		if (damageType == DamageType.SLASHING){
			wound = Injuries.getSlashingWound(severity, bodypart, randomize);
		} else if (damageType == DamageType.BLUNT) {
			wound = Injuries.getBluntWound(severity, bodypart, randomize);
		} else if (damageType == DamageType.PIERCING) {
			wound = Injuries.getStabWound(severity, bodypart, randomize);
		} else if (damageType == DamageType.FIRE) {
			wound = Injuries.getBurnWound(severity, bodypart, randomize);
		} else {
 			throw new IllegalArgumentException("That damage type has not been implemented yet!!");
		}
		return wound;
	}
	public static Wound getNewWound(int severity, int damageType, BodyPart bodypart) {
		return getNewWound(severity, damageType, bodypart, true);
	}
}
