package bodyparts;

import health.BodyPart;

public class Head extends BodyPart {

	private static final double head_damage = .5;
	private static final double head_pain = 1; 
	private static final double head_crippling = 1;
	
	public Head(String name) {
		super(name, head_damage, head_pain, head_crippling, new double[]{1,1,1,1,1,1,1,1,1,1});
	}

}
