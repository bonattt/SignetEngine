package health;

public abstract class Affliction {
	public abstract int[] passTime(int timePassed, int healingFactor, boolean resting);
	public abstract int getPain();
	public abstract double[] getStatMods();
	public abstract double getCrippling();
}
