package inventory;

import items.Item;

import java.util.ArrayList;

public class Bag extends ItemContainer {

	public static final int STARTING_BAG_SIZE = 1000;
	public static final int STARTING_BAG_WEIGHT = 10;
	
	public Bag(){
		super(STARTING_BAG_WEIGHT, STARTING_BAG_SIZE, "backpack");
	}

}
