package creatures;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import misc.*;


public class Skill {
	 
	public String name;
	public int id, ranks, exp;
	public SkillTag[] tags;
	public String[] linkedAttributes;
	
	public Skill(String name, int id, int startingRanks, String[] linkedAttributes, SkillTag[] tags){
		this.name = name;
		this.id = id;		
		this.tags = tags;
		this.ranks = startingRanks;
		this.linkedAttributes = linkedAttributes;
		this.exp = 0;
	}
	
	public Skill(String name, int id, String[] linkedAttributes, SkillTag[] tags){
		this(name, id, 0, linkedAttributes, tags);
	}
	
	public void saveToFile(PrintWriter writer){
		writer.println(name);
		writer.println(id);
		writer.println(ranks);
		writer.println(exp);
//		writer.println("tags");
		for (int i = 0; i < tags.length; i++){
			writer.println(skillTagEnumToString(tags[i]));
		}
		writer.println("end tags");
//		writer.println("linked attribute");
		for (int i = 0; i < linkedAttributes.length; i++){
			writer.println(linkedAttributes[i]);
		}
		writer.println("end linked attribute");
	}
	public static Skill loadSkillAlpha0_1(Scanner scanner) throws GameLoadException {
		String name = scanner.nextLine();
		String temp = scanner.nextLine();
		int id = Integer.parseInt(temp);
		temp = scanner.nextLine();
		int ranks = Integer.parseInt(temp);
		temp = scanner.nextLine();
		int exp = Integer.parseInt(temp);
//		scanner.nextLine();
		
		ArrayList<SkillTag> tagsList = new ArrayList<SkillTag>();
		String currentLine = scanner.nextLine();
		while (! currentLine.equals("end tags")){
			SkillTag tag = skillTagStringToEnum(currentLine);
			tagsList.add(tag);
			currentLine = scanner.nextLine();
		}
		SkillTag[] tags = new SkillTag[tagsList.size()];
		for (int i = 0; i < tagsList.size(); i++){
			tags[i] = tagsList.get(i);
		}
		
		ArrayList<String> attributeList = new ArrayList<String>();
		currentLine = scanner.nextLine();
		while(! currentLine.equals("end linked attribute")){
			attributeList.add(currentLine);
			currentLine = scanner.nextLine();
		}
		String[] attributes = new String[attributeList.size()];
		for (int i = 0; i < attributeList.size(); i++){
			attributes[i] = attributeList.get(i);
		}
		
		Skill skill = new Skill(name, id, ranks, attributes, tags);
		skill.exp = exp;
		return skill;
	}
	public static SkillTag skillTagStringToEnum(String tag) throws GameLoadException {
		if (tag.equals("visual")){
			return SkillTag.visual;
		} else if (tag.equals("auditory")) {
			return SkillTag.auditory;
		} else if (tag.equals("mental")) {
			return SkillTag.mental;
		} else if (tag.equals("language")) {
			return SkillTag.language;
		} else if (tag.equals("social")) {
			return SkillTag.social;
		} else if (tag.equals("combat")) {
			return SkillTag.combat;
		} else if (tag.equals("leftArm")) {
			return SkillTag.leftArm;
		} else if (tag.equals("rightArm")) {
			return SkillTag.PRIMARY_ARM;
		} else if (tag.equals("leftFoot")) {
			return SkillTag.leftFoot;
		} else if (tag.equals("rightFoot")) {
			return SkillTag.rightFoot;
		} else if (tag.equals("leftHand")) {
			return SkillTag.leftHand;
		} else if (tag.equals("rightHand")) {
			return SkillTag.rightHand;
		} else if (tag.equals("leftLeg")) {
			return SkillTag.leftLeg;
		} else if (tag.equals("rightLeg")) {
			return SkillTag.rightLeg;
		} else if (tag.equals("hands")) {
			return SkillTag.hands;
		} else if (tag.equals("arms")) {
			return SkillTag.arms;
		} else if (tag.equals("legs")) {
			return SkillTag.legs;
		} else if (tag.equals("feet")) {
			return SkillTag.feet;
		} else if (tag.equals("body")) {
			return SkillTag.body;
		} else {
			throw new GameLoadException("save file corrupted; unrecognized skill tag read in Skill.skillTagStringToEnum");
		}
	}
	public static String skillTagEnumToString(SkillTag tag){
		if (tag == SkillTag.visual){
			return "visual";
		} else if (tag == SkillTag.auditory) {
			return "auditory";
		} else if (tag == SkillTag.mental) {
			return "mental";
		} else if (tag == SkillTag.language) {
			return "language";
		} else if (tag == SkillTag.social) {
			return "social";
		} else if (tag == SkillTag.combat) {
			return "combat";
		} else if (tag == SkillTag.leftArm) {
			return "leftArm";
		} else if (tag == SkillTag.PRIMARY_ARM) {
			return "rightArm";
		} else if (tag == SkillTag.leftFoot) {
			return "leftFoot";
		} else if (tag == SkillTag.rightFoot) {
			return "rightFoot";
		} else if (tag == SkillTag.leftHand) {
			return "leftHand";
		} else if (tag == SkillTag.rightHand) {
			return "rightHand";
		} else if (tag == SkillTag.leftLeg) {
			return "leftLeg";
		} else if (tag == SkillTag.rightLeg) {
			return "rightLeg";
		} else if (tag == SkillTag.hands) {
			return "hands";
		} else if (tag == SkillTag.arms){
			return "arms";
		} else if (tag == SkillTag.legs) {
			return "legs";
		} else if (tag == SkillTag.feet) {
			return "feet";
		} else if (tag == SkillTag.body) {
			return "body";
		} else {
			return "ERROR";
			// TODO ERROR!!!!!!!
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Skill) {
			Skill skl = (Skill) o;
			if (skl.linkedAttributes.length != this.linkedAttributes.length ||
					skl.tags.length != this.tags.length) {
				return false;
			}
			int i;
			for (i = 0; i < this.linkedAttributes.length; i++) {
				if (! this.linkedAttributes[i].equals(skl.linkedAttributes[i])) {
					return false;
				}
			}
			for (i = 0; i < this.tags.length; i++) {
				if (this.tags[i] != skl.tags[i]) {
					return false;
				}
			}
			
			return (skl.exp == this.exp &&
					skl.id == this.id &&
					skl.name.equals(this.name) &&
					skl.ranks == this.ranks);
		}
		return false;
	}
	
	public void gainExperience(int expGained){
		exp += expGained;
//		TextTools.display("you gained " + expGained + " experience in " + name);
		while(exp >= (ranks+1)*1000){
			TextTools.display("you gained a rank in " + name + "!");
			exp -= (ranks+1)*1000;
			ranks++;
		}
	}

	public int[] makeSkillTest(Creature creature, int threshold, int adjustment){
		return makeLimitedSkillTest(creature, threshold, adjustment, Integer.MAX_VALUE, linkedAttributes);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment, String[] attributes){
		return makeLimitedSkillTest(creature, threshold, adjustment, Integer.MAX_VALUE, attributes);
	}
	/////
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, int limit){
		return makeLimitedSkillTest(creature, threshold, adjustment, limit, linkedAttributes);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, int limit, String[] attributes){

		int dicePool;
		if(ranks == 0){
			dicePool = getAttributes(creature, attributes) - 1;
		} else {
			dicePool = getAttributes(creature, attributes) + ranks;
		}
		
		int[] result = DiceRoller.makeRoll(dicePool);
		result[0] -= threshold;
		
		return result;
	}
	
	public static int[] makeOpposedSkillTest(Creature creature1, Skill skl1, Creature creature2, Skill skl2){
		return makeOpposedSkillTest(creature1, skl1, 0, creature2, skl2, 0);
	}
	public static int[] makeOpposedSkillTest(Creature creature1, Skill skl1, int adjust1, Creature creature2, Skill skl2, int adjust2){
		int[] result1 = skl1.makeSkillTest(creature1, 0, adjust1, skl1.linkedAttributes);
		int[] result2 = skl2.makeSkillTest(creature2, 0, adjust2, skl2.linkedAttributes);
		return new int[]{(result1[0] - result2[0]), result1[1], result2[1]};
	}
	
//	public static int[] makeSkillTest(Creature creature, String skill){
//		return makeSkillTest(creature, skill, 0);
//	}
//	public static int[] makeSkillTest(Creature creature, String skillStr, int threshold){
//		return creature.getSkills().get(skillStr)
//				.makeSkillTest(creature, threshold);
//	}
	
	private static int getAttributes(Creature creature, String[] attributes){
		int accumulator = 0;
		for (int i = 0; i < attributes.length; i++){
			accumulator += creature.getStat(attributes[i]);
		}
		return accumulator;
	}
}
