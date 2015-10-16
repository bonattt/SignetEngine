package sampleItems;

import items.WornItem;

public class SamplePants extends WornItem {

	private static final int SIZE = 100;
	private static final int WEIGHT = 5;
	private static final int DURABILITY = 10;
	private static final int HARDNESS = 0;
	private static final int DAMAGE = 0;
	
	public SamplePants() {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE);
		slot = "pants";
		name = "sample pants";
	}

	@Override
	public void itemBreaks() {
		// do nothing
	}
	
}
