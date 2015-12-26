package creatures;

public class SampleSkills {
	
	public static final int PISTOLS = 0;
	public static final int RIFLES = 1;
	public static final int AUTOMATIC_WEAPONS = 2;
	public static final int KNIVES = 8;
	
	public static final int CON = 3;
	public static final int NEGOCIATE = 4;
	public static final int INTIMIDATE = 5;
	public static final int STEALTH = 6;
	public static final int ELECTRONICS = 7;

	public static Skill knives(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.combat, SkillTags.rightHand};
		String[] linkedAttributes = new String[]{"agl", "dex"};
		return new Skill("knives", KNIVES, ranks, linkedAttributes, tags);
	}
	
	public static Skill pistols(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.combat, SkillTags.hands};
		String[] linkedAttributes = new String[]{"agl", "dex"};
		return new Skill("pistols", PISTOLS, ranks, linkedAttributes, tags);
	}

	public static Skill rifles(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.combat, SkillTags.arms};
		String[] linkedAttributes = new String[]{"agl", "dex"};
		return new Skill("rifles", RIFLES, ranks, linkedAttributes, tags);
	}
	
	public static Skill automaticWeapons(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.combat, SkillTags.arms};
		String[] linkedAttributes = new String[]{"str"};
		return new Skill("automatic weapons", AUTOMATIC_WEAPONS, ranks, linkedAttributes, tags);
	}
	
	public static Skill con(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.social};
		String[] linkedAttributes = new String[]{"cha"};
		return new Skill("con", CON, ranks, linkedAttributes, tags);
	}

	public static Skill negociate(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.social};
		String[] linkedAttributes = new String[]{"cha"};
		return new Skill("negociate", NEGOCIATE, ranks, linkedAttributes, tags);
	}

	public static Skill intimidate(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.social};
		String[] linkedAttributes = new String[]{"cha"};
		return new Skill("intimidate", INTIMIDATE, ranks, linkedAttributes, tags);
	}
	
	public static Skill stealth(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.legs, SkillTags.body};
		String[] linkedAttributes = new String[]{"agl"};
		return new Skill("stealth", STEALTH, ranks, linkedAttributes, tags);
	}

	public static Skill electronics(int ranks) {
		SkillTags[] tags = new SkillTags[]{SkillTags.hands, SkillTags.mental};
		String[] linkedAttributes = new String[]{"dex", "anl"};
		return new Skill("electronics", ELECTRONICS, ranks, linkedAttributes, tags);
	}
}
