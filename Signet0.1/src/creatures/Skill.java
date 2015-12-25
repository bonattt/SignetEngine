package creatures;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import misc.*;


public class Skill {
	 
	public String name;
	public int id;
	public SkillTags[] tags;
	public String[] linkedAttributes;
	public int ranks;
	private int exp;
	
	public static final HashMap<String, Integer> skillIndex = getSkillIndex();
	
	private static HashMap<String, Integer> getSkillIndex(){
		HashMap<String, Integer> index = new HashMap<String, Integer>();
		return index;
	}
	public Skill(String name, int id, String[] linkedAttributes, SkillTags[] tags, int startingRanks){
		this.name = name;
		this.id = id;		
		this.tags = tags;
		this.ranks = startingRanks;
		this.linkedAttributes = linkedAttributes;
		this.exp = 0;
	}
	public Skill(String name, int id, String[] linkedAttributes, SkillTags[] tags){
		this(name, id, linkedAttributes, tags, 0);
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
	public static Skill loadFromFile(Scanner scanner){
		String name = scanner.nextLine();
		String temp = scanner.nextLine();
		int id = Integer.parseInt(temp);
		temp = scanner.nextLine();
		int ranks = Integer.parseInt(temp);
		temp = scanner.nextLine();
		int exp = Integer.parseInt(temp);
//		scanner.nextLine();
		
		ArrayList<SkillTags> tagsList = new ArrayList<SkillTags>();
		String currentLine = scanner.nextLine();
		while (! currentLine.equals("end tags")){
			SkillTags tag = skillTagStringToEnum(currentLine);
			tagsList.add(tag);
			currentLine = scanner.nextLine();
		}
		SkillTags[] tags = new SkillTags[tagsList.size()];
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
		
		Skill skill = new Skill(name, id, attributes, tags, ranks);
		skill.exp = exp;
		return skill;
	}
	public static SkillTags skillTagStringToEnum(String tag){
		if (tag.equals("visual")){
			return SkillTags.visual;
		} else if (tag.equals("auditory")) {
			return SkillTags.auditory;
		} else if (tag.equals("mental")) {
			return SkillTags.mental;
		} else if (tag.equals("language")) {
			return SkillTags.language;
		} else if (tag.equals("social")) {
			return SkillTags.social;
		} else if (tag.equals("combat")) {
			return SkillTags.combat;
		} else if (tag.equals("leftArm")) {
			return SkillTags.leftArm;
		} else if (tag.equals("rightArm")) {
			return SkillTags.rightArm;
		} else if (tag.equals("leftFoot")) {
			return SkillTags.leftFoot;
		} else if (tag.equals("rightFoot")) {
			return SkillTags.rightFoot;
		} else if (tag.equals("leftHand")) {
			return SkillTags.leftHand;
		} else if (tag.equals("rightHand")) {
			return SkillTags.rightHand;
		} else if (tag.equals("leftLeg")) {
			return SkillTags.leftLeg;
		} else if (tag.equals("rightLeg")) {
			return SkillTags.rightLeg;
		} else if (tag.equals("hands")) {
			return SkillTags.hands;
		} else if (tag.equals("arms")) {
			return SkillTags.arms;
		} else if (tag.equals("legs")) {
			return SkillTags.legs;
		} else if (tag.equals("feet")) {
			return SkillTags.feet;
		} else if (tag.equals("body")) {
			return SkillTags.body;
		} else {
			return null;
			// TODO ERROR!!!!!!!
		}
	}
	public static String skillTagEnumToString(SkillTags tag){
		if (tag == SkillTags.visual){
			return "visual";
		} else if (tag == SkillTags.auditory) {
			return "auditory";
		} else if (tag == SkillTags.mental) {
			return "mental";
		} else if (tag == SkillTags.language) {
			return "language";
		} else if (tag == SkillTags.social) {
			return "social";
		} else if (tag == SkillTags.combat) {
			return "combat";
		} else if (tag == SkillTags.leftArm) {
			return "leftArm";
		} else if (tag == SkillTags.rightArm) {
			return "rightArm";
		} else if (tag == SkillTags.leftFoot) {
			return "leftFoot";
		} else if (tag == SkillTags.rightFoot) {
			return "rightFoot";
		} else if (tag == SkillTags.leftHand) {
			return "leftHand";
		} else if (tag == SkillTags.rightHand) {
			return "rightHand";
		} else if (tag == SkillTags.leftLeg) {
			return "leftLeg";
		} else if (tag == SkillTags.rightLeg) {
			return "rightLeg";
		} else if (tag == SkillTags.hands) {
			return "hands";
		} else if (tag == SkillTags.arms){
			return "arms";
		} else if (tag == SkillTags.legs) {
			return "legs";
		} else if (tag == SkillTags.feet) {
			return "feet";
		} else if (tag == SkillTags.body) {
			return "body";
		} else {
			return "ERROR";
			// TODO ERROR!!!!!!!
		}
	}
	
	public void setRanks(int newRank){
		ranks = newRank;
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
	public int[] makeSkillTest(Creature creature, int threshold){
		return makeLimitedSkillTest(creature, threshold, 0, linkedAttributes, Integer.MAX_VALUE);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment){
		return makeLimitedSkillTest(creature, threshold, adjustment, linkedAttributes, Integer.MAX_VALUE);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment, String attribute){
		return makeLimitedSkillTest(creature, threshold, 0, new String[]{attribute}, Integer.MAX_VALUE);
	}
	public int[] makeSkillTest(Creature creature, int threshold, int adjustment, String[] attributes){
		return makeLimitedSkillTest(creature, threshold, adjustment, attributes, Integer.MAX_VALUE);
	}
	/////
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int limit){
		return makeLimitedSkillTest(creature, threshold, 0, linkedAttributes, limit);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, int limit){
		return makeLimitedSkillTest(creature, threshold, adjustment, linkedAttributes, limit);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, String attribute, int limit){
		return makeLimitedSkillTest(creature, threshold, 0, new String[]{attribute}, limit);
	}
	public int[] makeLimitedSkillTest(Creature creature, int threshold, int adjustment, String[] attributes, int limit){

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
			accumulator += creature.getStats().get(attributes[i]);
		}
		return accumulator;
	}
}
