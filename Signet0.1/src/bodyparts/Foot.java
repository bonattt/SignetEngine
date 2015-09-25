package bodyparts;

import health.BodyPart;

public class Foot extends BodyPart {

	private static final double leg_damage = .5;
	private static final double leg_pain = 1; 
	private static final double leg_crippling = 1;
	
	public Foot(String name) {
		super(name, leg_damage, leg_pain, leg_crippling, new double[]{1,1,1,1,1,1,1,1,1,1});
	}

}
