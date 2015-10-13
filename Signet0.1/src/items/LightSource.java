package items;

import inventory.Gear;
import inventory.Inventory;
import creatures.Creature;

public abstract class LightSource extends Item {

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
}
