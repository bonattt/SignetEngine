package test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import misc.DeathException;
import mothers.CharacterMother;
import mothers.TestMonster;
import combat.*;
import inventory.Inventory;
import inventory.ItemContainer;
import items.*;
import creatures.*;
import sampleCombatActions.*;
import sampleWeapons.SampleSword;

public class CombatTestRun {
	
	public static void main (String[] args){
		
		PlayerCharacter player = CharacterMother.getDickDefenderOfLife();
		Creature monster = new TestMonster();
		Combat combat = Combat.newCombat(monster);
		
		try {
			Field creatureInv = Creature.class.getDeclaredField("inv");
			creatureInv.setAccessible(true);
			Field weapon = Inventory.class.getDeclaredField("equippedWeapon");
			weapon.setAccessible(true);

			Inventory temp = (Inventory) creatureInv.get(player);
			weapon.set(temp, new SampleSword());
			
			temp = (Inventory) creatureInv.get(monster);
			weapon.set(temp, new SampleSword());
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			System.out.println("fail");
		} catch (SecurityException e) {
			e.printStackTrace();
			System.out.println("fail");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("fail");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("fail");
		}
		
		try {
			combat.triggerEvent(player);
			System.out.print("The player killed the monster, huzah!!");
		} catch (DeathException e) {
			System.out.print("The player was killed, ohs noes!!");
		}
	}
}
