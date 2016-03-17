package testingMothers;

import health.BodyPart;

public class SampleBodyparts {
	

	private static final double ARM_DAMAGE = .5;
	private static final double ARM_PAIN = 1; 
	
	private static final double FOOT_DAMAGE = .5;
	private static final double FOOT_PAIN = 1; 
	
	private static final double HAND_DAMAGE = .5;
	private static final double HAND_PAIN = 1; 

	private static final double HEAD_DAMAGE = .5;
	private static final double HEAD_PAIN = 1; 

	private static final double LEG_DAMAGE = .5;
	private static final double LEG_PAIN = 1; 

	private static final double SHOULDER_DAMAGE = .5;
	private static final double SHOULDER_PIAN = 1; 
	
	private static final double BODY_DAMAGE = .5;
	private static final double BODY_PAIN = 1; 
	
	public static BodyPart arm(String name) {
		return new BodyPart(name, ARM_DAMAGE, ARM_PAIN);
	}
	
	public static BodyPart foot(String name) {
		return new BodyPart(name, FOOT_DAMAGE, FOOT_PAIN);
	}
	
	public static BodyPart hand(String name) {
		return new BodyPart(name, HAND_DAMAGE, HAND_PAIN);
	}
	
	public static BodyPart head(String name) {
		return new BodyPart(name, HEAD_DAMAGE, HEAD_PAIN);
	}
	
	public static BodyPart leg(String name) {
		return new BodyPart(name, LEG_DAMAGE, LEG_PAIN);
	}
	
	public static BodyPart shoulder(String name) {
		return new BodyPart(name, SHOULDER_DAMAGE, SHOULDER_PIAN);
	}
	
	public static BodyPart upperBody(String name) {
		return new BodyPart(name, BODY_DAMAGE, BODY_PAIN);
	}
}
