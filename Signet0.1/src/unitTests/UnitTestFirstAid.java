package unitTests;

import static org.junit.Assert.*;
import items.Bandage;
import items.Ointment;

import org.junit.Test;

public class UnitTestFirstAid {

	@Test
	public void bandageDoesntEqualOintment() {
		Bandage arg1 = new Bandage("bandage", "a bandage", 2.5, 2.5, 2.5);
		Ointment arg2 = new Ointment("bandage", "a bandage",  2.5, 2.5, 2.5);
		assertNotEquals(arg1, arg2);
	}
	@Test
	public void ointmentDoesntEqualBandage() {
		Ointment arg1 = new Ointment("ointment", "an ointment", 2.5, 2.5, 2.5);
		Bandage arg2 = new Bandage("ointment", "an ointment", 2.5, 2.5, 2.5);
		assertNotEquals(arg1, arg2);
	}
	@Test
	public void ointmentEquals() {
		Ointment arg1 = new Ointment("ointment", "an ointment", 2.5, 2.5, 2.5);
		Ointment arg2 = new Ointment("ointment", "an ointment", 2.5, 2.5, 2.5);
		assertTrue(arg1.equals(arg2));
	}
	@Test
	public void bandageEquals() {
		Bandage arg1 = new Bandage("bandage", "", 2.5, 2.5, 2.5);
		Bandage arg2 = new Bandage("bandage", "", 2.5, 2.5, 2.5);
		assertTrue(arg1.equals(arg2));
	}

}
