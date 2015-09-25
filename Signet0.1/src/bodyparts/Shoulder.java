package bodyparts;

import health.BodyPart;

public class Shoulder extends BodyPart {

	private static final double shoulder_damage = .5;
	private static final double shoulder_pain = 1; 
	private static final double shoulder_crippling = 1;

	public Shoulder(String name) {
		super(name, shoulder_damage, shoulder_pain, shoulder_crippling, new double[]{1,1,1,1,1,1,1,1,1,1});
		// TODO refine stats
	}
	
}
