//package test;
//
//import health.Body;
//import health.BodyPart;
//
//import static org.junit.Assert.*;
//import org.junit.Test;
//
//import mothers.CharacterMother;
//
//public class UnitTestBody {
//
//	public static void main(String[] args){
//		
//	}
//	private static void printFatigue(){
//		
//	}
//	@Test
//	public void testSmallFatigueIsAbsorbed(){
//		Body b = new Body(CharacterMother.getDickDefenderOfLife());
//		double fagigueInitial = b.getFatigue();
//		b.takeSteminaDamage(.1);
//		assertEquals(fagigueInitial, b.getFatigue(), 0);
//	}
//	@Test
//	public void testLargeFatiguePartiallyAbsorbed(){
//		Body b = new Body(CharacterMother.getDickDefenderOfLife());
//		double fagigueInitial = b.getFatigue();
//		b.takeSteminaDamage(1000);
//		assertNotEquals(fagigueInitial, b.getFatigue());
//	}
//	@Test
//	public void testLargeFatigueNotFullyAbsorbed(){
//		Body b = new Body(CharacterMother.getDickDefenderOfLife());
//		double fagigueInitial = b.getFatigue();
//		double fatigueTaken = 10;
//		b.takeSteminaDamage(10);
//		assertTrue(fagigueInitial+fatigueTaken > b.getFatigue());
//	}
//}
