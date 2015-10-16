package sampleItems;

import items.WornItem;

public class SampleShirt extends WornItem {

	private static final int SIZE = 100;
	private static final int WEIGHT = 5;
	private static final int DURABILITY = 10;
	private static final int HARDNESS = 0;
	private static final int DAMAGE = 0;
	
	public SampleShirt() {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE);
		slot = "shirt";
		name = "sample shirt";
	}

	@Override
	public void itemBreaks() {
		// do nothing
	}
	
}
