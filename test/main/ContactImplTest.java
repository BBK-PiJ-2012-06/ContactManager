package main;

import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest {
	private Contact c1, c2, c2_too;
	private final String n = "here's a note";
	private int h1, h2, h2_too;

	@Before
	public void buildUp() {
		c1 = new ContactImpl(1, "Alice", "some notes");
		c2 = new ContactImpl(2, "Bob");
		c2_too = new ContactImpl(2, "Bob");
	}

	@Test
	public void testsGetID() {
		assertEquals(1, c1.getID());
		assertEquals(2, c2.getID());
	}

	@Test
	public void testsGetName() {
		assertEquals("Alice", c1.getName());
		assertEquals("Bob", c2.getName());
	}

	@Test
	public void testsGetNotes() {
		assertEquals("some notes", c1.getNotes());
	}

	@Test
	public void testsGetNotesAfterFirstAddNotes() {
		c2.addNotes(n);
		assertEquals(n, c2.getNotes());
	}

	@Test
	public void testsGetNotesTwoLines() {
		c1.addNotes(n);
		assertEquals("some notes" + "\n" + n, c1.getNotes());
	}

	@Test
	public void testsEquals() {
		assertTrue(c2.equals(c2_too));
	}

	@Test
	public void testsHash() {
		h1 = c1.hashCode();
		h2 = c2.hashCode();
		h2_too = c2_too.hashCode();

		// hashCode()'s contract:
		// -- must return the same integer while running, as long as no mutable
		// field involved in equals() changes
		// -- if two objects are equal under equals(), they must return the same
		// hashCode()
		// -- two unequal objects under equals() are not required to return
		// different hashes, but it is preferable.
		assertEquals(c1.hashCode(), h1);
		assertFalse(h1 == h2);
		assertEquals(h2, h2_too);
		c1.addNotes("this should change c1's hash");
		assertFalse(h1 == c1.hashCode());
	}
}