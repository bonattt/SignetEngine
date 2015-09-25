package items;

public interface NonUniqueItem {
	
	@Override
	public int hashCode();
	@Override
	public boolean equals(Object obj);
	public boolean equals(Item itm);
	
	public Item getItem();

}
