package testMocks;

import items.Item;

public class ItemMock extends Item {

	public ItemMock(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);

	}
	public ItemMock(){
		super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
	}

	@Override
	public void itemBreaks() {

		
	}

}
