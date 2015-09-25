package combat;

public class DefaultEndCondition implements EndCondition{
	
	public DefaultEndCondition(){
		//do nothing
	}
	public boolean isCombatOver(int roundNumber, boolean[] combatState){
		return false;
	}
	
}
