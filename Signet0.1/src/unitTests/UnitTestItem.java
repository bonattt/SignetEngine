package unitTests;

import static org.junit.Assert.*;
import items.Armor;
import items.Item;
import items.LightSource;
import items.MeleeWeapon;

import org.junit.Test;

public class UnitTestItem {

	@Test
	public void testWeaponEquals() {
		String name = "name";
		String desc = "this is a weapon";
		MeleeWeapon weapon1 = new MeleeWeapon(4, 3, 2, 1, name, desc);
		MeleeWeapon weapon2 = new MeleeWeapon(4, 3, 2, 1, name, desc);
		assertTrue(weapon1.equals(weapon2));
		assertTrue(weapon2.equals(weapon1));
		assertTrue(weapon1.equals(weapon1));
	}
	@Test
	public void testWeaponNotEqual() {
		String desc = "this is a weapon";
		String name = "name";
		MeleeWeapon weapon1 = new MeleeWeapon(4, 3, 2, 1, "different", desc);
		MeleeWeapon weapon2 = new MeleeWeapon(4, 3, 2, 1, name, desc);
		assertFalse(weapon1.equals(weapon2));
		assertFalse(weapon2.equals(weapon1));
		
		weapon1 = new MeleeWeapon(4, 3, 2, 1, name, desc);
		weapon2 = new MeleeWeapon(1, 1, 1, 1, name, desc);
		assertFalse(weapon1.equals(weapon2));
		assertFalse(weapon2.equals(weapon1));
		
		weapon1 = new MeleeWeapon(4, 3, 2, 1, name, desc);
		weapon2 = new MeleeWeapon(4, 3, 2, 1, name, "different");
		assertFalse(weapon1.equals(weapon2));
		assertFalse(weapon2.equals(weapon1));
	}
	@Test
	public void testArmorEquals() {
		String name = "name";
		String desc = "this is armor";
		Armor armor1 = new Armor(1, 2, 3, 4, 5, name, desc);
		Armor armor2 = new Armor(1, 2, 3, 4, 5, name, desc);
		assertTrue(armor1.equals(armor2));
		assertTrue(armor2.equals(armor1));
		assertTrue(armor1.equals(armor1));
	}
	@Test
	public void testArmorNotEqual() {
		String name = "name";
		String desc = "this is armor";
		Armor armor1 = new Armor(1, 2, 3, 4, 5, name, desc);
		Armor armor2 = new Armor(5, 4, 3, 2, 1, name, desc);
		assertFalse(armor1.equals(armor2));
		assertFalse(armor2.equals(armor1));
		
		armor1 = new Armor(1, 2, 3, 4, 5, name, desc);
		armor2 = new Armor(1, 2, 3, 4, 5, name, "different");
		assertFalse(armor1.equals(armor2));
		assertFalse(armor2.equals(armor1));
		
		armor1 = new Armor(1, 2, 3, 4, 5, "different", desc);
		armor2 = new Armor(1, 2, 3, 4, 5, name, desc);
		assertFalse(armor1.equals(armor2));
		assertFalse(armor2.equals(armor1));
	}
	@Test
	public void testLightSourceEquals() {
		String name = "name";
		String desc = "this is light";
		LightSource light1 = new LightSource(1, 2, 3, 4, 5, name, desc);
		LightSource light2 = new LightSource(1, 2, 3, 4, 5, name, desc);
		assertTrue(light1.equals(light2));
		assertTrue(light2.equals(light1));
	}
	@Test
	public void testLightSourceEqualsSelf() {
		String name = "name";
		String desc = "this is light";
		LightSource light1 = new LightSource(1, 2, 3, 4, 5, name, desc);
		assertTrue(light1.equals(light1));
	}
	@Test
	public void testLightSourceNotEqual() {
		String name = "name";
		String desc = "this is light";
		LightSource light1 = new LightSource(1, 2, 3, 4, 5, name, desc);
		LightSource light2 = new LightSource(5, 4, 3, 2, 1, name, desc);
		assertFalse(light1.equals(light2));
		assertFalse(light2.equals(light1));
		
		light1 = new LightSource(5, 4, 3, 2, 1, "different", desc);
		light2 = new LightSource(5, 4, 3, 2, 1, name, desc);
		assertFalse(light1.equals(light2));
		assertFalse(light2.equals(light1));
		
		light1 = new LightSource(5, 4, 3, 2, 1, name, desc);
		light2 = new LightSource(5, 4, 3, 2, 1, name, "differnt");
		assertFalse(light1.equals(light2));
		assertFalse(light2.equals(light1));
	}
}
