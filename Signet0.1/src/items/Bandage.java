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
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, "Simple Bandage", "this is just a simple bandage",
				HEALING_RATE, DAMAGE_MULTIPLIER, HEALING_RATE);
	}
	public Bandage(String name, String description,
			double healingRate, double infectionRate, double damageMultiplier) {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE, name, description,
				healingRate, infectionRate, damageMultiplier);
	}
	public Bandage(int size, int weight, int durability, int harnesss, int damage, String name,
			String description, double infectionRate, double damageMultiplier, double healingRate) {
		super(size, weight, durability, harnesss, damage, name, description,
				infectionRate, damageMultiplier, healingRate);
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
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Bandage)) {
			return false;
		}
		Bandage item = (Bandage) o;
		return firstAidEquals(item);
	}
	
	@Override
	public void treatWound(Inventory inv, Wound injury) {
		if (injury.isBandaged()){
			TextTools.display("That wound is already bandaged");
			return;
		}
		inv.discardItem(this);
		injury.setBandage(this);
		TextTools.display("you treated the wound with a " + this.name());
	}
	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("bandage");
		saveFirstAidStatsToFile(writer);
	}
	public static Bandage loadBandageAlpha0_1(Scanner scanner) {
		String name = scanner.nextLine();
		String description = Bandage.loadItemDescriptionAlpha0_1(scanner);
		int size, weight, durability, hardness, damage;
		double infectionRate,damageMultiplier,healingRate;
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		infectionRate = scanner.nextDouble();
		damageMultiplier = scanner.nextDouble();
		healingRate = scanner.nextDouble();
		scanner.nextLine(); // realign the scanner to read strings after .nextDouble()
		Bandage item = new Bandage(size, weight, durability, hardness, damage, name, description,
				infectionRate,damageMultiplier,healingRate);
		return item;
	}
}
