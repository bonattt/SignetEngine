package injuries;

import misc.DamageType;
import health.BodyPart;
import health.Wound;

public class BluntWound extends Wound {
	

	private static final String[] NAMES = new String[]{"blunt1", "blunt2", "blunt3", "blunt4", "blunt5", "blunt6", "blunt7"};

	private static final int[] HEALTH_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] STUND_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] DAMAGE_RATE = new int[]{1, 1, 1, 1, 1, 1, 1};
	private static final int[] INST_HEALTH_DAMAGE = new int[]{0, 0, 0, 5, 10, 15, 5000};
	private static final int[] INST_STUN_DAMAGE = new int[]{1, 5, 10, 15, 20, 25, 5000};
	private static final int[] INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] HEALING_TIME = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] CHANCE_OF_INFECTION = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final double[] PAIN = new double[]{0, 0.0008, 0.016, 0.063, 0.125, 0.25, 0.5};
	private static final int[] CRIPPLING = new int[]{0, 0, 0, 4, 5, 6, Integer.MAX_VALUE};
	
//	HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE, INST_FATIGUE_DAMAGE,
//	HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING
	public BluntWound(int severity, BodyPart bodypart){
		super(severity, DamageType.blunt, bodypart,
				HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE,
				INST_FATIGUE_DAMAGE,HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING);
		name = NAMES[severity - 1];
	}
}
