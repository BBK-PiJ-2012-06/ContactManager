package main;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

public class MeetingImplTest {
	private Contact c1, c2;
	private Set<Contact> contacts;
	private Meeting m, m_too, not_m;
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
		m_too = new MeetingImpl(1, contacts, date);
		not_m = new MeetingImpl(2, contacts, date);
	}

	@Test
	public void testsGetID() {
		assertEquals(1, m.getID());
		;
	}

	@Test
	public void testsGetDate() {
		assertEquals(date, m.getDate());
	}

	@Test
	public void testsGetContacts() {
		assertEquals(contacts, m.getContacts());
	}

	@Test
	public void testsEquals() {
		assertTrue(m.equals(m_too));
	}

	@Test
	public void testsHash() {
		h = m.hashCode();
		h_too = m_too.hashCode();
		not_h = not_m.hashCode();

		// hashCode()'s contract:
		// -- must return the same integer while running, as long as no mutable
		// field involved in equals() changes
		// -- if two objects are equal under equals(), they must return the same
		// hashCode()
		// -- two unequal objects under equals() are not required to return
		// different hashes, but it is preferable.
		assertEquals(h, m.hashCode());
		assertFalse(h == not_h);
		assertEquals(h, h_too);
		// NB as all MeetingImpl's fields are final/immutable the situation
		// where the hash changes as a result
		// of a change to its fields should never occur.
	}
}