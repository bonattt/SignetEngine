package sampleEvents;

import sampleItems.*;
import inventory.ItemContainer;

public class SampleGenericChest extends ItemContainer {

	public static final int WEIGHT = 1000;
	public static final int SIZE = 50000;
	
	public SampleGenericChest(String name) {
		super(WEIGHT, SIZE, name);
		this.addItem(SampleClothing.getSampleShirt());
		this.addItem(SampleClothing.getSamplePants());
		this.addItem(SampleWeapons.getSampleAssaultRifle());
		this.addItem(SampleWeapons.getSampleCombatKnife());
		this.addItem(SampleArmor.getSampleHelmet());
		this.addItem(new SampleThingy());
		this.addItem(SampleWeapons.getSampleSword());
		this.addItem(SampleWeapons.getSamplePistol());
	}
	public SampleGenericChest() {
		this("generic chest");
	}
	
	
}
