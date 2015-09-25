package items;

public class AmmoClip<T extends Ammo> extends Item{

	private static final int DEFAULT_DURABILITY = 50;
	private static final int DEFAULT_HARDNESS = 5;
	
	private int ammoCapacity, ammoRemaining;
	
	public AmmoClip(int size, int ammoCapacity, int startingAmmo, int dam) {
		super(size, size/2, DEFAULT_DURABILITY, DEFAULT_HARDNESS, dam);
		this.ammoCapacity = ammoCapacity;
		ammoRemaining = startingAmmo;
	}
	public AmmoClip(int size, int ammoCapacity){
		super(size, size/2, DEFAULT_DURABILITY, DEFAULT_HARDNESS, 0);
		this.ammoCapacity = ammoCapacity;
		ammoRemaining = 0;
	}

	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
	}

}
