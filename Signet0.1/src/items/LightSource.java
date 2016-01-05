package items;

import java.io.PrintWriter;
import java.util.Scanner;

import inventory.Inventory;
import inventory.InventoryException;
import creatures.Creature;

public class LightSource extends Item {

	public LightSource(int size, int wt, int dur, int hard, int dam, String name, String description) {
		super(size, wt, dur, hard, dam, name, description);
	}

	@Override
	public boolean isLightSource(){
		return true;
	}
	@Override
	public boolean isEquipment(){
		return true;
	}

	@Override
	public void useFromInventory(Inventory inv, Creature character)  throws InventoryException {
		throw new InventoryException("use while exploring unimplemented for this class");
	}
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		// TODO 
	}
	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("light source");
		saveBaseStats(writer);
	}
	public static LightSource loadLightSourceAlpha0_1(Scanner scanner) {
		int size, weight, durability, hardness, damage;
		String name = scanner.nextLine();
		String description = Item.loadLongStringAlpha0_1(scanner);
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		scanner.nextLine(); // realign the scanner to read strings after .nextInt()
		LightSource item = new LightSource(size, weight, durability, hardness, damage, name, description);
		return item;
	}
	
	@Override
	public boolean equals(Object arg) {
		if (! (arg instanceof LightSource)) {
			return false;
		}
		LightSource item = (LightSource) arg;
		return (item.baseEquals(this));
	}
	
	public boolean equals(LightSource light) {
		return baseEquals(light);
	}
	
	public static LightSource loadAlpha0_1fromFile(Scanner scanner) {
		String name = scanner.nextLine();
		int size, weight, durability, hardness, damage;
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		String description = Item.loadLongStringAlpha0_1(scanner);
		LightSource item = new LightSource(size, weight, durability, hardness, damage, name, description);
		return item;
	}
	
}
