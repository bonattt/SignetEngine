package health;

import items.Armor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import misc.DeathException;
import creatures.Creature;
import bodyparts.*;

public class Body {

	private static final double HEALING_CONSTANT = .1;
	private static final String[][] RANDOM_WOUND_TABLE = new String[][]{
		{"right hand", "left hand", "right foot", "left foot", "right arm", "left arm", "right hand", "left hand", "right hand", "left hand"},
		{"right hand", "left hand", "right hand", "left arm", "right arm", "left arm", "right hand", "left hand", "right arm", "left arm"},
		{"right arm", "left arm", "left shoulder", "right shoulder", "belly", "right leg", "left leg", "right arm", "left arm", "belly"},
		{"right arm", "left arm", "belly", "chest", "right shoulder", "left shoulder", "belly", "chest", "right arm", "head"},
		{"belly", "belly", "belly", "belly", "chest", "chest", "chest", "head", "head", "head"},
		{"belly", "chest", "head", "head", "head", "head", "head", "head", "head", "head"}		
	};
		
	private Creature creature;
	private int healthDamage, stunDamage;
	private double fatigueActual, fatigueEffective, painActual, painEffective;
	private int[] statMods;
	private ArrayList<Affliction> afflictions = new ArrayList<Affliction>();
	private boolean painUpToDate, statModsUpToDate;
	
	public HashMap<String, BodyPart> bodyparts;
	
	public Body(Creature c){
		creature = c;
		fatigueActual = 1;
		fatigueEffective = 1;
		painActual = 1;
		painEffective = 1;
		healthDamage = 0;
		stunDamage = 0;
		
		bodyparts = new HashMap<String, BodyPart>();
		bodyparts.put("head", new Head("head"));
		bodyparts.put("back", new UpperBody("back"));
		bodyparts.put("chest", new UpperBody("chest"));
		bodyparts.put("belly", new UpperBody("belly"));
		bodyparts.put("left arm", new Arm("left arm"));
		bodyparts.put("right arm", new Arm("right arm"));
		bodyparts.put("left leg", new Leg("left leg"));
		bodyparts.put("right leg", new Leg("right leg"));
		bodyparts.put("left hand", new Hand("left hand"));
		bodyparts.put("right hand", new Hand("right hand"));
		bodyparts.put("left foot", new Foot("left foot"));
		bodyparts.put("right foot", new Foot("right foot"));
		bodyparts.put("right shoulder", new Shoulder("right shoulder"));
		bodyparts.put("left shoulder", new Shoulder("left shoulder"));
	}
	
//	public Armor[] addArmor(Armor armor, String armorSlot){
//		HashMap<String, String[]> map = getArmorSlotBodyPartIndex();
//		if (!map.containsKey(armorSlot)){
//			throw new IllegalArgumentException("you used a non existant armor slot or an armor slot that does not exist in your current setting");
//		}
//		
//		String[] bodyPartsToArmor = map.get(armorSlot);
//		Set<Armor> armorToBag = (Set<Armor>) (new CopyOnWriteArrayList<Armor>());
//		for (int i = 0; i < bodyPartsToArmor.length; i++){
//			BodyPart temp = bodyparts.get(bodyPartsToArmor[i]);
//			if (temp.hasWornArmor()){
//				armorToBag.add(temp.getArmor());
//			}
//			temp.setArmor(armor);
//		}
//		// TODO VERIFY THIS!!
//		return armorToBag.toArray(new Armor[0]);
//	}
	
//	public HashMap<String, String[]> getArmorSlotBodyPartIndex(){
//		HashMap<String, String[]> map = new HashMap<String, String[]>();
//		map.put("helmet", new String[]{"head"});
//		map.put("gloves", new String[]{"right hand", "left hand"});
//		map.put("jacket", new String[]{"chest", "belly","back","left shoulder","right shoulder","left arm","right arm"});
//		map.put("vest", new String[]{"chest", "belly","back"});
//		map.put("boots", new String[]{"left foot", "right foot"});
//		map.put("greaves", new String[]{"left leg", "right leg"});
//		return map;
//	}
	public int getHealthDamage(){
		return healthDamage;
	}
	
	public void recieveWound(int damage, int dType, BodyPart location) throws DeathException{
		try{
			if(damage > 7){
				damage = 7;
			}
			// instant damage {health damage, stun damage, fatigue damage}
			int[] instantDamage = Wound.addNewWound(damage, dType, location);
			takeHealthDamageDontRecalculate(instantDamage[0]);
			takeStunDamageDontRecalculate(instantDamage[1]);
			takeSteminaDamageDontRecalculate(instantDamage[2]);
		} catch (DeathException e){
			e.diedFromInstantDamage = true;
			throw e;
		}
	}
	
//	private BodyPart determineWoundLocation(int netHits){
//		Random r = new Random();
//		if (netHits >= RANDOM_WOUND_TABLE.length){
//			netHits = RANDOM_WOUND_TABLE.length;
//		}
//		String bodStr = RANDOM_WOUND_TABLE[netHits][r.nextInt(RANDOM_WOUND_TABLE[0].length)];
//		return bodyparts.get(bodStr);
//	}
	
	public int[] passTime(int timePassed, double healingFactor, boolean resting) throws DeathException {
		// healing {health damage, stun damage, fatigue damage}
		int[] healing = new int[3];
		for (Entry<String, BodyPart> current : bodyparts.entrySet()){
			double[] temp;
			// pass time in 1 hour chunks to allow wounds to heal while damaging player.
			while (timePassed > 1000){
				temp = current.getValue().passTime(1000, healingFactor, resting);
				healing[0] += temp[0];
				healing[1] += temp[1];
				healing[2] += temp[2];
			}
			// pass remaining time
			temp = current.getValue().passTime(timePassed, healingFactor, resting);
			healing[0] += temp[0];
			healing[1] += temp[1];
			healing[2] += temp[2];
		}
		checkIfDies();
		return healing;
	}
	public int getStunDamage(){
		return stunDamage;
	}
	public void takeHealthDamage(int damage) throws DeathException{
		takeHealthDamageDontRecalculate(damage);
		updatePain();
	}
	private void checkIfDies() throws DeathException{
		if ( healthDamage >= (70 + (creature.getStats().get("end")*5)) ){
			creature.die();
		}
	}
	public void takeStunDamage(int damage){
		takeStunDamageDontRecalculate(damage);
		calculateEffectivePain();
	}
	public void takeSteminaDamage(double fatigue){
		takeSteminaDamageDontRecalculate(fatigue);
		calculateEffectiveFatigue();
	}
	private void takeHealthDamageDontRecalculate(int damage) throws DeathException{
		healthDamage += damage;
		checkIfDies();
	}
	private void takeStunDamageDontRecalculate(int damage){
		stunDamage += damage;
	}
	private void takeSteminaDamageDontRecalculate(double fatigue){
		fatigueActual += fatigue;
	}
	public void healDamage(int healing){
		heal(healing, 0, 0);
	}
	public void healStun(int healing){
		heal(0, healing, 0);
	}
	public void healFatigue(int healing){
		heal(0, 0, healing);
	}
//	public void healWound(int healing, String key){
//		//TODO
//		calculateEffectivePain();
//	}
	
	public void heal(int healthHealed, int stunHealed, double fatigueHealed){
		healthDamage -= healthHealed;
		stunDamage -= stunHealed;
		fatigueActual -= fatigueHealed;
		
		if (healthDamage < 0) {
			healthDamage = 0;
		} if (stunDamage < 0) {
			stunDamage = 0;
		} if (fatigueActual < 1) {
			fatigueActual = 1;
		}
	}
	public int[] getStatMods() {
		return statMods;
	}	
	public double getPain() {
		return painEffective;
	}
	public HashMap<String, BodyPart> getBodyParts() {
		return bodyparts;
	}
	public double getFatigue(){
		return fatigueEffective;
	}
	public void bedRest(int time) throws DeathException{
		// TODO set healing factor for rest
		double healingFactor = -1;
		restGeneral(time, healingFactor);
		try {
			passTime(time, healingFactor, true);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileSleeping = true;
			throw e;
		}
	}
	public void sleep(int time) throws DeathException{
		//TODO set healing factor for sleep
		double healingFactor = -1;
		restGeneral(time, healingFactor);
		try {
			passTime(time, healingFactor, true);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileSleeping = true;
			throw e;
		}
	}
	public void wait(int time) throws DeathException{
		//TODO set healing factor for wait
		double healingFactor = -1;
		try {
			passTime(time, healingFactor, false);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileWaiting = true;
			throw e;
		}
	}
	public void travel(int time, int exhaustionFactor) throws DeathException{
		// TODO set healing factor for travel
		double healingFactor = -1;
		exhaustion(time, exhaustionFactor);
		try {
			passTime(time, healingFactor, false);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileTraveling = true;
			throw e;
		}
	}
	private void exhaustion(int time, int exhaustionFactor){
		double fatigueRate = 0.1875;
		fatigueActual += (double) (time * exhaustionFactor);
	}
	
	private void restGeneral(int timePassed, double healingFactor){
		//TODO
	}
	
	private void updateStatMods() {
		statMods = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for (Entry<String, BodyPart> e : bodyparts.entrySet()){
			int[] temp = e.getValue().getStatMods();
			for (int j = 0; j < statMods.length; j++){
				statMods[j] += temp[j];
			}
		}
	}
	private void updatePain() {
		painActual = 1;
		for (Entry<String, BodyPart> current : bodyparts.entrySet()){
			painActual += current.getValue().getPain();
		}
		if (painActual < 1){
			painActual = 1;
		}
		calculateEffectivePain();
	}
	private void calculateEffectivePain(){
		double painTollerance = (creature.getStats().get("wil")*.15 + creature.getStats().get("end")*.075);
		painEffective = painActual - painTollerance;
		if (painEffective < 1){
			painEffective = 1;
		}
	}
	
	public void updateFatigue(int timePassed, double exhaustionFactor) {
		fatigueActual += (double) (timePassed * exhaustionFactor) / 10.0; 
		calculateEffectiveFatigue();
	}
	private void calculateEffectiveFatigue(){
		double fatigueTollerance = (creature.getStats().get("end")*.15 + creature.getStats().get("str")*.075);
		fatigueEffective = fatigueActual - fatigueTollerance;
		if (fatigueEffective < 1){
			fatigueEffective = 1;
		}
	}
	public int countWounds(){
		int count = 0;
		for (String key : bodyparts.keySet()){
			count += bodyparts.get(key).injuries.size();
		}
		return count;
	}
}
