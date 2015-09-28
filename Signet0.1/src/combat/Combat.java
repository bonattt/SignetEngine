package combat;

import java.util.Scanner;

import misc.DeathException;
import misc.GameEvent;
import misc.NonExistantMonsterException;
import misc.TextTools;
import mothers.TestMonster;
import creatures.*;



public class Combat implements GameEvent {

	public static final int ROUND_DURATION = 5;
	private static Combat instance = null;
	
	private Creature player;
	private Creature opponent;
	private CombatAction playerAction, opponentAction;
	private int advantage;
	private int advantageGained;
	private EndCondition endcondition;
	private int roundNumber;
	// advantage tracks which combatant has the upper hand in the current fight, at the very moment.
	// the tides of a battle can change very suddenly, so adjustments to advantage only lasts 1 round.
	// positive numbers favor the player, negative numbers favor the monster.
	
	public static Combat getInstance(){
		return instance;
	}
	public static Combat newCombat(String[] args) throws NonExistantMonsterException{
		instance = new Combat(args);
		return instance;
	}
	public static Combat newCombat(Creature opponent){
		instance = new Combat(opponent);
		return instance;
	}
	
	private Combat(String[] args) throws NonExistantMonsterException{
		this(getMonster(args[1]));
	}
	private Combat(Creature opponent){
		this.player = null;
		this.opponent = opponent;
		advantage = 0;
		roundNumber = 1;
		endcondition = new DefaultEndCondition();
	}
	
	public static Creature getMonster(String monsterName) throws NonExistantMonsterException{
		// TODO get monsters.
		if (monsterName.equals("test monster")){
			return new TestMonster();
		}
		return null;
	}
	public void triggerEvent(Creature player) throws DeathException{
		this.player = player;
		combatRunner();
	}
	
	/**
	 * the player and monster roll initiative and select actions.
	 * @return
	 */
	private int setUpRound(Scanner inputScanner){
		TextTools.display("it is round " + roundNumber + ". \nPress enter to continue");
		inputScanner.nextLine();
		
		advantage = advantageGained;
		advantageGained = 0;
		int init = player.rollInititative() - opponent.rollInititative();
		opponentAction = opponent.selectNormalCombatAction(inputScanner);
		playerAction = player.selectNormalCombatAction(inputScanner);
		return init;
	}
	private int fightRound(CombatAction firstAttacker, CombatAction secondAttacker, int initiative, int advantage) throws DeathException {
		try {
			firstAttacker.attack.takeAction();
			secondAttacker.attack.takeAction();
		} catch (DeathException e){
			throw e;
		}
		return 8;
	}
	/**
	 * this method checks the end conditions of the 
	 * @return
	 */
	private boolean endRound(int initiative) throws DeathException{
		try {
			player.passTime(ROUND_DURATION, 0, false);
		} catch (DeathException e1) {
			try {
				opponent.passTime(ROUND_DURATION, 0, false);
				TextTools.display("The player was killed by the monster");
				throw e1;
			} catch (DeathException e2) {
				TextTools.display("player and monster both succumbed to their wounds");
				throw e1;
			}
		}
		try {
			opponent.passTime(ROUND_DURATION, 0, false);
		} catch (DeathException e2) {
			TextTools.display("player killed the monster!! You won the fight!");
			throw e2;
		}
		
		boolean playerHasUpperHand = (advantageGained-1 >0);
		boolean monsterHasUpperHand = (advantageGained-1 <0);
		boolean playerAttackedTwice = (initiative >= 10);
		boolean monsterAttackedTwice = (initiative <= -10);
		
		//TODO implement actual flags.
		boolean[] endFlags = new boolean[]{playerHasUpperHand,monsterHasUpperHand,playerAttackedTwice,monsterAttackedTwice};
		// TODO player.exhaustion
		// TODO monster.exhaustion
		return endcondition.isCombatOver(roundNumber, endFlags);
	}
	
	private void combatRunner() throws DeathException{
		boolean stillFighting = true;
		Scanner inputScanner = new Scanner(System.in);
		while(stillFighting){
			try{
				int init = setUpRound(inputScanner);
				if (init >= 0){
					advantageGained += fightRound(playerAction, opponentAction, advantage, init);
				} else {
					advantageGained -= fightRound(opponentAction, playerAction, -advantage, -init);
				}
				stillFighting = endRound(init);
			} catch (DeathException e){
				if(e.playerDeath){
					throw e;
				}
				return;
			}
		}
	}
	public Creature getPlayer(){
		return player;
	}
	
	public Creature getOpponent(){
		return opponent;
	}
	@Override
	public String getName() {
		// TODO add an exception
		TextTools.display("combat objects have no name.");
		return null;
	}
}
