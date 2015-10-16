package sampleItems;

import java.util.HashMap;

import items.Armor;

public class SampleArmorJacket extends Armor {

	private static final int SIZE = 200;
	private static final int WEIGHT = 100;
	private static final int DURABILITY = 100;
	private static final int HARDNESS = 100;
	private static final int DAMAGE = 1;
	
	private static final HashMap<Integer, Integer> RESISTANCE_TABLE = getDamageResistanceTable();
	private static final HashMap<Integer, Integer> TYPES = getTypeConversionTable();
	
	
	public SampleArmorJacket() {
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE);
		initializeArmorStats("armor", RESISTANCE_TABLE, TYPES);
		name = "sample armor jacket";
	}
	
	private static HashMap<Integer, Integer> getDamageResistanceTable(){
		HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
		
		
		return table;
	}
	private static HashMap<Integer, Integer> getTypeConversionTable(){
		return new HashMap<Integer, Integer>();
	}
	@Override
	public void itemBreaks() {
		// TODO Auto-generated method stub
		
	}

}
