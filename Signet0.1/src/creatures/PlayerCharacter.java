package creatures;

import health.Body;
import inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import sampleCombatActions.DefaultDefense;
import sampleCombatActions.SimpleAttack;
import misc.DeathException;
import misc.TextTools;
import combat.AttackAction;
import combat.Combat;
import combat.CombatAction;
import combat.DefenseAction;

import sampleCombatActions.*;

public class PlayerCharacter extends Creature {

	private static PlayerCharacter instance = null; 
	private ArrayList<CombatAction> combatActions;
	
	public PlayerCharacter(String creatureName, HashMap<String, Integer> baseStats, HashMap<String, Integer> damageMultipliers) {
		super(creatureName, baseStats, damageMultipliers);
		combatActions = new ArrayList<CombatAction>();
	}
	private ArrayList<CombatAction> getDefaultCombatActions(){
		ArrayList<CombatAction> actions = new ArrayList<CombatAction>();
		actions.add(new CombatEvasion(this));
		return actions;
	}
	public static PlayerCharacter getInstance(){
		if (instance == null){
			HashMap<String, Integer> baseStats = new HashMap<String, Integer>();
			HashMap<String, Integer> damageMultipliers = new HashMap<String, Integer>();
			instance = new PlayerCharacter("new character", baseStats, damageMultipliers);
		}
		return instance;
	}
	@Override
	public CombatAction selectNormalCombatAction(Scanner inputScanner) {
		//TODO make this work
//		System.out.println("DEBUG it's your turn, but you can't pick an attack action because that feature is not yet availible." +
//				"\npress enter to continue");
//		inputScanner.nextLine();
//		AttackAction atc = new SimpleAttack(this, getInventory().getWeapon(), combat.getOpponent());
//		DefenseAction def = new DefaultDefense(this);
//		return new CombatAction(this, atc, def);

		ArrayList<CombatAction> actionChoices = findAvailibleCombatActions();
		String question = "What will you do?";
		String[] answers = new String[actionChoices.size()];
		for (int i = 0; i < answers.length; i++){
			answers[i] = actionChoices.get(i).name;
		}
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_DISABLED);
		return actionChoices.get(choice - 1);
	}
	private ArrayList<CombatAction> findAvailibleCombatActions(){
		ArrayList<CombatAction> actions = getInventory().getWeapon().getActions();
		for (int i = 0; i < combatActions.size(); i++){
			if(combatActions.get(i).canBeUsed()){
				actions.add(combatActions.get(i));
			}
		}
		return actions;
	}
	
	@Override
	public AttackAction selectExtraAttack(Creature opponent, int advantage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void die() throws DeathException {
		throw new DeathException("you have been killed ", true);
	}
	@Override
	public boolean isPlayer() {
		return true;
	}
	@Override
	public String handleDeath(DeathException e) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String handleKills(DeathException e) {
		// TODO Auto-generated method stub
		return null;
	}
}
