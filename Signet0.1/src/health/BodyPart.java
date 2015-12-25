package health;

import items.Armor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class BodyPart {
	
	protected ArrayList<Wound> injuries = new ArrayList<Wound>();
	protected double damageMultiplier, painMultiplier, cripplingMultiplier;
	protected double[] statMultipliers;
	public String name;
	private Armor naturalArmor;
	
	public BodyPart(String name, double damageRate, double painRate, double cripplingRate, double[]statMultipliers){
		this(name, damageRate, painRate, cripplingRate, statMultipliers, null);
	}
	public BodyPart(String name, double damage, double pain, double crippling, double[]statMultipliers, Armor naturalArmor){
		this.name = name;
		this.damageMultiplier = damage;
		this.painMultiplier = pain;
		this.statMultipliers = statMultipliers;
		this.cripplingMultiplier = crippling;
		this.naturalArmor = naturalArmor;
	}
	public int getWoundCount(){
		return injuries.size();
	}
	public ArrayList<Wound> getInjuries(){
		return injuries;
	}
	public static BodyPart loadAlpha1_0fromFile(Scanner scanner){
		return null;
	}
	public void saveToFile(PrintWriter writer){
		writer.println(name);
		writer.println(damageMultiplier);
		writer.println(painMultiplier);
		writer.println(cripplingMultiplier);
		for (int i = 0; i < statMultipliers.length; i++){
			writer.println(statMultipliers[i]);
		}
		if (naturalArmor == null){
			writer.println("no natural armor");
		} else {
			writer.println("natural armor");
			naturalArmor.saveToFile(writer);
		}
		saveInjuriesToFile(writer);
	}
	private void saveInjuriesToFile(PrintWriter writer){
		writer.println("injuries");
		for(int i = 0; i < injuries.size(); i++){
			injuries.get(i).saveToFile(writer);
		}
		writer.println("end injuries");
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
		removeHealedWounds();
		// {physical damage healed/taken, stun damage healed/taken, fatigue damage healed/taken}
		return healing;
	}
	public void removeHealedWounds(){
		for (int i = 0; i < injuries.size(); i++){
			if(injuries.get(i).getSeverity() <= 0){
				injuries.remove(i);
				i -= 1;
			}
		}
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
	public void addNewWound(Wound w){
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
