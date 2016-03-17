package testingMothers;

import items.StoryItem;

public class SampleItems {

	private static final int THINGY_SIZE = 20;
	private static final int THINGY_WEIGHT = 10;
	private static final int THINGY_DURABILITY = 25;
	private static final int THINGY_HARDNESS = 3;
	private static final int THINGY_DAMAGE = 0;
	private static final String THINGY_NAME = "mystic thingy";
	private static final String THINGY_DESC = "this thingy is very mysterous,\nI wonder what it does.";
	private static final String THINGY_USESTR = "this thingy is very mysterous,\nI wonder what it does.";
	
	public static StoryItem getMysticThingy() {
		return new StoryItem(THINGY_SIZE, THINGY_WEIGHT, THINGY_DURABILITY, THINGY_HARDNESS, THINGY_DAMAGE,
				THINGY_NAME, THINGY_DESC, THINGY_USESTR);
	}
}
