package health;

import items.Armor;

import java.util.ArrayList;
import java.util.HashMap;

import misc.DamageType;


public abstract class BodyPart {
	
	ArrayList<Wound> injuries = new ArrayList<Wound>();
	double damageMultiplier, painMultiplier, cripplingMultiplier;
	double[] statMultipliers;
	public String name;
	private Armor wornArmor;
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
		wornArmor = null;
	}
	public boolean hasWornArmor(){
		return (wornArmor != null);
	}
	public boolean hasNaturalArmor(){
		return (naturalArmor != null);
	}
	public Armor getArmor(){
		return wornArmor;
	}
	public void setArmor(Armor arm){
		wornArmor = arm;
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
	public Damage resistDamage(int damage, DamageType dt){
		Damage naturalArmorReduction = new Damage(damage, dt);
		Damage wornArmorReduction = new Damage(damage, dt);
		if (naturalArmor != null){
			naturalArmor.modifyDamage(naturalArmorReduction);
		}
		if (wornArmor != null){
			wornArmor.modifyDamage(wornArmorReduction);
		}
		Damage finalDamage = new Damage(0, null);
		
		if(wornArmorReduction.ammount > (int) naturalArmorReduction.ammount){
			finalDamage.ammount = naturalArmorReduction.ammount;
		} else if (wornArmorReduction.ammount < naturalArmorReduction.ammount){
			finalDamage.ammount = wornArmorReduction.ammount;
		} else {
			finalDamage.ammount = wornArmorReduction.ammount - 1;
		}
		if (finalDamage.ammount < 0){
			finalDamage.ammount = 0;
		}
		
		if ((DamageType) wornArmorReduction.type != dt){
			finalDamage.type = wornArmorReduction.type;
		} else if ((DamageType) wornArmorReduction.type != dt){
			finalDamage.type = naturalArmorReduction.type;
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
