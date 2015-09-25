package combat;


public abstract class CombatEffect {
	
	int init, atc, def;
	
	public CombatEffect(int initiativeModifier, int attackDiceModifier, int defenseDiceModifier){
		init = initiativeModifier;
		atc = attackDiceModifier;
		def = defenseDiceModifier;
	}
	public int attackMod(){
		return atc;
	}
	public int defenseMod(){
		return def;
	}
	public int initiativeMod(){
		return init;
	}
	/**
	 * this combat effect may grant the player temporary special attacks, which are returned in an array.
	 * By default, returns an empty array.
	 * @return
	 */
	public CombatAction[] getSpecialAttacks(){
		return new CombatAction[]{};
	}
}
