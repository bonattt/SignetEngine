package injuries;

import health.BodyPart;
import health.DamageType;
import health.Wound;

public class BurnWound extends Wound {
	
	private static final String[] NAMES = new String[]{"n/a", "1st degree burn", "minor 2nd degree burn", "severe 2nd degree burn",
														"3rd degree burn", "4th degree burn", "cooked"};

			
	private static final int[] HEALTH_DAMAGE = new int[]{0, 0, 1, 1, 3, 5, -1};
	private static final int[] STUND_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, -1};
	private static final int[] FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, -1};
	private static final int[] DAMAGE_RATE = new int[]{1, 1, 6000, 3000, 1000, 1000, -1};
	private static final int[] INST_HEALTH_DAMAGE = new int[]{0, 3, 6, 13, 25, 50, -1};
	private static final int[] INST_STUN_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, -1};
	private static final int[] INST_FATIGUE_DAMAGE = new int[]{0, 0, 0, 0, 0, 0, -1};
	private static final int[] HEALING_TIME = new int[]{0, 168000, 336000, 840000, 1440000, Integer.MAX_VALUE, -1};
	private static final int[] CHANCE_OF_INFECTION = new int[]{0, 0, 20, 30, 80, 95, -1};
	private static final double[] PAIN = new double[]{0, 0.1, 0.2, 0.5, 0, 0, -1};
	private static final int[] CRIPPLING = new int[]{0, 0, 1, 2, 3, 4, -1};
	
//	HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE, INST_FATIGUE_DAMAGE,
//	HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING
	public BurnWound(int severity, BodyPart bodypart){
		super(severity, DamageType.BLUNT, bodypart,
				HEALTH_DAMAGE, STUND_DAMAGE, FATIGUE_DAMAGE, DAMAGE_RATE, INST_HEALTH_DAMAGE, INST_STUN_DAMAGE,
				INST_FATIGUE_DAMAGE,HEALING_TIME, CHANCE_OF_INFECTION, PAIN, CRIPPLING);
		name = NAMES[severity - 1];
	}
}
