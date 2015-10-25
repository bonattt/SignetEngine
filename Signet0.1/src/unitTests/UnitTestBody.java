package unitTests;

import injuries.SlashingWound;
import health.Body;
import health.BodyPart;
import health.DamageType;
import health.Wound;
import misc.DeathException;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import testingMothers.CharacterMother;


public class UnitTestBody {

	private Body body;
	
	@Before
	public void setup(){
		body = new Body(CharacterMother.getDickDefenderOfLife());
	}
	
	@Test
	public void testSmallFatigueIsAbsorbed(){
		double fagigueInitial = body.getFatigue();
		body.takeSteminaDamage(.1);
		assertEquals(fagigueInitial, body.getFatigue(), 0);
	}
	@Test
	public void testLargeFatiguePartiallyAbsorbed(){
		double fagigueInitial = body.getFatigue();
		body.takeSteminaDamage(1000);
		assertNotEquals(fagigueInitial, body.getFatigue());
	}
	@Test
	public void testLargeFatigueNotFullyAbsorbed(){
		double fagigueInitial = body.getFatigue();
		double fatigueTaken = 10;
		body.takeSteminaDamage(10);
		assertTrue(fagigueInitial+fatigueTaken > body.getFatigue());
	}
	@Test
	public void testGettingWoundedAddsOneWound(){
		int initialWoundCount = body.countWounds();
		try {
			body.recieveWound(3, DamageType.BLUNT, body.getBodyParts().get("chest"));
		} catch (DeathException e) {
			// do nothing 
		}
		assertEquals(initialWoundCount+1, body.countWounds());
	}
	@Test
	public void testGettingWoundedIncreasesPain(){
		try {
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
		} catch (DeathException e) {
			fail("Player died");
		}
		double initialPainLevel = body.getPain();
		try {
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
		} catch (DeathException e) {
			fail("Player died");
		}
		assertTrue(initialPainLevel < body.getPain());
	}
	@Test
	public void testPainIsPartiallyIgnored(){
		try {
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
			body.recieveWound(4, DamageType.SLASHING, body.getBodyParts().get("chest"));
		} catch (DeathException e) {
			fail("Player died");
		}
		Wound testWound = new SlashingWound(4, body.getBodyParts().get("chest"));
		double totalPain = testWound.getPain() * body.countWounds();
		assertTrue(totalPain > body.getPain());
	}
	@Test
	public void testSmallAmountOfPainIgnored(){
		try {
			body.recieveWound(3, DamageType.BLUNT, body.getBodyParts().get("chest"));
		} catch (DeathException e) {
			fail("Player died");
		}
		assertEquals(1, body.getPain(), .000001);
	}
	@Test
	public void testHealingOccursOverTime(){
		int initialDamage = 0;
		try {
			body.takeHealthDamage(50);
			initialDamage = body.getHealthDamage();
			body.passTime(10000, 1, false);
		} catch (DeathException e) {
			fail("player died");
		}
		assertTrue(body.getHealthDamage() < initialDamage);
	}
	@Test
	public void testHealingOccursFasterWhileResting(){
		try {
			int timePassed = 5000;
			body.takeHealthDamage(70);
			int initialDamage = body.getHealthDamage();
			body.passTime(timePassed, 1, false);
			int healingWhileNotResting = initialDamage - body.getHealthDamage();
			body.takeHealthDamage(healingWhileNotResting);
			initialDamage = body.getHealthDamage();
			body.passTime(timePassed, 1, true);
			int healingWhileResting = initialDamage - body.getHealthDamage();
			assertTrue (healingWhileResting > healingWhileNotResting);
		} catch (DeathException e) {
			fail("player died");
		}
	}
	@Test
	public void testHealthDamageDamagesHealth(){
		int damageInitial = body.getHealthDamage();
		int damage = 10;
		try {
			body.takeHealthDamage(damage);
		} catch (DeathException e) {
			// do nothing, doesn't matter if you die
		}
		assertEquals(damageInitial + damage, body.getHealthDamage());
	}
	@Test
	public void testHealDamageHealsHealth(){
		int damageInitial = body.getHealthDamage();
		int damage = 10;
		try {
			body.takeHealthDamage(damage);
		} catch (DeathException e) {
			// do nothing, doesn't matter if you die
		}
		body.healDamage(damage);
		assertEquals(damageInitial, body.getHealthDamage());
	}
	@Test
	public void testHealStunHealsStun(){
		int damageInitial = body.getStunDamage();
		int damage = 10;
		body.takeStunDamage(damage);
		body.healStun(damage);
		assertEquals(damageInitial, body.getStunDamage());
	}
	
	
}
