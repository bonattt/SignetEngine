package unitTests;

import health.Body;
import health.BodyPart;
import health.DamageType;
import static org.junit.Assert.*;
import misc.DeathException;

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
