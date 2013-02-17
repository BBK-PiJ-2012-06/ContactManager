/**
 * 
 */
package main;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContactManagerImplTest {
	
	private ContactManager cm;
	private static Calendar past1, past2, future1, future2;
	private static Contact alice, bob, charlie;
	private static Set<Contact> contacts;
	private static Set<Contact> unknownContacts;
	
	@BeforeClass
	public static void onlyOnce() {
		past1 = Calendar.getInstance();
        past1.clear();
        past1.set(1900, Calendar.JANUARY, 1, 00, 00);
        past2 = Calendar.getInstance();
        past2.clear();
        past2.set(1901, Calendar.JANUARY, 1, 00, 00);
        future1 = Calendar.getInstance();
        future1.clear();
        future1.set(2100, Calendar.JANUARY, 1, 00, 00);
        future2 = Calendar.getInstance();
        future2.clear();
        future2.set(2101, Calendar.JANUARY, 1, 00, 00);   
        
        alice = new ContactImpl(0, "Alice");
        bob = new ContactImpl(1, "Bob", "Bob has notes");
        charlie = new ContactImpl(2, "Charlie", "Charlie is unknown");
        contacts = new HashSet<Contact>();
        contacts.add(alice);
        contacts.add(bob);
        unknownContacts = new HashSet<Contact>(contacts);
        unknownContacts.add(charlie);
	}
	
	@Before
	public void setUp() {
		cm = new ContactManagerImpl();
	}
	
	@After
	public void tearDown() {
		cm.flush();
	}

	@Test
	public final void testEmptyContactManager() {
		assertTrue(cm.getContacts().isEmpty());
		assertTrue(cm.getContacts("simon").isEmpty());
	}
	
	@Test
	public final void testAddThenGetNewContact() {		
		cm.addNewContact(alice.getName(), alice.getNotes());
		cm.addNewContact(bob.getName(), bob.getNotes());
		
		assertEquals(1, cm.getContacts(alice.getID()).size());
		
		List<Contact> bobsList = new LinkedList<Contact>(cm.getContacts(bob.getID()));
		String bobsNotes = bobsList.get(0).getNotes();
		int bobsId = bobsList.get(0).getID();
		
		assertEquals(1, bobsId);
		assertEquals("Bob has notes", bobsNotes);
	}
	
	@Test(expected = NullPointerException.class)
	public final void testAddContactNullName() {
		String nullStr = null;
		cm.addNewContact(nullStr, alice.getNotes());
	}
	
	@Test(expected = NullPointerException.class)
	public final void testAddContactNullNotes() {
		String nullStr = null;
		cm.addNewContact(alice.getName(), nullStr);
	}
	
	@Test
	public final void testAddFutureMeeting() {
		int id1 = cm.addFutureMeeting(contacts, future1);
		int id2 = cm.addFutureMeeting(contacts, future2);
		
		assertEquals(0, id1);
		assertEquals(1, id2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddFutureMeetingWithDateInPast() {
		cm.addFutureMeeting(contacts, past1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddFutureMeetingWithUnknownContact() {
		cm.addFutureMeeting(unknownContacts, future1);
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getPastMeeting(int)}.
	 */
	@Test
	public final void testGetPastMeeting() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getFutureMeeting(int)}.
	 */
	@Test
	public final void testGetFutureMeeting() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getMeeting(int)}.
	 */
	@Test
	public final void testGetMeeting() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getFutureMeetingList(main.Contact)}.
	 */
	@Test
	public final void testGetFutureMeetingListContact() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getFutureMeetingList(java.util.Calendar)}.
	 */
	@Test
	public final void testGetFutureMeetingListCalendar() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getPastMeetingList(main.Contact)}.
	 */
	@Test
	public final void testGetPastMeetingList() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#addNewPastMeeting(java.util.Set, java.util.Calendar, java.lang.String)}.
	 */
	@Test
	public final void testAddNewPastMeeting() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#addMeetingNotes(int, java.lang.String)}.
	 */
	@Test
	public final void testAddMeetingNotes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getContacts(int[])}.
	 */
	@Test
	public final void testGetContactsIntArray() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#getContacts(java.lang.String)}.
	 */
	@Test
	public final void testGetContactsString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.ContactManagerImpl#flush()}.
	 */
	@Test
	public final void testFlush() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	public final void testObject() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	public final void testGetClass() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	public final void testClone() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	public final void testNotify() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	public final void testNotifyAll() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	public final void testWaitLong() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	public final void testWaitLongInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	public final void testWait() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	public final void testFinalize() {
		fail("Not yet implemented"); // TODO
	}

}
