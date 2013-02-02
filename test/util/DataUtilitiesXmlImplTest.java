package util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.*;

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
	private Calendar past1, past2, future1, future2;
	
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

        past1 = Calendar.getInstance();
        past1.clear();
        past1.set(1900, Calendar.JANUARY, 1, 00, 00);
        past2 = Calendar.getInstance();
        past2.clear();
        past2.set(1901, Calendar.JANUARY, 1, 00, 00);
        p1 = new PastMeetingImpl(1, contacts, past1,  "note");
		p2 = new PastMeetingImpl(2, contacts, past2, "note");

        future1 = Calendar.getInstance();
        future1.clear();
        future1.set(2100, Calendar.JANUARY, 1, 00, 00);
        future2 = Calendar.getInstance();
        future2.clear();
        future2.set(2101, Calendar.JANUARY, 1, 00, 00);
        f1 = new FutureMeetingImpl(1, contacts, future1);
        f2 = new FutureMeetingImpl(2, contacts, future2);
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
