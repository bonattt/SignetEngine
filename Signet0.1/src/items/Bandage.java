package items;

import java.io.PrintWriter;
import java.util.Scanner;

import misc.TextTools;
import inventory.Inventory;
import creatures.Creature;
import health.Wound;

public class Bandage extends FirstAidItem{
	
	private Wound woundTreated;
	private double healingMultiplier, infectionMultiplier, damageMultiplier;

	private static final int SIZE = 1;
	private static final int WEIGHT = 1;
	private static final int DURABILITY = 5;
	private static final int HARDNESS = 0;
	private static final int DAMAGE = 0;

	private static final double INFECTION_RATE = .5;
	private static final double DAMAGE_MULTIPLIER = .5;
	private static final double HEALING_RATE = 1.2;

	public Bandage() {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, HEALING_RATE, DAMAGE_MULTIPLIER, HEALING_RATE);
		this.name = "Simple Bandage";
	}
	public Bandage(double healingRate, double infectionRate, double damageMultiplier, String name) {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, healingRate, infectionRate, damageMultiplier);
		this.name = name;
	}
	public Bandage(int size, int weight, int durability, int harnesss, int damage,
			double infectionRate, double damageMultiplier, double healingRate) {
		super(size, weight, durability, harnesss, damage, infectionRate, damageMultiplier, healingRate);
	}
	public boolean needsToBeChanged(){
		// TODO implement needs to be changed on Bandage.
		return false;
	}
	@Override
	public void handleUseWhileEquipped(Inventory inv, Creature player, int choice){
		System.out.println("Bandage should not be used while equipped");
		// does nothing
	}
	public boolean passTime(int timePassed, double healingFactor, Wound injury) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean equals(Bandage item) {
		return firstAidEquals(item);
	}
	public boolean equals(Ointment item) {
		return false;
	}
	
	@Override
	public void treatWound(Inventory inv, Wound injury) {
		if (injury.isBandaged()){
			TextTools.display("That wound is already bandaged");
			return;
		}
		inv.discardItem(this);
		injury.setBandage(this);
		TextTools.display("you treated the wound with a " + name);
	}
	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("bandage");
		saveFirstAidStatsToFile(writer);
	}
	public static Bandage loadAlpha0_1(Scanner scanner) {
		String name = scanner.nextLine();
		int size, weight, durability, hardness, damage;
		double infectionRate,damageMultiplier,healingRate;
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		String description = Bandage.loadItemDescriptionAlpha0_1(scanner);
		infectionRate = scanner.nextDouble();
		damageMultiplier = scanner.nextDouble();
		healingRate = scanner.nextDouble();
		Bandage item = new Bandage(size, weight, durability, hardness, damage,
				infectionRate,damageMultiplier,healingRate);
		item.name = name;
		return item;
	}
}
