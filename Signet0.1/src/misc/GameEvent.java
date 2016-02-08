package misc;

import java.io.PrintWriter;

import creatures.Creature;

public interface GameEvent {
	public Object triggerEvent(Creature player) throws DeathException;
	public String getName();
	public void saveToFile(PrintWriter writer);
}
