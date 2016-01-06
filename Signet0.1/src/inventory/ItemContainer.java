package inventory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import creatures.Creature;
import misc.TextTools;
import items.*;

public class ItemContainer {
	
	private String name;
	
	public static boolean DEBUG_VALUE_TEMP = false;
	
	private ArrayList<Item> items;
	
	private int
	concealmentHits,	// this is a rating of how well the container was concealed by whoever hit it. (number of hits)
	concealability,		// how easily is this container concealed.
	spaceUsed,			// space taken up by items inside
	weightContained,	// weight of items inside
	baseSpace,			// space in the container
	baseWeight;			// weight of the container
	
	public ItemContainer(int weight, int size, String name){
		this(weight, size, name, new ArrayList<Item>());
	}
	public ItemContainer(int weight, int size, String name, ArrayList<Item> itms){
		items = itms;
		baseSpace = size;
		baseWeight = weight;
		this.name = name;
		updateWeight();
	}
	public String name() {
		return name;
	}
	
	public void updateWeight() {
		weightContained = 0;
		int length = items.size();
		for (int i = 0; i < length; i++) {
			weightContained += items.get(i).getWeight();
		}
	}
	
	public void saveToFile(PrintWriter writer){
		writer.println(name);
		writer.println(concealmentHits);
		writer.println(concealability);
		writer.println(spaceUsed);
		writer.println(weightContained);
		writer.println(baseSpace);
		writer.println(baseWeight);
		for (int i = 0; i < items.size(); i++){
			items.get(i).saveToFile(writer);
		}
		writer.println("end item container");
	}
	public static ItemContainer loadAlpha0_1(Scanner scanner){
		int concealmentHits, concealability, spaceUsed, weightContained, baseSpace, baseWeight;
		String name = scanner.nextLine();
		concealmentHits = scanner.nextInt();
		concealability = scanner.nextInt();
		spaceUsed = scanner.nextInt();
		weightContained = scanner.nextInt();
		baseSpace = scanner.nextInt();
		baseWeight = scanner.nextInt();
		ArrayList<Item> items = loadItemsAlpha0_1(scanner);
		return new ItemContainer(baseWeight, baseSpace, name, items);
	}
	private static ArrayList<Item> loadItemsAlpha0_1(Scanner scanner) {
		ArrayList<Item> items = new ArrayList<Item>();
		System.out.println("DEBUG: '" + scanner.nextLine() + "'");
		String itemType = scanner.nextLine();
		while(!itemType.equals("end item container")) {
			Item item = Item.loadAlpha0_1(scanner, itemType);
			items.add(item);
			itemType = scanner.nextLine();
		}
		return items;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof ItemContainer)) {
			return false;
		}
		ItemContainer obj = (ItemContainer) o;
		return (obj.concealability == this.concealability)
				&& (obj.name.equals(this.name))
				&& (obj.baseWeight == this.baseWeight)
				&& (obj.baseSpace == this.baseSpace);
		
	}

	public boolean contentsEqual(ItemContainer obj) {
		if(this.items.size() != obj.items.size()) {
			return false;
		}
		for (Item item : this.items) {
			if (!obj.items.contains(item)) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * this method retuns a boolean for whether the concealed container was found by a searcher.
	 * @return
	 */
	public boolean contains(Item item){
		return items.contains(item);
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
	public void lootDuringExplore(Creature player){
		TextTools.display("you open the " + name);
		while (true){
			String question = "What will you do?";
			String[] answers = new String[]{"take items", "store items", "exit"};
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 1){
				takeItemsDuringExplore(player.getInventory());
			} else if (choice == 2){
				storeItemsDuringExplore(player.getInventory());
			} else if (choice == 0){
				break;
			}
		}
	}
	public void takeItemsDuringExplore(Inventory inv){
		while(true){
			String question = "what will you take?";
			String[] answers = new String[items.size() + 1];
			for (int i = 0; i < items.size(); i++){
				answers[i] = items.get(i).name();
			}
			answers[answers.length - 1] = "cancel";
			int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
			if (choice == 0){
				break;
			}
			Item selectedItem = items.get(choice - 1);
			if(inv.store(selectedItem)){
				items.remove(selectedItem);
			} else {
				TextTools.display("there is no room in your bag for the " + selectedItem.name());
			}
		}
	}
	public void storeItemsDuringExplore(Inventory inv){
		while(true){
			String question = "what will you take?";
			Item selectedItem = inv.selectItemFromBackpack(question);
			if(selectedItem == null){
				break;
			}
			if(this.addItem(selectedItem)){
				inv.discardItem(selectedItem);
			} else {
				TextTools.display("There is no room in the " + name + " for your " + selectedItem.name());
			}
		}
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
	protected ArrayList<Item> getAllItems(){
		return items;
	}
	protected ArrayList<Item> getMedicine(){
		ArrayList<Item> medicine = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++){
			if(items.get(i).isFirstAid()){
				medicine.add(items.get(i));
			}
		}
		return medicine;
	}
	protected ArrayList<Item> getLightSources(){
		ArrayList<Item> lights = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++){
			if(items.get(i).isLightSource()){
				lights.add(items.get(i)); //TODO make this better.
			}
		}
		return lights;
	}
	protected ArrayList<Item> getWeapons(){
		ArrayList<Item> weapons = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++){
			if(items.get(i).isWeapon()){
				weapons.add(items.get(i)); //TODO make this better.
			}
		}
		return weapons;
	}
	protected ArrayList<Item> getClothing(){
		ArrayList<Item> clothes = new ArrayList<Item>();for (int i = 0; i < items.size(); i++){
			if(items.get(i).isClothing()){
				clothes.add(items.get(i)); //TODO make this better.
			}
		}
		return clothes;
	}
	protected ArrayList<Item> getArmor(){
		ArrayList<Item> armor = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++){
			if(items.get(i).isArmor()){
				armor.add(items.get(i)); //TODO make this better.
			}
		}
		return armor;
	}
	public void accessSelectedItems(ArrayList<Item> itemsSelected, Inventory inv, Creature player){
		String question = "which item would you like to use?";
		String[] answers = new String[itemsSelected.size() + 1];
		for (int i = 0; i < answers.length - 1; i++){
			answers[i] = itemsSelected.get(i).name();
		}
		answers[answers.length - 1] = "cancel";
		int choice = TextTools.questionAsker(question, answers, TextTools.BACK_ENABLED);
		if (choice == 0){
			return;
		}
		try {
			itemsSelected.get(choice - 1).useFromInventory(inv, player);
		} catch (Exception e) {
			// do nothing -- this is temporary
		}
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
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("ItemContainer: ");
		str.append(name);
		str.append("\n {");
		str.append("\n    ");
		str.append(items.toString());
		str.append("\n    ");
		str.append("weight: ");
		str.append(getWeight());
		str.append("\n    ");
		str.append("space used:  ");
		str.append(spaceUsed);
		str.append(" / ");
		str.append(baseSpace);
		str.append("\n    ");
		str.append("concealment hits: ");
		str.append(concealmentHits);
		return str.toString();
	}
	
	private String printNoRoomInContainer(Item itm){
		StringBuilder str = new StringBuilder();
		str.append("There is no room for that ");
		str.append(itm.name());
		str.append(" in your ");
		str.append(name);
		str.append(".");
		return str.toString();
	}
}
