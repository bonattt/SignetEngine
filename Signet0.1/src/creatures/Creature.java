package creatures;

import health.Body;
import health.BodyPart;
import health.Wound;
import inventory.Inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	
	public String name;
	
	private Body body;
	private Inventory inv;
	
	private HashMap<String,Integer> stats_base, stats_adjusted;
	private HashMap<String,Integer> damageMultipliers;
	private HashMap<String, Skill> skills;
	
	public Creature(String creatureName, HashMap<String,Integer> baseStats, HashMap<String,Integer> damageMultipliers,
			HashMap<String, Skill> startingSkills, HashMap<String, BodyPart> bodyparts){
		this.name = creatureName;
		this.damageMultipliers = damageMultipliers;
		stats_base = baseStats;
		stats_adjusted = stats_base; // TODO make this a deep copy, then make it actually modify stats.
		skills = startingSkills;
		inv = new Inventory();
		body = new Body(this, bodyparts);
	}
	
	// Selects the combat action that will be used in a fight. PC's prompt for user input, NPC's (Monster) use algorithms.
	public abstract void die() throws DeathException;
	public abstract boolean isPlayer();
	public abstract String handleDeath(DeathException e);
	public abstract String handleKills(DeathException e);
	
	public void loadInvAndBodyAlpha0_1(Scanner scanner) throws GameLoadException {
		this.inv = Inventory.loadAlpha1_0(scanner);
		this.body = Body.loadAlpha1_0fromFile(scanner, this);
	}
	public static HashMap<String, Integer> loadAlpha0_1stats(Scanner scanner){
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		String[] statsInOrder = new String[]{"str", "agl", "end", "dex", "cha", "anl", "per", "wil", "int", "rec"};
		for (String stat : statsInOrder){
			String strVal = scanner.nextLine();
			int intVal = Integer.parseInt(strVal);
			stats.put(stat, intVal);
		}
		return stats;
	}
	public static HashMap<String, Integer> loadAlpha0_1damageMultipliers(Scanner scanner){
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
	public static HashMap<String, Skill> loadAlpha0_1skills(Scanner scanner){
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		String line = scanner.nextLine();
		while(! line.equals("end skills")) {
			Skill current = Skill.loadFromFile(scanner);
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
	public void saveStats(PrintWriter writer){
		for (int i = 0; i < ABILITIES.length; i++){
			String key = ABILITIES[i];
			writer.println(stats_base.get(key));
		}
	}
	public void saveDamageMultipliers(PrintWriter writer){
//		writer.println("damage characteristics");
		for(String key : damageMultipliers.keySet()){
			writer.println(key);
			writer.println(damageMultipliers.get(key));
		}
		writer.println("end damage characteristics");
	}
	public void saveSkills(PrintWriter writer){
//		writer.println("skills");
		for (String key : skills.keySet()){
			writer.println(key);
			skills.get(key).saveToFile(writer);
		}
		writer.println("end skills");
	}
	
	public int getStat(String stat) {
		return stats_adjusted.get(stat);
	}
	
	public ArrayList<Wound> listAllWounds(){
		HashMap<String, BodyPart> bodyparts = body.getBodyParts();
		ArrayList<Wound> injuries = new ArrayList<Wound>();
		for (String key : bodyparts.keySet()){
			BodyPart current = bodyparts.get(key);
			for (int i = 0; i < current.getInjuries().size(); i++){
				injuries.add(current.getInjuries().get(i));
			}
		}
		return injuries;
	}
	
	public HashMap<String, Skill> getSkills(){
		return skills;
	}
	
	public Inventory getInventory(){
		return inv;
	}	
	public int getMight(){
		int might = inv.getWeapon().getMight();
		int[] strengthTest = DiceRoller.makeRoll(stats_adjusted.get("str"));
		// TODO account for glitching.
		might += strengthTest[0];
		return might;
	}
	
	public void recieveWound(int damage, int damageType, String bodypart) throws DeathException{
		body.recieveWound(damage, damageType, bodypart);
	}
	public void recieveWound(int damage, int damageType, BodyPart bodypart) throws DeathException{
		body.recieveWound(damage, damageType, bodypart);
	}
	public HashMap<String, Integer> getStats(){
		return stats_adjusted;
	}
	public void setStat(String key, int value){
		stats_base.put(key, value);
	}
//	private void adjustSingleStat(String key, int modifier){
//		int starting = stats_adjusted.get(key);
//		stats_adjusted.put(key, starting + modifier);
//	}
	public void refreshCombatStats(){
		// TODO implement
	}
	public void wait(int timePassed, double healingFactor, boolean resting) throws DeathException{
		body.wait(timePassed, getHealingFactor());
	}
	public void bedRest(int timePassed) throws DeathException{
		body.bedRest(timePassed, getHealingFactor());
	}
	public void sleep(int timePassed) throws DeathException{
		body.sleep(timePassed, getHealingFactor());
	}
	protected double getHealingFactor(){
		return (.5 + (stats_adjusted.get("end") * .167));
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
			dicePool += this.stats_adjusted.get(attributes[i]);
		}
		return DiceRoller.makeRoll(dicePool);
	}
	public int[] makeTest(String testName, int threshold){
		if(testName.equals("")){
			//TODO add special tests.
		} else {
			TextTools.display("ERROR non-existant special test");
		}
		return skills.get(testName).makeSkillTest(this, threshold);
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
		if (arg.stats_base.size() != this.stats_base.size()) {
			return false;
		}
		for (String key : this.stats_base.keySet()) {
			if (! arg.stats_base.containsKey(key)) {
				return false;
			}
			if (! arg.stats_base.get(key).equals(this.stats_base.get(key))) {
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
