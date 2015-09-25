package health;

import misc.DamageType;

public class Damage {
	public DamageType type;
	public int ammount;
	
	public Damage(int damage, DamageType type){
		this.ammount = damage;
		this.type = type;
	}
	
}
