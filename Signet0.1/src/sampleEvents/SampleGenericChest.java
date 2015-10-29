package sampleEvents;

import sampleItems.*;
import sampleWeapons.*;
import inventory.ItemContainer;

public class SampleGenericChest extends ItemContainer {

	public static final int WEIGHT = 1000;
	public static final int SIZE = 50000;
	
	public SampleGenericChest(String name) {
		super(WEIGHT, SIZE, name);
		this.addItem(new SampleShirt());
		this.addItem(new SamplePants());
		this.addItem(new SampleAssaultRifle());
		this.addItem(new SampleCombatKnife());
		this.addItem(new SampleHelmet());
		this.addItem(new SampleThingy());
		this.addItem(new SampleSword());
		this.addItem(new SamplePistol());
	}
	public SampleGenericChest() {
		this("generic chest");
	}
	
	
}
