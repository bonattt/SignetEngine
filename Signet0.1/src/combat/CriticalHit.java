package combat;

import creatures.Creature;
import health.Wound;

public abstract class CriticalHit {
	
	public CriticalHit(){
		
	}
	
	public abstract Wound modifyWound(Wound w, Creature c);
	
}
