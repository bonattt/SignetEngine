package injuries;

import misc.DamageType;
import health.BodyPart;
import health.Wound;

public class SlashingWound extends Wound {
	

	private static final String[] NAMES = new String[]{"slash1", "slash2", "slash3", "slash4", "slash5", "slash6", "slash7"};

	private static final int[] HEALTH_DAMAGE = new int[]{0, 0, 2, 4, 8, 13, 19};
	private static final int[] STUND_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 0};
	private static final int[] DAMAGE_RATE = new int[]{1, 1, 1000, 1000, 500, 17, 1};
	private static final int[] INST_HEALTH_DAMAGE = new int[]{0, 0, 0, 5, 10, 15, 5000};
	private static final int[] INST_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, 1000};
	private static final int[] HEALING_TIME = new int[]{24000, 96000, 168000, 168000, 168000, 168000, Integer.MIN_VALUE};
	private static final int[] CHANCE_OF_INFECTION = new int[]{1, 5, 15, 25, 35, 80, 0};
	private static final double[] PAIN = new double[]{0, 0.0008, 0.016, 0.063, 0.125, 0.25, 0.5};
	private static final int[] CRIPPLING = new int[]{0, 0, 1, 1, 2, 3, Integer.MAX_VALUE};
	
//	HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE, INST_FATIGUE_DAMAGE,
//	HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING
	public SlashingWound(int severity, BodyPart bodypart){
		super(severity, DamageType.SLASHING, bodypart,
				HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE,
				INST_FATIGUE_DAMAGE,HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING);
		name = NAMES[severity - 1];
	}
}
