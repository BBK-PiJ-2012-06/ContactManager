package util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.Contact;
import main.ContactImpl;
import main.FutureMeeting;
import main.PastMeeting;
import main.PastMeetingImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataUtilitiesXmlImplTest {
	private DataUtilities data;
	private File file;
	private String filename;
	private Set<Contact> contacts;
	private List<PastMeeting> pastmeetings;
	private List<FutureMeeting> futuremeetings;
	private Contact alice, bob;
	private PastMeeting p1, p2;
	private FutureMeeting f1, f2;
	private Calendar past1, past2, past3, past4;
	
	@Before
	public void buildUp() {
		data = new DataUtilitiesXmlImpl();
		filename = "." + File.separator + "test_file.txt";
		file = new File(filename);
		
		alice = new ContactImpl(1, "Alice");
		bob = new ContactImpl(2, "Bob");
		contacts = new HashSet<Contact>();
		contacts.add(alice);
		contacts.add(bob);
		
		past1 = new Calendar
		p1 = new PastMeetingImpl(1, contacts, "note");
		p2 = new 
	}

	@After
	public void cleanUp() {
	}

	@Test
	public void testsLoadFile() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testsWriteFile() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testsAddContacts() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testsGetContacts() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testsAddPastMeetings() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testsGetPastMeetings() {
		fail("Not yet implemented");
	}

	@Test
	public void testsAddFutureMeetings() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testsGetFutureMeetings() {
		fail("Not yet implemented");
	}
}
