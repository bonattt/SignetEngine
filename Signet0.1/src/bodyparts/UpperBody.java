package bodyparts;

import health.BodyPart;

public class UpperBody extends BodyPart {

	private static final double body_damage = .5;
	private static final double body_pain = 1; 
	private static final double body_crippling = 1;

	public UpperBody(String name) {
		super(name, body_damage, body_pain, body_crippling, new double[]{1,1,1,1,1,1,1,1,1,1});
	}

}
