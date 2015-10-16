package items;

import inventory.Gear;
import inventory.Inventory;
import creatures.Creature;

public class Ammo extends Item{

	private int unitValue; // how many shots a single unit of this ammo is worth.
	
	public Ammo(int unitValue) {
		super(0, 0, 10, 5, 0);
		this.unitValue = unitValue;
	}
	public Ammo() {
		super(0, 0, 10, 5, 0);
		unitValue = 0;
	}

	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}	
	public void specialEffect(Creature target){
		// TODO do nothing by default.
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character) throws Exception {
		throw new Exception("use while exploring unimplemented for this class");
	}
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		System.out.println("Ammo should not be used while equipped");
		// does nothing
	}
}
