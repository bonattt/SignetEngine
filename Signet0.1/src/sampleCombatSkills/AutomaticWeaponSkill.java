package sampleCombatSkills;

import creatures.Skill;
import creatures.SkillTags;

public class AutomaticWeaponSkill extends Skill {

	public static final int ID = 2;
	private static final String[] LINKED_ABILITIES = new String[]{"agl"};
	
	public AutomaticWeaponSkill() {
		super("Automatics", ID, LINKED_ABILITIES, getTags());
	}
	public AutomaticWeaponSkill(int ranks){
		super("Automatics", ID, LINKED_ABILITIES, getTags(), ranks);
	}
	private static SkillTags[] getTags(){
		return new SkillTags[]{SkillTags.combat, SkillTags.hands, SkillTags.visual};
	}
	
}
