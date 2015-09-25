package bodyparts;

import health.BodyPart;

public class Hand extends BodyPart {

	private static final double hand_damage = .5;
	private static final double hand_pain = 1; 
	private static final double hand_crippling = 1;
	
	public Hand(String name) {
		super(name, hand_damage, hand_pain, hand_crippling, new double[]{1,1,1,1,1,1,1,1,1,1});
	}

}
