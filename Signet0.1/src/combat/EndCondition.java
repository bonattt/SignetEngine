package combat;

public interface EndCondition {
	public boolean isCombatOver(int roundNumber, boolean[] combatState);
}
