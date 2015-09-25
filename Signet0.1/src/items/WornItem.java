package items;

public abstract class WornItem extends Item {

	public WornItem(int size, int wt, int dur, int hard, int dam) {
		super(size, wt, dur, hard, dam);
	}

	public void modifyStats(int[] stats){
		// by default, does nothing.
	}
}
