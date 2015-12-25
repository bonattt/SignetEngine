package items;

import java.io.PrintWriter;
import java.util.Scanner;

import inventory.Gear;
import inventory.Inventory;
import creatures.Creature;

public class LightSource extends Item {

	public LightSource(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
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
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		throw new Exception("use while exploring unimplemented for this class");
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
	public static LightSource loadAlpha0_1(Scanner scanner) {
		String name = scanner.nextLine();
		int size, weight, durability, hardness, damage;
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		String description = Item.loadItemDescriptionAlpha0_1(scanner);
		LightSource item = new LightSource(size, weight, durability, hardness, damage);
		item.name = name;
		item.description = description;
		return item;
	}
	
	public boolean equals(LightSource light) {
		return baseEqual(light);
	}
	
	public static LightSource loadAlpha0_1fromFile(Scanner scanner) {
		String name = scanner.nextLine();
		int size, weight, durability, hardness, damage;
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		String description = Item.loadItemDescriptionAlpha0_1(scanner);
		LightSource item = new LightSource(size, weight, durability, hardness, damage);
		item.name = name;
		item.description = description;
		return item;
	}
	
}
