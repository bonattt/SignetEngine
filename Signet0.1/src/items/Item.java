package items;

import creatures.Creature;
import inventory.Gear;
import inventory.Inventory;
import inventory.ItemContainer;
import misc.DiceRoller;
import misc.TextTools;

public abstract class Item {
	
	private int size;		// measured in ??
	private int weight;		// measured in grams
	private int durability; // how much damage the item can take before breaking.
	private int hardness;	// items resistance to damage.
	private int damage;		// damage dealt to the item's durability.
	private int[] args;
	public String name;
	public String description;
	
	public Item (int size, int wt, int dur, int hard, int dam){
		this.size = size;
		weight = wt;
		durability = dur;
		hardness = hard;
		damage = dam;
	}
	public void inspect(Creature player) {
		TextTools.display(description);
		// TODO add a pause in text reading here.
	}
	public abstract void useFromInventory(Inventory inv, Creature player) throws Exception;
	public abstract void handleUseWhileEquipped(Inventory inv, Creature player, int choice);
	
	public String toString(){
		return name;
	}
	
	public void useWhileEquipped(Inventory inv, Creature player){
		String question = "What would you like to do withj the " + name + " you have equipped?";
		String[] answers = new String[]{"stow", "discard", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice != 0){
			handleUseWhileEquipped(inv, player, choice);
		}
	}
	public void useFromContainer(Inventory inv, Creature player, ItemContainer container) {
		String question = "What would you like to do with the " + name;
		String[] answers = new String[]{"pick-up", "discard", "inspect", "cancel"};
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		} else if (choice == 1) {
			if(inv.spaceRemaining() >= this.getSize()){
				inv.store(this);
				container.removeItem(this);
			} else {
				TextTools.display("You do not have enough room for that");
			}
		} else if (choice == 2) {
			container.removeItem(this);
		} else if (choice == 3) {
			this.inspect(player);
		} else {
			// TODO throw an Exception
		}
		 
	}
	
	public boolean canBeUsedInExplore(){
		return true;
	}
	public boolean isExpendible(){
		return false;
	}
	public boolean canBeUsedInCombat(){
		return false;
	}
	public boolean isWeapon(){
		return false;
	}
	public boolean isRangedWeapon(){
		return false;
	}
	public boolean isMeleeWeapon(){
		return false;
	}
	public boolean isDepletable(){
		return false;
	}
	public boolean isClothing(){
		return false;
	}
	public boolean isArmor(){
		return false;
	}
	public boolean isEquipment(){
		return false;
	}
	public boolean isLightSource(){
		return false;
	}
	public boolean isFirstAid(){
		return false;
	}
	
	public int getSize(){
		return size;
	}
	public int getWeight(){
		return weight;
	}
	public String checkDamage(){
		return "";
	}
	
	public boolean resistDamage(int damage, int armorPiercing) {
		//TODO handle items taking damage 
		args = new int[]{durability, 1};
		// run special abilities.
		int dicePool = (int) args[0];
		double multiplier = (double) args[1];
		int hits = DiceRoller.makeRoll(dicePool)[0];
		int damageDealt = (int) Math.ceil(damage - hits);
		if (damageDealt < 0){
			damageDealt = 0;
		}
		damage += damageDealt;
		if (damage >= durability){
			itemBreaks();
			return true;
		}
		return false;
	}
	
	
	public abstract void itemBreaks();
}
