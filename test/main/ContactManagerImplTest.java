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

import org.junit.BeforeClass;
import org.junit.Test;

public class ContactManagerImplTest {
	
	private static ContactManager cm;
	private static Calendar past1, past2, future1, future2;
	private static PastMeeting p1, p2;
	private static FutureMeeting f1, f2;
	private static Contact alice, bob, charlie, dave;
	private static Set<Contact> contacts;
	private static Set<Contact> unknownContacts;
	private static Set<Contact> emptyContacts;
	
	@BeforeClass
	public static final void onlyOnce() {
        alice = new ContactImpl(0, "alice");  // Lower case names make string searching more interesting
        bob = new ContactImpl(1, "bob", "bob has notes");
        charlie = new ContactImpl(99, "charlie", "charlie is unknown");
        dave = new ContactImpl(2, "dave", "dave is known but not associated with any meetings");
        contacts = new HashSet<Contact>();
        contacts.add(alice);
        contacts.add(bob);
        unknownContacts = new HashSet<Contact>(contacts);
        unknownContacts.add(charlie);
        emptyContacts = new HashSet<Contact>();
        
        future1 = Calendar.getInstance();
        future1.clear();
        future1.set(2100, Calendar.JANUARY, 1, 12, 00);
        future2 = Calendar.getInstance();
        future2.clear();
        future2.set(2100, Calendar.JANUARY, 1, 12, 30);   
        f1 = new FutureMeetingImpl(0, contacts, future1);
        f2 = new FutureMeetingImpl(1, contacts, future2);
        
		past1 = Calendar.getInstance();
        past1.clear();
        past1.set(1900, Calendar.JANUARY, 1, 12, 00);
        past2 = Calendar.getInstance();
        past2.clear();
        past2.set(1900, Calendar.JANUARY, 1, 12, 30);
        p1 = new PastMeetingImpl(2, contacts, past1, "");
        p2 = new PastMeetingImpl(3, contacts, past2, "");
        
        cm = new ContactManagerImpl();
	}

	@Test
	public final void testEmptyContactManager() {
		assertTrue(cm.getContacts().isEmpty());
		assertTrue(cm.getContacts("simon").isEmpty());
		assertNull(cm.getFutureMeeting(0));
	}
	
	@Test
	public final void testAddThenGetNewContact() {		
		cm.addNewContact(alice.getName(), alice.getNotes());
		cm.addNewContact(bob.getName(), bob.getNotes());
		cm.addNewContact(dave.getName(), dave.getNotes());
		
		assertEquals(1, cm.getContacts(alice.getID()).size());
		
		List<Contact> bobsList = new LinkedList<Contact>(cm.getContacts(bob.getID()));
		String bobsNotes = bobsList.get(0).getNotes();
		int bobsId = bobsList.get(0).getID();
		
		assertEquals(1, bobsId);
		assertEquals("bob has notes", bobsNotes);
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
		int id0 = cm.addFutureMeeting(contacts, future1);
		int id1 = cm.addFutureMeeting(contacts, future2);
		assertEquals(0, id0);
		assertEquals(1, id1);
	}
		
	@Test(expected = IllegalArgumentException.class)
	public final void testAddFutureMeetingWithDateInPast() {
		cm.addFutureMeeting(contacts, past1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddFutureMeetingWithUnknownContact() {
		cm.addFutureMeeting(unknownContacts, future1);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testAddFutureMeetingWithEmptyContact() {
		Set<Contact> emptyContacts = new HashSet<Contact>();
		cm.addFutureMeeting(emptyContacts, future1);
	}

	@Test
	public final void testAddNewPastMeeting() {
		cm.addNewPastMeeting(contacts, past1, "");
		cm.addNewPastMeeting(contacts, past2, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddNewPastMeetingEmptyContacts() {
		cm.addNewPastMeeting(emptyContacts, past1, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddNewPastMeetingUnkownContacts() {
		cm.addNewPastMeeting(unknownContacts, past1, "");
	}
	
	@Test(expected = NullPointerException.class)
	public final void testAddNewPastMeetingNullArg() {
		String nullStr = null;
		cm.addNewPastMeeting(contacts, past1, nullStr);
	}

	@Test
	public final void testGetPastMeeting() {
		assertEquals(p1, cm.getPastMeeting(p1.getID()));
	}
	
	@Test
	public void testAddMeetingNotes() {
		cm.addMeetingNotes(p1.getID(), "Some notes");
		
		// Keep p1 up to date for comparison
		((PastMeetingImpl) p1).addNotes("Some notes");
		
		assertEquals(p1.getNotes(), cm.getPastMeeting(p1.getID()).getNotes());
	}

	@Test
	public final void testGetFutureMeeting() {
		assertEquals(f1, cm.getFutureMeeting(f1.getID()));
		assertEquals(f2, cm.getFutureMeeting(f2.getID()));
	}

	@Test
	public final void testGetMeeting() {
		assertEquals(f1, cm.getMeeting(f1.getID()));
		assertEquals(p1, cm.getMeeting(p1.getID()));
	}

	@Test
	public final void testGetFutureMeetingListContact() {
		List<FutureMeeting> expectedList = new LinkedList<FutureMeeting>();
		expectedList.add(f1);
		expectedList.add(f2);
		
		assertEquals(expectedList, cm.getFutureMeetingList(alice));
		assertEquals(expectedList, cm.getFutureMeetingList(bob));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGetFutureMeetingListUnknownContact() {
		cm.getFutureMeetingList(charlie);
	}
	
	@Test(expected = NullPointerException.class)
	public final void testGetFutureMeetingListNullContact() {
		Contact nullContact = null;
		cm.getFutureMeetingList(nullContact);
	}
	
	@Test
	public final void testGetFutureMeetingListExpectEmptyList() {
		assertTrue(cm.getFutureMeetingList(dave).isEmpty());
	}
	
	@Test
	public final void testGetFutureMeetingListChronological() {
		List<Meeting> returnedList = cm.getFutureMeetingList(alice);
		
		assertTrue(returnedList.indexOf(f1) < returnedList.indexOf(f2));
	}
	
	@Test
	public final void testGetFutureMeetingListCalendar() {
		List<Meeting> expectedList = new LinkedList<Meeting>();
		expectedList.add(f1);
		expectedList.add(f2);
		
		assertEquals(expectedList, cm.getFutureMeetingList(future1));
		
		expectedList.clear();
		expectedList.add(p1);
		expectedList.add(p2);
		
		assertEquals(expectedList, cm.getFutureMeetingList(past1));
		
		Calendar dayOff = Calendar.getInstance();
		assertTrue(cm.getFutureMeetingList(dayOff).isEmpty());
	}
	
	@Test(expected = NullPointerException.class)
	public final void testGetFutureMeetingListNullDate() {
		Calendar nullDate = null;
		cm.getFutureMeetingList(nullDate);
	}
	
	@Test
	public final void testGetFutureMeetingListCalendarChronological() {
		List<Meeting> returnedList = cm.getFutureMeetingList(future1);
		
		assertTrue(returnedList.indexOf(f1) < returnedList.indexOf(f2));
	}

	@Test
	public final void testGetPastMeetingList() {
		List<PastMeeting> expectedList = new LinkedList<PastMeeting>();
		expectedList.add(p1);
		expectedList.add(p2);
		
		assertEquals(expectedList, cm.getPastMeetingList(alice));
		assertEquals(expectedList, cm.getPastMeetingList(bob));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGetPastMeetingListUnknownContact() {
		cm.getPastMeetingList(charlie);
	}
	
	@Test(expected = NullPointerException.class)
	public final void testGetPastMeetingListNullContact() {
		Contact nullContact = null;
		cm.getPastMeetingList(nullContact);
	}
	
	@Test
	public final void testGetPastMeetingListExpectEmptyList() {
		assertTrue(cm.getPastMeetingList(dave).isEmpty());
	}
	
	@Test
	public final void testGetPastMeetingListChronological() {
		List<PastMeeting> returnedList = cm.getPastMeetingList(alice);
		
		assertTrue(returnedList.indexOf(p1) < returnedList.indexOf(p2));
	}

	@Test
	public final void testGetContactsVarArgs() {
		Set<Contact> returnedSet = cm.getContacts(alice.getID(), bob.getID(), dave.getID());
		
		assertTrue(returnedSet.contains(alice));
		assertTrue(returnedSet.contains(bob));
		assertTrue(returnedSet.contains(dave));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGetContactVarArgsUnknownContact() {
		cm.getContacts(alice.getID(), bob.getID(), charlie.getID(), dave.getID());
	}

	@Test
	public final void testGetContactsNameSearch() {
		Set<Contact> aliceAndDave = cm.getContacts("a");
		Set<Contact> shouldBeEmpty = cm.getContacts("bobo");
		
		assertTrue(aliceAndDave.contains(alice));
		assertTrue(aliceAndDave.contains(dave));
		assertTrue(shouldBeEmpty.isEmpty());
	}
	
	@Test(expected = NullPointerException.class)
	public final void testGetContactsNameSearchNullString() {
		String nullStr = null;
		cm.getContacts(nullStr);
	}

	@Test
	public final void testFlushAndLoad() {
		cm.flush();
		
		// Reload data from file into contact manager
		cm = new ContactManagerImpl();
		
		// Rerun tests for getters
		testGetPastMeeting();
		testGetFutureMeeting();
		testGetMeeting();
		testGetFutureMeetingListContact();
		testGetFutureMeetingListCalendar();
		testGetPastMeetingList();
		testGetContactsVarArgs();
		testGetContactsNameSearch();
	}
}