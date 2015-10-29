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
import environment.GameClock;
import bodyparts.*;

public class Body {

	private static final double HEALING_CONSTANT = .005;
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
	
	public int getHealthDamage(){
		return healthDamage;
	}
	public void recieveWound(int damage, int dType, String location) throws DeathException{
		recieveWound(damage, dType, bodyparts.get(location));
	}
	public void recieveWound(int damage, int dType, BodyPart location) throws DeathException{
		painUpToDate = false;
		statModsUpToDate = false;
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
	
	public void passTime(int timePassed, double healingFactor, boolean resting) throws DeathException {
		// healing {health damage, stun damage, fatigue damage}
		painUpToDate = false;
		statModsUpToDate = false;
		GameClock.getInstance().passTime(timePassed);
		int[] damage = new int[3];
		for (Entry<String, BodyPart> current : bodyparts.entrySet()){
			int time = timePassed;
			double[] temp;
			// pass time in 1 hour chunks to allow wounds to heal while damaging player.
			while (time > 1000){
				temp = current.getValue().passTime(1000, healingFactor, resting);
				damage[0] += temp[0];
				damage[1] += temp[1];
				damage[2] += temp[2];
				time -= 1000;
			}
			// pass remaining time
			temp = current.getValue().passTime(time, healingFactor, resting);
			damage[0] += temp[0];
			damage[1] += temp[1];
			damage[2] += temp[2];
		}
		healOverTime(timePassed, healingFactor, resting);
		// TODO known-bug: if a player has a serious wound, they will take all damage at once and die, without factoring in healing.
		updateDamage(damage);
		checkIfDies();
	}
	private void healOverTime(int timePassed, double healingFactor, boolean resting){
		if(resting){
			healingFactor *= 2.5;
		}
		int healing = (int) (healingFactor * timePassed * HEALING_CONSTANT);
		heal(healing, healing, healing);
	}
	
	private void updateDamage(int[] damage){
		healthDamage += damage[0];
		stunDamage += damage[1];
		fatigueActual += damage[2];
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
		if(!statModsUpToDate){
			updateStatMods();
		}
		return statMods;
	}	
	public double getPain() {
		if(!painUpToDate){
			updatePain();
		}
		return painEffective;
	}
	public HashMap<String, BodyPart> getBodyParts() {
		return bodyparts;
	}
	public double getFatigue(){
		return fatigueEffective;
	}
	public void bedRest(int time, double healingFactor) throws DeathException{
		healingFactor *= 1.5;
		try {
			passTime(time, healingFactor, true);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileSleeping = true;
			throw e;
		}
	}
	public void sleep(int time, double healingFactor) throws DeathException{
		try {
			passTime(time, healingFactor, true);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileSleeping = true;
			throw e;
		}
	}
	public void wait(int time, double healingFactor) throws DeathException{
		try {
			passTime(time, healingFactor, false);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileWaiting = true;
			throw e;
		}
	}
	public void travel(int time, double exhaustionFactor, double healingFactor) throws DeathException{
		healingFactor *= .9;
		exhaustion(time, exhaustionFactor);
		calculateEffectiveFatigue();
		try {
			passTime(time, healingFactor, false);
		} catch (DeathException e) {
			e.diedFromBleeding = true;
			e.diedWhileTraveling = true;
			throw e;
		}
	}
	private void exhaustion(int time, double exhaustionFactor){
		fatigueActual += (double) (time * exhaustionFactor);
	}
	private void updateStatMods() {
		statModsUpToDate = true;
		statMods = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for (Entry<String, BodyPart> e : bodyparts.entrySet()){
			int[] temp = e.getValue().getStatMods();
			for (int j = 0; j < statMods.length; j++){
				statMods[j] += temp[j];
			}
		}
	}
	private void updatePain() {
		painUpToDate = true;
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
