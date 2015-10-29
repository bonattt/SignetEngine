package items;

import java.util.ArrayList;

import misc.TextTools;
import inventory.Inventory;
import creatures.Creature;
import health.Wound;

public abstract class FirstAidItem extends Item {
	
	protected double infectionMultiplier, healingMultiplier, damageMultiplier;
	protected Wound woundTreated;

	public FirstAidItem(int size, int wt, int dur, int hard, int dam, double infection, double damage, double healing) {
		super(size, wt, dur, hard, dam);
		infectionMultiplier = infection;
		healingMultiplier = healing;
		damageMultiplier = damage;
	}
	@Override
	public void useFromInventory(Inventory inv, Creature character){
		ArrayList<Wound> wounds = character.listAllWounds();
		String question = "which wound would you like to treat?";
		String[] answers = new String[wounds.size() + 1];
		for (int i = 0; i < wounds.size(); i++){
			answers[i] = wounds.get(i).name;
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if(choice == 0){
			return;
		}
		treatWound(inv, wounds.get(choice - 1));
		
	}
	public abstract void treatWound(Inventory inv, Wound injury);
	public abstract boolean passTime(int timePassed, double healingFactor, Wound injury);
	
	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}
	public boolean isFirstAid(){
		return true;
	}
	public void setWound(Wound injury){
		woundTreated = injury;
	}
	public double getDamageMultiplier(){
		return damageMultiplier;
	}
	public double getHealingRateAdjustment(){
		return healingMultiplier;
	}
	public double getInfectionMultiplier(){
		return infectionMultiplier;
	}
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		System.out.println("Ammo should not be used while equipped");
		// does nothing
	}
}
