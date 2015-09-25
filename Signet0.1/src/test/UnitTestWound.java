//package test;
//
//import static org.junit.Assert.*;
//
//import java.lang.reflect.*;
//
//import injuries.BluntWound;
//import injuries.BurnWound;
//import injuries.PiercingWound;
//import injuries.PiercingWound;
//import injuries.SlashingWound;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//
//import health.BodyPart;
//import health.Wound;
//import misc.DamageType;
//import bodyparts.*;
//
//public class UnitTestWound {
//	
//	private static BodyPart GENERIC_TESTING_BODYPART = new Arm("generic arm");
//	
//	@Test
//	public void testLightWoundDealsNoDamage(){
//		Wound wound = new SlashingWound(1, GENERIC_TESTING_BODYPART);
//		double[] array = wound.passTime(24000, 1, false);
//		assertEquals(0, array[0], 0);
//		assertEquals(0, array[1], 0);
//		assertEquals(0, array[2], 0);
//	}
//	
//	@Test
//	public void testDeepWoundDealsDamage(){
//		Wound wound = new SlashingWound(6, GENERIC_TESTING_BODYPART);
//		double[] array = wound.passTime(24000, 1, false);
//		assertNotEquals(0.0, array[0], 0.1);
//	}
//	@Test
//	public void testDeepWoundDealsNoStunOrFatigue(){
//		Wound wound = new SlashingWound(6, GENERIC_TESTING_BODYPART);
//		double[] array = wound.passTime(24000, 1, false);
//		assertEquals(0, array[1], 0);
//		assertEquals(0, array[2], 0);
//	}
//	@Test
//	public void testLightWoundDealsNoInstantDamage(){
//		Wound wound = new SlashingWound(1, GENERIC_TESTING_BODYPART);		
//		int[] array = wound.getInstantDamage();
//		assertEquals(0, array[0]);
//		assertEquals(0, array[1]);
//		assertEquals(0, array[2]);
//	}
//	@Test
//	public void testDeepWoundDealsInstantDamage(){
//		Wound wound = new PiercingWound(6, GENERIC_TESTING_BODYPART);
//		int[] array = wound.getInstantDamage();
//		assertNotEquals(0, array[0]);
//	}
//	@Test
//	public void testDeepWoundDealsNoInstantStunOrFatigue(){
//		Wound wound = new PiercingWound(6, GENERIC_TESTING_BODYPART);
//		int[] array = wound.getInstantDamage();
//		assertEquals(0, array[1]);
//		assertEquals(0, array[2]);
//	}
//	@Test
//	public void cannotCreateWoundWorseThan7(){
//		Wound w = new BluntWound(7, GENERIC_TESTING_BODYPART);
//		w = new PiercingWound(6, GENERIC_TESTING_BODYPART);
//		w = new SlashingWound(5, GENERIC_TESTING_BODYPART);
//		w = new BurnWound(4, GENERIC_TESTING_BODYPART);
//		try {
//			w= new BluntWound(8, GENERIC_TESTING_BODYPART);
//			fail();
//		} catch (IllegalArgumentException e) {
//			// do nothing
//		}
//		
//		try {
//			w= new PiercingWound(9, GENERIC_TESTING_BODYPART);
//			fail();
//		} catch (IllegalArgumentException e) {
//			// do nothing
//		}
//		
//		try {
//			w= new SlashingWound(10, GENERIC_TESTING_BODYPART);
//			fail();
//		} catch (IllegalArgumentException e) {
//			// do nothing
//		}
//		
//		try {
//			w= new BurnWound(11, GENERIC_TESTING_BODYPART);
//			fail();
//		} catch (IllegalArgumentException e) {
//			// do nothing
//		}
//	}	
//	
//	@Test
//	public void testWoundHealsOverTimeNotResting(){
//		int initialSeverity = 3;
//		Wound w = new SlashingWound(initialSeverity, GENERIC_TESTING_BODYPART);
//		w.passTime(300000, 1, false);
//		assertTrue(initialSeverity > w.getCurrentSeverity());
//	}
//	@Test
//	public void testWoundHealsOverTimeResting(){
//		int initialSeverity = 3;
//		Wound w = new PiercingWound(initialSeverity, GENERIC_TESTING_BODYPART);
//		w.passTime(200000, 1, true);
//		assertTrue(initialSeverity > w.getCurrentSeverity());
//	}
//	
////	@Test
//	public void testTreatWoundUsingMock(){
//		fail("not implemented");
//	}
////	@Test
//	public void testInfectionsAreCalledUsingMock(){
//		fail("not implemented");
//	}
////	@Test
//	public void testBandageCalledUsingMock(){
//		fail("not implemented");
//	}
////	@Test
//	public void testOintmentCalledUsingMock(){
//		fail("not implemented");
//	}
//}
