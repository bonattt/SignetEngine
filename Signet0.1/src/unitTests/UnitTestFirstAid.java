package unitTests;

import static org.junit.Assert.*;
import items.Bandage;
import items.Ointment;

import org.junit.Test;

public class UnitTestFirstAid {

	@Test
	public void bandageDoesntEqualOintment() {
		Bandage arg1 = new Bandage(2.5, 2.5, 2.5, "bandage");
		Ointment arg2 = new Ointment(2.5, 2.5, 2.5, "bandage");
		assertNotEquals(arg1, arg2);
	}
	@Test
	public void ointmentDoesntEqualBandage() {
		Ointment arg1 = new Ointment(2.5, 2.5, 2.5, "ointment");
		Bandage arg2 = new Bandage(2.5, 2.5, 2.5, "ointment");
		assertNotEquals(arg1, arg2);
	}
	@Test
	public void ointmentEquals() {
		Ointment arg1 = new Ointment(2.5, 2.5, 2.5, "ointment");
		Ointment arg2 = new Ointment(2.5, 2.5, 2.5, "ointment");
		assertTrue(arg1.equals(arg2));
	}
	@Test
	public void bandageEquals() {
		Bandage arg1 = new Bandage(2.5, 2.5, 2.5, "bandage");
		Bandage arg2 = new Bandage(2.5, 2.5, 2.5, "bandage");
		assertTrue(arg1.equals(arg2));
	}

}
