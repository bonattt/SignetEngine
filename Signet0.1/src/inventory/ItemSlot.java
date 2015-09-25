package inventory;

import misc.TextTools;
import items.CombatItem;

/**
 * Weapon slots are availible for storing weapon in. A given weapon slot has restrictions on how big of an item can be stored there, and 
 * contains stats for how easily an item can be concealed there.
 * @author bonattt
 *
 */
public class ItemSlot {
	
	public String name;
	
	private int capacity;
	private int concealability;
	private CombatItem itemStored;
	
	public ItemSlot(String name, int capacity, int concealability){
		this.name = name;
		this.capacity = capacity;
		this.concealability = concealability;
	}
	public CombatItem getItem(){
		return itemStored;
	}
	public int getWeight(){
		if (itemStored == null){
			return 0;
		}
		return itemStored.getWeight();
	}
	
	public boolean isEmpty(){
		return (itemStored == null);
	}
	public boolean addItem(CombatItem item){
		if (isEmpty()){
			itemStored = item;
			return true;
		}
		TextTools.display("You are already storing a " + itemStored.getName() + " on your " + name);
		return false;
	}
	
	public CombatItem replaceItemWith(CombatItem item){
		if(isEmpty()){
			itemStored = item;
			TextTools.display("there was nothing to replace.");
			return null;
		}
		CombatItem oldItem = itemStored;
		itemStored = item;
		TextTools.display("You replaced the "+ oldItem.getName() + " with a " + item.getName() + " on your " + name); 
		return oldItem;
	}
//	public CombatAction[] getActions(){
//		if(itemStored.needsToBeHeld()){
//			return new CombatAction[]{};
//		}
//		return itemStored.getActions();
//	}
}
