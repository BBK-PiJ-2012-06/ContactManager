package util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import main.*;

import org.junit.Before;
import org.junit.Test;

public class DataUtilXmlImplTest {
	private DataUtil data;
	private String filename;
	private Set<Contact> contacts;
	private List<PastMeeting> pastmeetings;
	private List<FutureMeeting> futuremeetings;
	private Contact alice;
	private Contact bob;
	private PastMeeting p1, p2;
	private FutureMeeting f1, f2;
	private Calendar past1, past2, future1, future2;
	
	@Before
	public void buildUp() {
		data = new DataUtilXmlImpl();
		filename = "." + File.separator + "data_util_test_file.txt";
		
		alice = new ContactImpl(1, "Alice");
		bob = new ContactImpl(2, "Bob", "Bob has notes");
		contacts = new HashSet<Contact>();
		contacts.add(alice);
		contacts.add(bob);

        past1 = Calendar.getInstance();
        past1.clear();
        past1.set(1900, Calendar.JANUARY, 1, 00, 00);
        past2 = Calendar.getInstance();
        past2.clear();
        past2.set(1901, Calendar.JANUARY, 1, 00, 00);
        p1 = new PastMeetingImpl(1, contacts, past1,  "note");
		p2 = new PastMeetingImpl(2, contacts, past2, "note");
		
		pastmeetings = new LinkedList<PastMeeting>();
		pastmeetings.add(p1);
		pastmeetings.add(p2);

        future1 = Calendar.getInstance();
        future1.clear();
        future1.set(2100, Calendar.JANUARY, 1, 00, 00);
        future2 = Calendar.getInstance();
        future2.clear();
        future2.set(2101, Calendar.JANUARY, 1, 00, 00);
        f1 = new FutureMeetingImpl(1, contacts, future1);
        f2 = new FutureMeetingImpl(2, contacts, future2);
        
        futuremeetings = new LinkedList<FutureMeeting>();
        futuremeetings.add(f1);
        futuremeetings.add(f2);
    }

	@Test
	public void testsAddAndGetContacts() {
		data.addContacts(contacts);
		Set<Contact> returned = data.getContacts();
		
		assertTrue(returned.containsAll(contacts));
	}
	
	@Test(expected = NullPointerException.class)
	public void testsAddNullContacts() {
		Set<Contact> null_contacts = null;
		data.addContacts(null_contacts);
	}
	
	@Test
	public void testsAddAndGetPastMeetings() {
		data.addPastMeetings(pastmeetings);
		List<PastMeeting> returned = data.getPastMeetings();
		
		assertTrue(returned.containsAll(pastmeetings));
	}
	
	@Test(expected = NullPointerException.class)
	public void testsAddNullPastMeetings() {
		List<PastMeeting> null_meetings = null;
		data.addPastMeetings(null_meetings);
	}

	@Test
	public void testsAddAndGetFutureMeetings() {
		data.addFutureMeetings(futuremeetings);
		List<FutureMeeting> returned = data.getFutureMeetings();
		
		assertTrue(returned.containsAll(futuremeetings));
	}
	
	@Test(expected = NullPointerException.class)
	public void testsAddNullFutureMeetings() {
		List<FutureMeeting> null_meetings = null;
		data.addFutureMeetings(null_meetings);
	}
	
	@Test
	public void testsSaveAndLoadData() throws Exception {
		//Add data
		data.addContacts(contacts);
		data.addFutureMeetings(futuremeetings);
		data.addPastMeetings(pastmeetings);
		//Save
		data.saveData(filename);
		//Load
		data.loadData(filename);
	}
	
	@Test
	public void testsExtractContacts() throws Exception {
		//Ensure testsSaveAndLoadData() is carried out first so we have a file to load from
		testsSaveAndLoadData();
		
		//Retrieve the reloaded contacts
		Set<Contact> extractedContacts = data.getContacts();
		
		assertTrue(extractedContacts.containsAll(contacts));
		assertEquals(contacts, extractedContacts);
	}
	
	@Test
	public void testsExtractPastMeetings() throws Exception {
		//Ensure testsSaveAndLoadData() is carried out first so we have a file to load from
		testsSaveAndLoadData();
		
		//Retrieve the reloaded past meetings
		List<PastMeeting> extractedMeetings = data.getPastMeetings();
		
		assertTrue(extractedMeetings.containsAll(pastmeetings));
		assertEquals(pastmeetings, extractedMeetings);
	}
	
	@Test
	public void testsExtractFutureMeetings() throws Exception {
		//Ensure testsSaveAndLoadData() is carried out first so we have a file to load from
		testsSaveAndLoadData();
		
		//Retrieve the reloaded future meetings
		List<FutureMeeting> extractedMeetings = data.getFutureMeetings();
		
		assertTrue(extractedMeetings.containsAll(futuremeetings));
		assertEquals(futuremeetings, extractedMeetings);
	}
}
