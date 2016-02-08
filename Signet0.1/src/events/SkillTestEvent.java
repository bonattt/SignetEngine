package events;

import java.io.PrintWriter;

import com.sun.webkit.ThemeClient;

import creatures.Creature;
import creatures.Skill;
import misc.DeathException;
import misc.GameEvent;

public class SkillTestEvent implements GameEvent {
	
	private String skill;
	private int threshold, adjustment, limit;
	
	public SkillTestEvent(String skillName, int threshold, int adjustment, int limit) {
		skill = skillName;
		this.threshold = threshold;
		this.adjustment = adjustment;
		this.limit = limit;
	}

	public Object triggerEvent(Creature player) throws DeathException {
		int[] result =  player.makeTest(skill, threshold, limit, adjustment);
		if (result[0] >= 0) {
			return new Boolean(true);
		} else {
			return new Boolean(false);
		}
	}

	public String getName() {
		return String.format("%s test event (%d, %d, %d)", skill, threshold, adjustment, limit);
	}

	public void saveToFile(PrintWriter writer) {
		// TODO Auto-generated method stub
		
	}
	
}
