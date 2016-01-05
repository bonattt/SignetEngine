package items;

import inventory.Inventory;
import inventory.InventoryException;

import java.io.PrintWriter;
import java.util.Scanner;

import misc.TextTools;
import creatures.Creature;

public class StoryItem extends Item {

	private String useString;
	
	public StoryItem(int size, int wt, int dur, int hard, int dam, String name,
			String description, String useString) {
		super(size, wt, dur, hard, dam, name, description);
		this.useString = useString;
	}

	@Override
	public void useFromInventory(Inventory inv, Creature player)
			throws InventoryException {
		TextTools.display(useString);
	}

	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player,
			int choice) throws InventoryException {
		throw new InventoryException("StoryItems cannot be equipped.");
	}

	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("story item");
		saveBaseStats(writer);
		saveLongStringToFile(writer, useString);
	}
	
	public static StoryItem loadStoryItemAlpha0_1(Scanner scanner) {
		String name = scanner.nextLine();
		String desc = loadLongStringAlpha0_1(scanner);
		int size = scanner.nextInt();
		int wt = scanner.nextInt();
		int dur = scanner.nextInt();
		int hard = scanner.nextInt();
		int dam = scanner.nextInt();
		String useString = loadLongStringAlpha0_1(scanner);
		
		StoryItem item = new StoryItem(size, wt, dur, hard, dam, name, desc, useString);
		return item;
	}
	
	@Override
	public boolean equals(Object o) {
		if(! (o instanceof StoryItem)) {
			return false;
		}
		StoryItem item = (StoryItem) o;
		return (item.baseEquals(this)) &&
				(item.useString.equals(this.useString));
	}
}
