package unitTests;

import static org.junit.Assert.*;
import items.Armor;
import items.Item;
import items.LightSource;
import items.Weapon;

import org.junit.Test;

import sampleItems.SampleShirt;
import sampleWeapons.SampleSword;

public class UnitTestItem {

	@Test
	public void testWeaponEquals() {
		Weapon weapon1 = new Weapon(4, 3, 2, 1);
		weapon1.name = "name";
		Weapon weapon2 = new Weapon(4, 3, 2, 1);
		weapon2.name = "name";
		assertTrue(weapon1.equals(weapon2));
		assertTrue(weapon2.equals(weapon1));
		assertTrue(weapon1.equals(weapon1));
	}
	@Test
	public void testWeaponNotEqual() {
		Weapon weapon1 = new Weapon(4, 3, 2, 1);
		weapon1.name = "different";
		Weapon weapon2 = new Weapon(4, 3, 2, 1);
		weapon2.name = "name";
		assertFalse(weapon1.equals(weapon2));
		assertFalse(weapon2.equals(weapon1));
		
		weapon1 = new Weapon(4, 3, 2, 1);
		weapon2 = new Weapon(1, 1, 1, 1);
		assertFalse(weapon1.equals(weapon2));
		assertFalse(weapon2.equals(weapon1));
		
		weapon1 = new Weapon(4, 3, 2, 1);
		weapon2 = new Weapon(4, 3, 2, 1);
		weapon2.description = "different";
		assertFalse(weapon1.equals(weapon2));
		assertFalse(weapon2.equals(weapon1));
		
		
	}
	@Test
	public void testArmorEquals() {
		Armor armor1 = new Armor(1, 2, 3, 4, 5);
		Armor armor2 = new Armor(1, 2, 3, 4, 5);
		assertTrue(armor1.equals(armor2));
		assertTrue(armor2.equals(armor1));
		assertTrue(armor1.equals(armor1));
	}
	@Test
	public void testArmorNotEqual() {
		Armor armor1 = new Armor(1, 2, 3, 4, 5);
		Armor armor2 = new Armor(5, 4, 3, 2, 1);
		assertFalse(armor1.equals(armor2));
		assertFalse(armor2.equals(armor1));
		
		armor1 = new Armor(1, 2, 3, 4, 5);
		armor2 = new Armor(1, 2, 3, 4, 5);
		armor2.name = "different";
		assertFalse(armor1.equals(armor2));
		assertFalse(armor2.equals(armor1));
		
		armor1 = new Armor(1, 2, 3, 4, 5);
		armor1.description = "different";
		armor2 = new Armor(1, 2, 3, 4, 5);
		assertFalse(armor1.equals(armor2));
		assertFalse(armor2.equals(armor1));
	}
	@Test
	public void testLightSourceEquals() {
		LightSource light1 = new LightSource(1, 2, 3, 4, 5);
		LightSource light2 = new LightSource(1, 2, 3, 4, 5);
		assertTrue(light1.equals(light2));
		assertTrue(light2.equals(light1));
	}
	@Test
	public void testLightSourceEqualsSelf() {
		LightSource light1 = new LightSource(1, 2, 3, 4, 5);
		assertTrue(light1.equals(light1));
	}
	@Test
	public void testLightSourceNotEqual() {
		LightSource light1 = new LightSource(1, 2, 3, 4, 5);
		LightSource light2 = new LightSource(5, 4, 3, 2, 1);
		assertFalse(light1.equals(light2));
		assertFalse(light2.equals(light1));
		
		light1 = new LightSource(1, 2, 3, 4, 5);
		light1.name = "this is not the same";
		light2 = new LightSource(5, 4, 3, 2, 1);
		light2.name = "different";
		assertFalse(light1.equals(light2));
		assertFalse(light2.equals(light1));
		
		light1 = new LightSource(1, 2, 3, 4, 5);
		light1.description = "this is not the same";
		light2 = new LightSource(5, 4, 3, 2, 1);
		light2.description = "this is different";
		assertFalse(light1.equals(light2));
		assertFalse(light2.equals(light1));
	}
}
