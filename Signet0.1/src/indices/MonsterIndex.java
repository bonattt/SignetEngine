package indices;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import testingMothers.TestMonster;
import creatures.Creature;

public class MonsterIndex extends HashMap<String, Constructor<Creature>>{
	
	public MonsterIndex(){
		Constructor con = null;
		try {
			con = TestMonster.class.getConstructor();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.put("test monster", con);
		// this.put(_, _);
	}
	
	public Creature get(String monsterName){
		Constructor<Creature> constructor = super.get(monsterName);
		return null; //constructor.newInstance(arg0);
	}
	
}
