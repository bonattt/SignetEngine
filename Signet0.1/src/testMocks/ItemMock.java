package testMocks;

import java.io.PrintWriter;

import creatures.Creature;
import inventory.Gear;
import inventory.Inventory;
import items.Item;

public class ItemMock extends Item {

	public ItemMock(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);

	}
	public ItemMock(){
		super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
	}

	@Override
	public void itemBreaks() {

		
	}
	public void useWhileExploring(Gear equipment, Creature character)
			throws Exception {
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player,
			int choice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveToFile(PrintWriter writer) {
		System.out.println("ERROR - item mock cannot be saved");
	}

}
