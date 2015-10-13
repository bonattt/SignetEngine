package sampleSkills;

import creatures.Skill;
import creatures.SkillTags;

public class NegociateSkill extends Skill {

	public static final int ID = 15;
	
	public NegociateSkill() {
		super("Negociate", ID, getTags());
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.social};
	}
}
