package items;

import inventory.Gear;
import inventory.Inventory;
import creatures.Creature;

public class AmmoClip<T extends Ammo> extends Item{

	private static final int DEFAULT_DURABILITY = 50;
	private static final int DEFAULT_HARDNESS = 5;
	
	private int ammoCapacity, ammoRemaining;
	
	public AmmoClip(int size, int ammoCapacity, int startingAmmo, int dam) {
		super(size, size/2, DEFAULT_DURABILITY, DEFAULT_HARDNESS, dam);
		this.ammoCapacity = ammoCapacity;
		ammoRemaining = startingAmmo;
	}
	public AmmoClip(int size, int ammoCapacity){
		super(size, size/2, DEFAULT_DURABILITY, DEFAULT_HARDNESS, 0);
		this.ammoCapacity = ammoCapacity;
		ammoRemaining = 0;
	}

	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		System.out.println("Ammo should not be used while equipped");
		// does nothing
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		throw new Exception("use while exploring unimplemented for this class");
	}
}
