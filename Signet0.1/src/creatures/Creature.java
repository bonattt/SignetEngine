package creatures;

import health.Body;
import health.BodyPart;
import health.HealthException;
import health.Wound;
import inventory.Inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import misc.DeathException;
import misc.DiceRoller;
import misc.GameLoadException;
import misc.TextTools;

public abstract class Creature {
	/* 
	 * PHYSICAL: str (strength), agl (agilit), end(endurance),
	 * SKILL: dex(dexterity), cha(charisma), anl(analysis),
	 * MENTAL: per(perception), wil(willpower), int(intuition), rec(rection)
	 */
	public static final String[] ABILITIES = {"str", "agl", "end", "dex", "cha", "anl", "per", "wil", "int", "rec"};
	
	
	private String name;
	
	private Body body;
	private Inventory inv;
	
	private HashMap<String,Integer> statsBase, statsAdjusted;
	private HashMap<String,Integer> damageMultipliers;
	private HashMap<String, Skill> skills;
	
	public Creature(String creatureName, HashMap<String,Integer> baseStats, HashMap<String,Integer> damageMultipliers,
			HashMap<String, Skill> startingSkills, HashMap<String, BodyPart> bodyparts){
		this.name = creatureName;
		this.damageMultipliers = damageMultipliers;
		statsBase = baseStats;
		statsAdjusted = statsBase; // TODO make this a deep copy, then make it actually modify stats.
		skills = startingSkills;
		inv = new Inventory();
		body = new Body(this, bodyparts);
	}
	
	// Selects the combat action that will be used in a fight. PC's prompt for user input, NPC's (Monster) use algorithms.
	public abstract void die() throws DeathException;
	public abstract boolean isPlayer();
	public abstract String handleDeath(DeathException e);
	public abstract String handleKills(DeathException e);
	
	public String name() {
		return name;
	}
	
	protected void loadInvAndBodyAlpha0_1(Scanner scanner) throws GameLoadException {
		this.inv = Inventory.loadAlpha1_0(scanner);
		this.body = Body.loadAlpha1_0fromFile(scanner, this);
	}
	protected static HashMap<String, Integer> loadAlpha0_1stats(Scanner scanner){
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		String[] statsInOrder = new String[]{"str", "agl", "end", "dex", "cha", "anl", "per", "wil", "int", "rec"};
		for (String stat : statsInOrder){
			String strVal = scanner.nextLine();
			int intVal = Integer.parseInt(strVal);
			stats.put(stat, intVal);
		}
		return stats;
	}
	
	public ArrayList<Wound> listAllWounds() {
		ArrayList<Wound> list = new ArrayList<Wound>();
		String[] bodyparts = body.getBodyParts();
		for (int i = 0; i < bodyparts.length; i++) {
			addWoundsToList(list, bodyparts[i]);
		}
		return list;
	}
	private void addWoundsToList(ArrayList<Wound> list, String bodypart) {
		BodyPart limb = body.getBodyPart(bodypart);
		List<Wound> wounds = limb.getInjuries();
		for (int i = 0; i < wounds.size(); i++) {
			list.add(wounds.get(i));
		}
	}
	
	protected static HashMap<String, Integer> loadAlpha0_1damageMultipliers(Scanner scanner){
		HashMap<String, Integer> damageMultipliers = new HashMap<String, Integer>();
		String currentLine = scanner.nextLine();
		while(! currentLine.equals("end damage characteristics")){
			String key = currentLine;
			Integer multiplier = Integer.getInteger(scanner.nextLine());
			currentLine = scanner.nextLine();
			damageMultipliers.put(key, multiplier);
		}
		return damageMultipliers;
	}
	protected static HashMap<String, Skill> loadAlpha0_1skills(Scanner scanner) throws GameLoadException{
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		String line = scanner.nextLine();
		while(! line.equals("end skills")) {
			Skill current = Skill.loadSkillAlpha0_1(scanner);
			skills.put(line, current);
			line = scanner.nextLine();
		}
		return skills;
	}
	
	
	public void saveToFile(PrintWriter writer){
		writer.println(name);
		saveStats(writer);
		saveDamageMultipliers(writer);
		saveSkills(writer);
		inv.saveToFile(writer);
		body.saveToFile(writer);
	}
	protected void saveStats(PrintWriter writer){
		for (int i = 0; i < ABILITIES.length; i++){
			String key = ABILITIES[i];
			writer.println(statsBase.get(key));
		}
	}
	protected void saveDamageMultipliers(PrintWriter writer){
		for(String key : damageMultipliers.keySet()){
			writer.println(key);
			writer.println(damageMultipliers.get(key));
		}
		writer.println("end damage characteristics");
	}
	protected void saveSkills(PrintWriter writer){
		for (String key : skills.keySet()){
			writer.println(key);
			skills.get(key).saveToFile(writer);
		}
		writer.println("end skills");
	}
	
	public int getStat(String stat) {
		return statsAdjusted.get(stat);
	}
	
	public Inventory getInventory(){
		return inv;
	}	
	
	public void recieveWound(int damage, int damageType, String bodypart) throws DeathException{
		body.recieveWound(damage, damageType, bodypart);
	}
	public void recieveWound(int damage, int damageType, int netHits) throws DeathException{
		String bodypart = getRandomWoundLocation(netHits);
		body.recieveWound(damage, damageType, bodypart);
	}
	private String getRandomWoundLocation(int netHits) {
		try {
			return body.getTotalRandomBodyPart();
		} catch (HealthException e) {
			return body.getBodyParts()[0];
		}
	}
	public void passTime(int timePassed, double healingFactor, boolean resting) throws DeathException{
		body.wait(timePassed, getHealingFactor());
	}
	public void bedRest(int timePassed) throws DeathException{
		body.bedRest(timePassed, getHealingFactor());
	}
	public void sleep(int timePassed) throws DeathException{
		body.sleep(timePassed, getHealingFactor());
	}
	protected double getHealingFactor(){
		return (.5 + (statsAdjusted.get("end") * .167));
	}
	
	public void travel(int travelTime, double exhaustionFactor) throws DeathException{
		double weightFactor = ((double) inv.getCarriedWeight());
		weightFactor *= .05;
		weightFactor += 1;
		exhaustionFactor *= weightFactor;
		travelTime *= weightFactor;
		body.travel(travelTime, exhaustionFactor, getHealingFactor());
	}
	public int[] makeAttributeTest(String[] attributes){
		int dicePool = 0;
		for (int i = 0; i < attributes.length; i++){
			dicePool += this.statsAdjusted.get(attributes[i]);
		}
		return DiceRoller.makeRoll(dicePool);
	}
	public int[] makeTest(String testName, int threshold){
		return makeTest(testName, threshold, 0, Integer.MAX_VALUE);
	}
		
	public int[] makeTest(String testName, int threshold, int adjustment, int limit) {
		if(testName.equals("")){
			//TODO add special tests.
		} else if (skills.containsKey(testName)) {
			return skills.get(testName).makeSkillTest(this, threshold, 0);
		} else {
			TextTools.display("ERROR non-existant special test");
		}
		return new int[]{};
	}
	
	@Override
	public boolean equals(Object o) {
		if(! (o instanceof Creature)) {
			return false;
		}
		Creature creature = (Creature) o;
		return (creature.name.equals(this.name)) &&
				(creature.body.equals(this.body)) &&
				(creature.inv.equals(this.inv)) &&
				(statsEqual(creature)) &&
				(damageMultipliersEqual(creature)) &&
				(skillsEqual(creature));
	}
	
	private boolean statsEqual(Creature arg) {
		if (arg.statsBase.size() != this.statsBase.size()) {
			return false;
		}
		for (String key : this.statsBase.keySet()) {
			if (! arg.statsBase.containsKey(key)) {
				return false;
			}
			if (! arg.statsBase.get(key).equals(this.statsBase.get(key))) {
				return false;
			}
		}
		return true;
	}
	private boolean damageMultipliersEqual(Creature arg) {
		if (arg.damageMultipliers.size() != this.damageMultipliers.size()) {
			return false;
		}
		for (String key : this.damageMultipliers.keySet()) {
			if (! arg.damageMultipliers.containsKey(key)) {
				return false;
			}
			if (! arg.damageMultipliers.get(key).equals(this.damageMultipliers.get(key))) {
				return false;
			}
		}
		return true;
	}
	private boolean skillsEqual(Creature arg) {
		if (arg.skills.size() != this.skills.size()) {
			return false;
		}
		for (String key : this.skills.keySet()) {
			if (! arg.skills.containsKey(key)) {
				return false;
			}
			if (! arg.skills.get(key).equals(this.skills.get(key))) {
				return false;
			}
		}
		return true;
	}
	
}
