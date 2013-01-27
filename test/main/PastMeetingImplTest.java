package main;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

public class PastMeetingImplTest {
	private Contact c1, c2;
	private Set<Contact> contacts;
	private Meeting m;
	private PastMeeting p, p_too, not_p;
	private int h, h_too, not_h;
	private Calendar date;

	@Before
	public void buildUp() {
		c1 = new ContactImpl(1, "Alice");
		c2 = new ContactImpl(2, "Bob");
		contacts = new HashSet<Contact>();
		contacts.add(c1);
		contacts.add(c2);
		date = Calendar.getInstance();
		date.clear();
		date.set(1900, Calendar.JANUARY, 1, 00, 00);
		m = new MeetingImpl(1, contacts, date);
		p = new PastMeetingImpl(1, contacts, date, "notes");
		p_too = new PastMeetingImpl(m, "notes");
		not_p = new PastMeetingImpl(2, contacts, date, "not the same as p!");
	}

	// As most of the functionality of PastMeeting is inherited from Meeting,
	// there
	// should be no need to test it here, but for completeness and safety's sake
	// (not
	// to mention the fact that the code has already been written) we will test
	// it here too
	@Test
	public void testsGetID() {
		assertEquals(1, p.getID());
		;
	}

	@Test
	public void testsGetDate() {
		assertEquals(date, p.getDate());
	}

	@Test
	public void testsGetContacts() {
		assertEquals(contacts, p.getContacts());
	}

	@Test
	public void testsEquals() {
		PastMeeting p_again = new PastMeetingImpl(1, contacts, date, "notes");
		assertTrue(p.equals(p_again));
	}

	@Test
	public void testsEquivalenceOfConstructors() {
		assertTrue(p.equals(p_too));
	}

	@Test
	public void testsHash() {
		h = p.hashCode();
		h_too = p_too.hashCode();
		not_h = not_p.hashCode();

		// hashCode()'s contract:
		// -- must return the same integer while running, as long as no mutable
		// field involved in equals() changes
		// -- if two objects are equal under equals(), they must return the same
		// hashCode()
		// -- two unequal objects under equals() are not required to return
		// different hashes, but it is preferable.
		assertEquals(h, p.hashCode());
		assertFalse(h == not_h);
		assertEquals(h, h_too);
	}
}