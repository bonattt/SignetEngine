package bodyparts;

import health.BodyPart;

public class Arm extends BodyPart{
	
	private static final double arm_damage = .5;
	private static final double arm_pain = 1; 
	private static final double arm_crippling = 1;

	public Arm(String name) {
		super(name, arm_damage, arm_pain, arm_crippling, new double[]{1,1,1,1,1,1,1,1,1,1});
		// TODO Auto-generated constructor stub
	}

}
