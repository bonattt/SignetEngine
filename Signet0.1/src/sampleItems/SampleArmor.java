package sampleItems;

import java.util.HashMap;

import items.Armor;

public class SampleArmor {

	public static Armor getSampleArmorJacket() {
		Armor armor = new Armor(200, 100, 100, 100, 1, "main-armor", "sample armor jacket", "this is an armored jacket");
		armor.initializeArmorStats("armor", getDamageResistanceTableArmorJacket(), getTypeConversionTableArmorJacket());
		return armor;
	}
	private static HashMap<Integer, Integer> getDamageResistanceTableArmorJacket(){
		HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
		
		
		return table;
	}
	private static HashMap<Integer, Integer> getTypeConversionTableArmorJacket(){
		return new HashMap<Integer, Integer>();
	}
	
	public static Armor getSampleHelmet() {
		Armor armor = new Armor(200, 100, 100, 100, 0, "helmet", "sample helmet", "this is a metal helmet");
		armor.initializeArmorStats("helmet", getDamageResistanceTableHelmet(), getTypeConversionTableHelmet());
		return armor;
	}
	private static HashMap<Integer, Integer> getDamageResistanceTableHelmet(){
		HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
		
		return table;
	}
	private static HashMap<Integer, Integer> getTypeConversionTableHelmet(){
		return new HashMap<Integer, Integer>();
	}
}
