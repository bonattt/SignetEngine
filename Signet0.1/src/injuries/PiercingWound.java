package injuries;

import health.BodyPart;
import health.DamageType;
import health.Wound;

public class PiercingWound extends Wound {
	

	private static final String[] NAMES = new String[]{"stab1", "stab2", "stab3", "stab4", "stab5", "stab6", "stab7"};

	private static final int[] HEALTH_DAMAGE = new int[]{0, 0, 1, 2, 3, 13, 19};
	private static final int[] STUND_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] DAMAGE_RATE = new int[]{1, 1, 2000, 1000, 500, 17, 1};
	private static final int[] INST_HEALTH_DAMAGE = new int[]{0, 0, 8, 18, 35, 50, 1000};
	private static final int[] INST_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] HEALING_TIME = new int[]{24000, 120000, 168000, 192000, 168000, 192000, Integer.MIN_VALUE};
	private static final int[] CHANCE_OF_INFECTION = new int[]{1, 2, 10, 15, 25, 70, 100};
	private static final double[] PAIN = new double[]{0, 0.0008, 0.016, 0.063, 0.125, 0.25, 0.5};
	private static final int[] CRIPPLING = new int[]{0, 0, 1, 1, 2, 3, Integer.MAX_VALUE};
	
//	HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE, INST_FATIGUE_DAMAGE,
//	HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING
	public PiercingWound(int severity, BodyPart bodypart){
		super(severity, DamageType.PIERCING, bodypart,
				HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE,
				INST_FATIGUE_DAMAGE,HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING);
		name = NAMES[severity - 1];
	}
}
