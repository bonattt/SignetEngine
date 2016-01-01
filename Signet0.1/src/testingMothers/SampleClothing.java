package testingMothers;

import items.WornItem;

public class SampleClothing {

	public static WornItem getSamplePants() {
		WornItem clothing = new WornItem(100, 5, 10, 0, 0, "pants", "sample pants", "these are some normal pants");
		return clothing;
	}
	
	public static WornItem getSampleShirt() {
		WornItem clothing = new WornItem(100, 5, 10, 0, 0, "shirt", "sample shirt", "this is a normal shirt");
		return clothing;
	}
	
}
