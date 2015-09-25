package items;

import java.util.ArrayList;
import java.util.HashMap;

import misc.TextTools;
import combat.Combat;
import combat.CombatAction;

public abstract class Weapon extends Item implements CombatItem {

	public static final int LIGHT = 0;
	public static final int ONE_HANDED = 1;
	public static final int TWO_HANDED = 2;
	
	private HashMap<String, Integer> utility;
	private int 
	parry,		// stat used as a bonus to active defense test while using this weapon.
	accuracy,	// bonus applied to 
	might;		// amount of damage dealt by a hit with this weapon.
	private CombatAction[] attacks;
	
	public Weapon(int might, int accuracy, int parry, int weaponType) {
		super(getWeaponSize(weaponType), getWeaponWeight(weaponType), getWeaponDurability(weaponType), getWeaponHardness(weaponType), 0);
		this.parry = parry;
		this.accuracy = accuracy;
		this.might = might;
	}
	protected void setActions(CombatAction[] attacks){
		this.attacks = attacks;
	}
	/**
	 * puts all availible combat actions into 
	 * @param combat
	 * @return
	 */
	public ArrayList<CombatAction> getActions(){
		
		ArrayList<CombatAction> actions = new ArrayList<CombatAction>();
		for (int i = 0; i < attacks.length; i++){
			if(attacks[i].canBeUsed()) {
				actions.add(attacks[i]);
			}
		}
		return actions;
	}
	
//	public CombatAction selectAction(){
//		String question = "what action will you take?";
//		String[] answers = new String[attacks.length];
//		for (int i = 0; i < attacks.length; i++){
//			answers[i] = attacks[i].name;
//		}
//		int selection = TextTools.questionAsker(question, answers, TextTools.BACK_DISABLED);
//		CombatAction selectedAction = attacks[selection-1];
//		return selectedAction;
//	}
	
//	public Weapon(int size, int weight, int durability, int hardness, int might, int accuracy, int parry, int weaponType){
//		super(size, weight, durability, hardness, 0);
//		this.parry = parry;
//		this.accuracy = accuracy;
//		this.might = might;
//	}
	@Override
	public String checkDamage(){
		String s = super.checkDamage();
		return s;
	}
	public void setUtility(){
		
	}
	
	public int getUtilityBonus(String task){
		if(utility.containsKey(task)){
			return utility.get(task);
		}
		return -1;
	}
	
	public static int getWeaponWeight(int type){
		if (type == Weapon.LIGHT){
			return 5;
		}
		else if (type == ONE_HANDED){
			return 15;
		}
		else if (type == TWO_HANDED){
			return 25;
		}
		return Integer.MIN_VALUE;
	}
	public static int getWeaponSize(int type){
		if (type == LIGHT){
			return 5;
		}
		else if (type == ONE_HANDED){
			return 15;
		}
		else if (type == TWO_HANDED){
			return 25;
		}
		return Integer.MIN_VALUE;
	}
	public static int getWeaponDurability(int type){
		if (type == LIGHT){
			return 100;
		}
		else if (type == ONE_HANDED){
			return 125;
		}
		else if (type == TWO_HANDED){
			return 150;
		}
		return Integer.MIN_VALUE;
	}
	public static int getWeaponHardness(int type){
		if (type == LIGHT){
			return 5;
		}
		else if (type == ONE_HANDED){
			return 5;
		}
		else if (type == TWO_HANDED){
			return 5;
		}
		return Integer.MIN_VALUE;
	}
	
	public int getMight(){
		return might;
	}
	public int getAccuracy(){
		return accuracy;
	}
	public int getParry(){
		return parry;
	}
}
