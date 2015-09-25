package items;

import misc.DiceRoller;

public abstract class Item {
	
	private int size;		// measured in ??
	private int weight;		// measured in grams
	private int durability; // how much damage the item can take before breaking.
	private int hardness;	// items resistance to damage.
	private int damage;		// damage dealt to the item's durability.
	private int[] args;
	public String name;
	
	public Item (int size, int wt, int dur, int hard, int dam){
		this.size = size;
		weight = wt;
		durability = dur;
		hardness = hard;
		damage = dam;
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
