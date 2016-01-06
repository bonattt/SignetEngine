package testingMothers;

import health.BodyPart;
import health.DamageType;
import health.Wound;
import inventory.InventoryException;
import items.Bandage;
import items.Ointment;

import java.util.HashMap;

import misc.DeathException;
import misc.TextTools;
import creatures.PlayerCharacter;
import creatures.Skill;
/**
 * Jarred the impaler, object mother character.
 * @author bonattt
 */
public class CharacterMother {
	
	public static PlayerCharacter getDickDefenderOfLife() throws InventoryException{
		// TODO implement Jarred
		String creatureName = "Jarred";
		HashMap<String, Integer> baseStats = getJarredStatMap();
		HashMap<String, Integer> damageMultipliers = getJarredDamageMultipliers();
		HashMap<String, Skill> skills = getJarredSkills();
		PlayerCharacter dickDefenderOfLife = new PlayerCharacter(creatureName, baseStats,
				damageMultipliers, skills, getBodyParts());
		woundDick(dickDefenderOfLife);
		equipDick(dickDefenderOfLife);
		return dickDefenderOfLife;
	}
	private static HashMap<String, BodyPart> getBodyParts() {

		HashMap<String,BodyPart> bodyparts = new HashMap<String, BodyPart>();
		bodyparts.put("head", SampleBodyparts.head("head"));
		bodyparts.put("back", SampleBodyparts.upperBody("back"));
		bodyparts.put("chest", SampleBodyparts.upperBody("chest"));
		bodyparts.put("belly", SampleBodyparts.upperBody("belly"));
		bodyparts.put("left arm", SampleBodyparts.arm("left arm"));
		bodyparts.put("right arm", SampleBodyparts.arm("right arm"));
		bodyparts.put("left leg", SampleBodyparts.leg("left leg"));
		bodyparts.put("right leg", SampleBodyparts.leg("right leg"));
		bodyparts.put("left hand", SampleBodyparts.hand("left hand"));
		bodyparts.put("right hand", SampleBodyparts.hand("right hand"));
		bodyparts.put("left foot", SampleBodyparts.foot("left foot"));
		bodyparts.put("right foot", SampleBodyparts.foot("right foot"));
		bodyparts.put("right shoulder", SampleBodyparts.shoulder("right shoulder"));
		bodyparts.put("left shoulder", SampleBodyparts.shoulder("left shoulder"));
		return bodyparts;
	}
	
	private static void woundDick(PlayerCharacter dickDefenderOfLife){
		try {
			dickDefenderOfLife.recieveWound(4, DamageType.SLASHING, "left arm");
		} catch (DeathException e) {
			TextTools.display("DICK HAS DIED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
	}
	private static void equipDick(PlayerCharacter dickDefenderOfLife) throws InventoryException {
		dickDefenderOfLife.getInventory().equipWeapon("boot-holster", SampleWeapons.getSamplePistol());
		dickDefenderOfLife.getInventory().getEquipment().equipClothing(SampleClothing.getSampleShirt());
		dickDefenderOfLife.getInventory().getEquipment().equipArmor(SampleArmor.getSampleArmorJacket());
		dickDefenderOfLife.getInventory().pickUpWeapon(SampleWeapons.getSampleAssaultRifle());
		dickDefenderOfLife.getInventory().store(SampleItems.getMysticThingy());
		dickDefenderOfLife.getInventory().store(SampleArmor.getSampleHelmet());
		dickDefenderOfLife.getInventory().store(SampleWeapons.getSampleCombatKnife());
		dickDefenderOfLife.getInventory().store(SampleClothing.getSamplePants());
		dickDefenderOfLife.getInventory().store(new Bandage());
		dickDefenderOfLife.getInventory().store(new Ointment());
	}
	
	private static HashMap<String, Skill> getJarredSkills() {
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		skills.put("knives", SampleSkills.knives(2));
		skills.put("electronics", SampleSkills.electronics(3));
		return skills;
	}
	
	private static HashMap<String,Integer> getJarredStatMap(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("str", 12);	// strength
		map.put("agl", 10);	// agility
		map.put("end", 10);	// endurance
		map.put("dex", 4);	// dexterity
		map.put("cha", 1);	// Charisma
		map.put("anl", 4);	// analysis
		map.put("per", 6);	// perception
		map.put("wil", 12);	// willpower
		map.put("int", 8);	// intuition
		map.put("rec", 8);	// reaction
		return map;
	}
	private static HashMap<String, Integer> getJarredDamageMultipliers(){
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		return ret;
	}
}
