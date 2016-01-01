package health;

import items.Armor;
import items.Item;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class BodyPart {
	
	protected ArrayList<Wound> injuries = new ArrayList<Wound>();
	protected double damageMultiplier, painMultiplier;
	public String name;
	private Armor naturalArmor;
	
	public BodyPart(String name, double damageRate, double painRate){
		this(name, damageRate, painRate, null);
	}
	public BodyPart(String name, double damage, double pain, Armor naturalArmor){
		this.name = name;
		this.damageMultiplier = damage;
		this.painMultiplier = pain;
		this.naturalArmor = naturalArmor;
	}
	public int getWoundCount(){
		return injuries.size();
	}
	public ArrayList<Wound> getInjuries(){
		return injuries;
	}
	public static BodyPart loadAlpha0_1(Scanner scanner){
		double damageMultiplier, painMultiplier;
		String name = scanner.nextLine();
		damageMultiplier = scanner.nextDouble();
		painMultiplier = scanner.nextDouble();
		scanner.nextLine();
		Armor naturalArmor = loadNaturalArmorAlpha0_1(scanner);
		
		BodyPart bodypart = new BodyPart(name, damageMultiplier, painMultiplier, naturalArmor);
		bodypart.loadInjuriesAlpha0_1(scanner);
		return bodypart;
	}
	
	private static Armor loadNaturalArmorAlpha0_1(Scanner scanner) {
		String line = scanner.nextLine();
		if(line.equals("no natural armor")) {
			return null;
		} else {
			return (Armor) Item.loadAlpha0_1(scanner, line);
		}
	}
	
	private void loadInjuriesAlpha0_1(Scanner scanner) {
		String line = scanner.nextLine();
		while(!line.equals("end injuries")) {
			Wound injury = Wound.loadAlpha1_0fromFile(scanner, this);
			this.injuries.add(injury);
			line = scanner.nextLine();
		}
	}
	
	private static double[] loadStatMultipliersAlpha0_1(Scanner scanner) {
		double[] statMult = new double[scanner.nextInt()];
		for (int i = 0; i < statMult.length; i++) {
			statMult[i] = scanner.nextDouble();
		}
		scanner.nextLine();
		return statMult;
	}
	public void saveToFile(PrintWriter writer){
		writer.println(name);
		writer.println(damageMultiplier);
		writer.println(painMultiplier);
		if (naturalArmor == null){
			writer.println("no natural armor");
		} else {
			writer.println("natural armor");
			naturalArmor.saveToFile(writer);
		}
		saveInjuriesAlpha0_1(writer);
	}
	
	private void saveInjuriesAlpha0_1(PrintWriter writer){
		for(int i = 0; i < injuries.size(); i++){
			writer.println("injury");
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
		if (! (arg instanceof BodyPart)) {
			return false;
		}
		BodyPart bodypart = (BodyPart) arg;
		if (bodypart.naturalArmor == null) {
			if (this.naturalArmor != null) {
				return false;
			}
		} else {
			if (! bodypart.naturalArmor.equals(this.naturalArmor)) {
				return false;
			}
		}
		
		return (bodypart.name.equals(this.name)) &&
				(bodypart.damageMultiplier == this.damageMultiplier) &&
				(bodypart.painMultiplier == this.painMultiplier) &&
				(this.equalWounds(bodypart));
	}
	
	private boolean equalWounds(BodyPart bodypart) {
		int size = this.injuries.size();
		if (size != bodypart.injuries.size()) {
			return false;
		}
		for(int i = 0; i < this.injuries.size(); i++) {
			Wound current = this.injuries.get(i);
			Wound other = bodypart.injuries.get(i);
			if (! current.equals(other)) {
				return false;
			}
		}
		return true;
	}
}
