package misc;

import java.io.PrintWriter;

import creatures.Creature;

public interface GameEvent {
	public void triggerEvent(Creature player) throws DeathException;
	public String getName();
	public void saveToFile(PrintWriter writer);
}
