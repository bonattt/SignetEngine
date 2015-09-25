package npc;

import java.util.Scanner;

import misc.*;
import creatures.*;

public class SkillTestNode extends DialogueNode {
	
	Skill skill;
	int threshold;
	DialogueNode failure, success, failFumble, successFumble;

	public SkillTestNode(Scanner input, String text, Skill skill, int threshold, DialogueNode[] nodes) {
		super(input, text);
		this.skill = skill ;
		this.threshold = threshold;
		failure = nodes[0];
		success = nodes[1];
		failFumble = nodes[2];
		successFumble = nodes[3];
	}

	@Override
	public DialogueNode openNode(PlayerCharacter player, NPC npc) {
		int[] result = skill.makeSkillTest(player, threshold);
		boolean fumble = DiceRoller.detectFumble(result[0], result[1]); //TODO fix this to use actual dice-pool
		if (fumble) {
			if (result[0] >= 0){
				return successFumble;
			}
			return failFumble;
		} else {
			if (result[0] >= 0){
				return success;
			}
			return failure;
		}
	}
	
	
}
