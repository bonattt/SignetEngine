package inventory;

import java.util.ArrayList;

import creatures.Creature;
import misc.DiceRoller;
import misc.TextTools;
import items.Item;

public abstract class ItemContainer {
	
	public String name;
	
	private ArrayList<Item> items;
	
	private int
	concealmentHits,	// this is a rating of how well the container was concealed by whoever hit it. (number of hits)
	concealability,		// how easily is this container concealed.
	spaceUsed,			// space taken up by items inside
	weightContained,	// weight of items inside
	baseSpace,			// space in the container
	baseWeight;			// weight of the container
	
	public ItemContainer(int weight, int size, String name){
		items = new ArrayList<Item>();
		baseSpace = size;
		baseWeight = weight;
		this.name = name;
	}
	/**
	 * this method retuns a boolean for whether the concealed container 
	 * @return
	 */
	public boolean avoidDetection(Creature searcher){		
		if (items.size() == 0){
			// if there's nothing to find, you can't find it.
			return false;
		}
		int hits = searcher.getSkills().get("perception").makeSkillTest(searcher, concealmentHits, -concealability)[0];
		return (hits >= 0);
	}
	
	/**
	 * searching a container requires time based on how much stuff is in there, and how big the container is.
	 * (usually its easier to sort through lots of stuff if it's not all jammed in the same tiny bag)
	 * @return
	 */
	public int timeToSearch(){
		int time = (items.size()*3) - baseSpace;
		if (time < 10){
			time = 10;
		}
		return time;
	}
	public ItemContainer(int weight, int size, String name, ArrayList<Item> itms){
		items = itms;
		baseSpace = size;
		baseWeight = weight;
		this.name = name;
	}
	/**
	 * adds an item to the item container, if it will fit, then updates space used and weight fields.
	 * returns true if the add was successful
	 * @param itm
	 * @return
	 */
	public boolean addItem(Item itm){
		int newSpaceUsed = spaceUsed + itm.getSize();
		if (newSpaceUsed > baseSpace){
			TextTools.display(printNoRoomInContainer(itm));
			return false;
		}
		items.add(itm);
		spaceUsed = newSpaceUsed;
		weightContained += itm.getWeight();
		return true;
	}
	
	public boolean removeItem(Item itm){
		for(int i = 0; i < items.size(); i++){
			if (items.get(i) == itm){
				items.remove(i);
				weightContained -= itm.getWeight();
				spaceUsed -= itm.getSize();
				return true;
			}
		}
		TextTools.display("Item could not be found to remove.");
		return false;
	}
	
	public int getWeight(){
		return baseWeight + weightContained;
	}
	public int getSpaceLeft(){
		return baseSpace - spaceUsed;
	}
	public ArrayList<Item> listITems(){
		return items;
	}
	
	private String printNoRoomInContainer(Item itm){
		StringBuilder str = new StringBuilder();
		str.append("There is no room for that ");
		str.append(itm.name);
		str.append(" in your ");
		str.append(name);
		str.append(".");
		return str.toString();
	}
}
