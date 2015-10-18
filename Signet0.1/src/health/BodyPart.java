package health;

import items.Armor;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class BodyPart {
	
	protected ArrayList<Wound> injuries = new ArrayList<Wound>();
	protected double damageMultiplier, painMultiplier, cripplingMultiplier;
	protected double[] statMultipliers;
	public String name;
	private Armor naturalArmor;
	
	public BodyPart(String name, double damage, double pain, double crippling, double[]statMultipliers){
		this(name, damage, pain, crippling, statMultipliers, null);
	}
	public BodyPart(String name, double damage, double pain, double crippling, double[]statMultipliers, Armor naturalArmor){
		this.damageMultiplier = damage;
		this.painMultiplier = pain;
		this.statMultipliers = statMultipliers;
		this.cripplingMultiplier = crippling;
		this.naturalArmor = naturalArmor;
	}
	public boolean hasNaturalArmor(){
		return (naturalArmor != null);
	}
	public Armor getNaturalArmor(){
		return naturalArmor;
	}
	protected void setNaturalArmor(Armor arm){
		naturalArmor = arm;
	}
	protected double getDamageMultiplier(){
		return damageMultiplier;
	}
	protected double getCripplingMultiplier(){
		return cripplingMultiplier;
	}

	public double[] passTime(int timePassed, double healingFactor, boolean resting) {
		double[] healing = {0, 0, 0};
		for (int i = 0; i < injuries.size(); i++){
			double[] temp = injuries.get(i).passTime(timePassed, healingFactor, resting);
			for (int j = 0; j < temp.length; j++){
				healing[i] -= (temp[j] * damageMultiplier);
			}
		}
		return healing;
	}
	public int[] resistDamage(int damage, int damageType){
		int[] naturalArmorReduction = new int[]{damage, damageType};
		int[] wornArmorReduction = new int[]{damage, damageType};
		if (naturalArmor != null){
			naturalArmor.modifyDamage(naturalArmorReduction);
		}
		int[] finalDamage = new int[2];
		finalDamage[0] = 0;
		
		if(wornArmorReduction[0] > naturalArmorReduction[0]){
			finalDamage[0] = naturalArmorReduction[0];
		} else if (wornArmorReduction[0] < naturalArmorReduction[0]){
			finalDamage[0] = wornArmorReduction[0];
		} else {
			finalDamage[0] = wornArmorReduction[0] - 1;
		}
		if (finalDamage[0] < 0){
			finalDamage[0] = 0;
		}
		
		if (wornArmorReduction[1] != damageType){
			finalDamage[1] = wornArmorReduction[1];
		} else if (wornArmorReduction[1] != damageType){
			finalDamage[1] = naturalArmorReduction[1];
		}
		
		return finalDamage;
	}
	protected void addNewWound(Wound w){
		injuries.add(w);
	}

	public int[] getStatMods() {
		int [] statMods = new int[]{0,0,0,0,0,0,0,0,0};
		// TODO implement this.
		return statMods;
	}
	
	public double getPain() {
		double pain = 0;
		for (int i = 0; i < injuries.size(); i++){
			pain += injuries.get(i).getPain();
		}
		return (pain * painMultiplier);
	}
	@Override
	public boolean equals(Object arg){
		return false;
	}
	public boolean equals(BodyPart bp){
		if (bp.name.equals(this.name)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return name.hashCode();
	}
}
