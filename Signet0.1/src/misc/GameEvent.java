package misc;

import creatures.Creature;

public interface GameEvent {
	public void triggerEvent(Creature player) throws DeathException;
	public String getName();
}
