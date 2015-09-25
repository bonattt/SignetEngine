package inventory;

import misc.CreatureAction;

public abstract class InventoryAction implements CreatureAction{

	private Inventory inv;
	public InventoryAction(Inventory inv){
		this.inv = inv;
	}
}
