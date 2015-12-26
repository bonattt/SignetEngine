package items;


import java.io.PrintWriter;
import java.util.Scanner;

public class MeleeWeapon extends Weapon {

	public static final int LIGHT = 0;
	public static final int ONE_HANDED = 1;
	public static final int TWO_HANDED = 2;
	
	private int parry;		// stat used as a bonus to active defense test while using this weapon.
	
	public MeleeWeapon(int might, int accuracy, int parry, int weaponType, String name, String descrip) {
		super(getWeaponSize(weaponType), getWeaponWeight(weaponType), getWeaponDurability(weaponType),
				getWeaponHardness(weaponType), 0, name, descrip, 
				might, accuracy, weaponType);
		this.parry = parry;
	}
	public MeleeWeapon(int size, int weight, int durability, int hardness, int damage,
			String name, String descrip, 
			int weaponType, int parry, int accuracy, int might) {
		super(size, weight, durability, hardness, damage, name, descrip,
				might, accuracy, weaponType);
		this.parry = parry;
	}
	
	@Override
	public void saveToFile(PrintWriter writer) {
		writer.println("weapon");
		saveBaseStats(writer);
		saveBaseWeaponStats(writer);
		writer.println(parry);
	}
	public static MeleeWeapon loadMeleeWeaponAlpha0_1(Scanner scanner) {
		int size, weight, durability, hardness, damage, weaponType, parry, accuracy, might;
		String name = scanner.nextLine();
		String description = Item.loadItemDescriptionAlpha0_1(scanner);
		size = scanner.nextInt();
		weight = scanner.nextInt();
		durability = scanner.nextInt();
		hardness = scanner.nextInt();
		damage = scanner.nextInt();
		might = scanner.nextInt();
		accuracy = scanner.nextInt();
		weaponType = scanner.nextInt();
		parry = scanner.nextInt();
		scanner.nextLine(); // realign the scanner to read lines after performing nextInt()
		MeleeWeapon weapon =  new MeleeWeapon(size, weight, durability, hardness, damage, name, description,
				weaponType, parry, accuracy, might);
		return weapon;
	}

	@Override
	public boolean isMeleeWeapon(){
		return true;
	}
	@Override
	public boolean isRangedWeapon() {
		return false;
	}
	
	@Override
	public String checkDamage() {
		String s = super.checkDamage();
		return s;
	}
	
	private static int getWeaponWeight(int type) {
		if (type == MeleeWeapon.LIGHT){
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
	
	private static int getWeaponSize(int type){
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
	
	private static int getWeaponDurability(int type){
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
	
	private static int getWeaponHardness(int type){
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
	
	public int getParry(){
		return parry;
	}
	
	@Override
	public boolean equals(Item item){
		MeleeWeapon weapon;
		try {
			weapon = (MeleeWeapon) item;
		} catch (ClassCastException e) {
			return false;
		}
		return (super.equals(weapon)) &&
				(weapon.name().equals(this.name())) &&
				((weapon.getAccuracy() == getAccuracy())) &&
				((weapon.getMight() == getMight())) &&
				((weapon.getParry() == weapon.getParry())) &&
				((weapon.getSize() == weapon.getSize())) && 
				((weapon.getWeaponType() == getWeaponType())) &&
				((weapon.getWeight() == getWeight())) &&
				(weapon.description().equals(this.description()));
	}
}
