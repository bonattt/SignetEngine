package sampleItems;

import java.io.PrintWriter;

import misc.TextTools;
import creatures.Creature;
import inventory.Inventory;
import items.Item;

public class SampleThingy extends Item {

	private static final int SIZE = 20;
	private static final int WEIGHT = 10;
	private static final int DURABILITY = 25;
	private static final int HARDNESS = 3;
	private static final int DAMAGE = 0;
	
	public SampleThingy() {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, "Thingy-ma-bob", "this thingy-ma-bob is very mysterious.");
	}

	@Override
	public void useFromInventory(Inventory inv, Creature player)
			throws Exception {
		TextTools.display("Using this item does nothing.");
	}

	@Override
	public void itemBreaks() {
		// do nothing
	}

	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player,
			int choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveToFile(PrintWriter writer) {
		System.out.println("saveToFile unimplemented in thingy");
	}

}
