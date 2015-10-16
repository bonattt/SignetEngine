package sampleItems;

import items.Armor;

import java.util.HashMap;

public class SampleHelmet extends Armor {
	
	private static final int SIZE = 200;
	private static final int WEIGHT = 100;
	private static final int DURABILITY = 100;
	private static final int HARDNESS = 100;
	private static final int DAMAGE = 0;
	
	private static final HashMap<Integer, Integer> RESISTANCE_TABLE = getDamageResistanceTable();
	private static final HashMap<Integer, Integer> TYPES = getTypeConversionTable();
	
	public SampleHelmet(){
		super(SIZE, WEIGHT, DURABILITY, HARDNESS, DAMAGE);
		initializeArmorStats("helmet", RESISTANCE_TABLE, TYPES);
		name = "sample helmet";
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
