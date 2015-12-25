package testingMothers;

import health.DamageType;
import health.Wound;
import items.Bandage;
import items.Ointment;

import java.util.HashMap;

import misc.DeathException;
import misc.TextTools;
import sampleCombatSkills.KnifeSkill;
import sampleItems.*;
import sampleWeapons.*;
import creatures.PlayerCharacter;
import creatures.Skill;
/**
 * Jarred the impaler, object mother character.
 * @author bonattt
 */
public class CharacterMother {
	
	public static PlayerCharacter getDickDefenderOfLife(){
		// TODO implement Jarred
		String creatureName = "Jarred";
		HashMap<String, Integer> baseStats = getJarredStatMap();
		HashMap<String, Integer> damageMultipliers = getJarredDamageMultipliers();
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		skills.put("knives", new KnifeSkill());
		PlayerCharacter dickDefenderOfLife = new PlayerCharacter(creatureName, baseStats, damageMultipliers, skills);
		woundDick(dickDefenderOfLife);
		equipDick(dickDefenderOfLife);
		return dickDefenderOfLife;
	}
	private static void woundDick(PlayerCharacter dickDefenderOfLife){
		try {
			dickDefenderOfLife.recieveWound(4, DamageType.SLASHING, "left arm");
		} catch (DeathException e) {
			TextTools.display("DICK HAS DIED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
	}
	private static void equipDick(PlayerCharacter dickDefenderOfLife){
		dickDefenderOfLife.getInventory().initializeScifiEquipmentSlots(dickDefenderOfLife.getInventory().getEquipment());
		dickDefenderOfLife.getInventory().equipWeapon("holster", new SamplePistol());
		dickDefenderOfLife.getInventory().getEquipment().equipClothing(new SampleShirt());
		dickDefenderOfLife.getInventory().getEquipment().equipArmor(new SampleArmorJacket());
		dickDefenderOfLife.getInventory().pickUpWeapon(new SampleAssaultRifle());
		dickDefenderOfLife.getInventory().store(new SampleThingy());
		dickDefenderOfLife.getInventory().store(new SampleHelmet());
		dickDefenderOfLife.getInventory().store(new SampleCombatKnife());
		dickDefenderOfLife.getInventory().store(new SamplePants());
		dickDefenderOfLife.getInventory().store(new Bandage());
		dickDefenderOfLife.getInventory().store(new Ointment());
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
