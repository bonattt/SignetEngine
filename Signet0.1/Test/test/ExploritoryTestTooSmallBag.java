package test;

import inventory.Inventory;
import inventory.ItemContainer;

import java.lang.reflect.Field;

import testingMothers.CharacterMother;
import testingMothers.SampleGenericChest;
import creatures.Creature;

public class ExploritoryTestTooSmallBag {
	
	public static void main(String[] args) throws Exception {
		Creature player = CharacterMother.getDickDefenderOfLife();
		setPlayerBackpack(player);
		ItemContainer chest = new SampleGenericChest();
		chest.lootDuringExplore(player);
	}
	
	private static void setPlayerBackpack(Creature player) throws Exception {
		Field f = Creature.class.getDeclaredField("inv");
		f.setAccessible(true);
		Inventory inv = (Inventory) f.get(player);
		
		f = Inventory.class.getDeclaredField("backpack");
		f.setAccessible(true);
		ItemContainer newBackpack = new ItemContainer(10, 50, "new backpack");
		f.set(inv, newBackpack);
	}
}

