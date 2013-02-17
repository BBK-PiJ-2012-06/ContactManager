package main;

import java.util.*;
import java.util.concurrent.Future;
import java.io.File;
import java.io.IOException;
import java.lang.Exception;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import util.CalendarUtil;
import util.DataManager;
import util.DataManagerImpl;

/**
 * A class to manage your contacts and meetings.
 **/
public class ContactManagerImpl implements ContactManager {
	private final String DATA_FILE = "contacts.txt";
	private DataManager data = new DataManagerImpl();
	private int next_contact_id = 0;
	private int next_meeting_id = 0;
	private Set<Contact> knownContacts = new HashSet<Contact>();
	private List<PastMeeting> pastMeetings = new LinkedList<PastMeeting>();
	private List<FutureMeeting> futureMeetings = new LinkedList<FutureMeeting>();
	private Map<Integer, Contact> contactIds = new HashMap<Integer, Contact>();
	private Map<Integer, PastMeeting> pastMeetingIds = new HashMap<Integer, PastMeeting>();
	private Map<Integer, FutureMeeting> futureMeetingIds = new HashMap<Integer, FutureMeeting>();
	private Map<Contact, Set<PastMeeting>> pastMeetingContacts = new HashMap<Contact, Set<PastMeeting>>();
	private Map<Contact, Set<FutureMeeting>> futureMeetingContacts = new HashMap<Contact, Set<FutureMeeting>>();
	private Map<Calendar, Set<Meeting>> meetingDates = new HashMap<Calendar, Set<Meeting>>();
	
	/**
	 * Creates a ContactManager using data from previous sessions stored in the local file
	 * "contacts.txt". If no such file exists an empty ContactManager will be created.
	 */
	public ContactManagerImpl() {
		// Recover previous session info from file if it exists
		if(new File(DATA_FILE).isFile()) {
			try {
				// Load and retrieve the stored contacts and meetings
				data.loadData(DATA_FILE);
				knownContacts = data.getContacts();
				pastMeetings = data.getPastMeetings();
				futureMeetings = data.getFutureMeetings();
				
				// Use this data to populate ID & contact maps
				populateMaps();
			} catch (IOException e) {
				System.out.println(DATA_FILE + " could not be read");
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				System.out.println("Could not parse XML in " + DATA_FILE);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Populates the mappings from ID to contacts, past meetings and future meetings,
	 * plus the mappings from contact to meetings attended/attending.  
	 */
	private void populateMaps() {
		// Contacts
		for(Contact contact : knownContacts) {
			contactIds.put(contact.getID(), contact);
			// Initialise the set of past and future meetings attended,
			// using tree set to keep meetings ordered chronologically
			// (see http://java2novice.com/java-collections-and-util/treeset/comparator-object/)
			pastMeetingContacts.put(contact, new TreeSet<PastMeeting>(CalendarUtil.getMeetingComparator()));
			futureMeetingContacts.put(contact, new TreeSet<FutureMeeting>(CalendarUtil.getMeetingComparator()));
		}
		// Past meetings
		for(PastMeeting meeting : pastMeetings) {
			pastMeetingIds.put(meeting.getID(), meeting);
			for(Contact attendee : meeting.getContacts()) {
				pastMeetingContacts.get(attendee).add(meeting);
			}
			// Initialise the set of meetings that occurred on this date
			meetingDates.put(CalendarUtil.trimTime(meeting.getDate()), new TreeSet<Meeting>(CalendarUtil.getMeetingComparator()));
		}
		// Future meetings
		for(FutureMeeting meeting : futureMeetings) {
			futureMeetingIds.put(meeting.getID(), meeting);
			for(Contact attendee : meeting.getContacts()) {
				futureMeetingContacts.get(attendee).add(meeting);
			}
		}
	}

	/**
	 * Add a new meeting to be held in the future.
	 * 
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past, or if any
	 * 	contact is unknown / non-existent
	 **/
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// Check that the given date is in the future
		if (!CalendarUtil.isInFuture(date)) {
			throw new IllegalArgumentException("Given date, " + CalendarUtil.format(date) + ", is not in the future");
		}

		// Make sure each contact is known
		try {
			checkContactsAreKnown(contacts);
		} catch(NullPointerException e) {
			throw new NullPointerException("Given contacts contains null contact");
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Given contacts contains unknown contact");
		}
		
		// All is well, add the meeting
		int id = next_meeting_id++;
		FutureMeeting newMeeting = new FutureMeetingImpl(id, contacts, date);
		futureMeetings.add(newMeeting);
		futureMeetingIds.put(id, newMeeting);
		
		return id;
	}

	/**
	 * Checks that no contacts in the given set are either null or unknown.
	 * 
	 * @param contacts the set of contacts to check
	 * @throws NullPointerException if any contact is null
	 * @throws IllegalArgumentException if any contact is unknown
	 */
	private void checkContactsAreKnown(Set<Contact> contacts) {
		for(Contact contact : contacts) {
			if(contact == null) {
				throw new NullPointerException();
			}
			if(!contactIds.containsKey(contact.getID())) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Returns the PAST meeting with the requested ID, or null if there is none.
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	 **/
	@Override
	public PastMeeting getPastMeeting(int id) {
		// Check that the ID isn't that of a future meeting
		if(futureMeetingIds.containsKey(id)) {
			throw new IllegalArgumentException("Requested ID, " + id + ", belongs to a future meeting");
		}

		// Fetch the meeting with this id (if no mapping for id get(id) returns null)
		PastMeeting requestedMeeting = pastMeetingIds.get(id);
		return requestedMeeting;
	}

	/**
	 * Returns the FUTURE meeting with the requested ID, or null if there is none.
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 **/
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// Check that the ID isn't that of a past meeting
		if(pastMeetingIds.containsKey(id)) {
			throw new IllegalArgumentException("Requested ID, " + id + ", belongs to a past meeting");
		}

		// Fetch the meeting with this id (if no mapping for id get(id) returns null)
		FutureMeeting requestedMeeting = futureMeetingIds.get(id);
		return requestedMeeting;
	}

	/**
	 * Returns the meeting with the requested ID, or null if there is none.
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 **/
	@Override
	public Meeting getMeeting(int id) {
		Meeting requestedMeeting = pastMeetingIds.get(id);
		
		if(requestedMeeting == null) {
			return (Meeting) futureMeetingIds.get(id);
		}
		return requestedMeeting; 
	}

	/**
	 * Returns the list of future meetings scheduled with this contact.
	 * 
	 * If there are none, the returned list will be empty. Otherwise, the list
	 * will be chronologically sorted and will not contain any duplicates.
	 * 
	 * @param contact one of the user's contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty)
	 * @throws IllegalArgumentException if the contact does not exist
	 **/
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// Check that this contact is known and not null
		if(contact == null) {
			throw new NullPointerException("Given contact is null");
		}
		if(!knownContacts.contains(contact)) {
			throw new IllegalArgumentException("Given contact does not exist");
		}
		
		// Fetch the set of future meetings this contact is attending
		// (tree set has taken care of chronological ordering)
		// (may be empty)
		return new LinkedList<Meeting>(futureMeetingContacts.get(contact));
	}

	/**
	 * Returns the list of meetings that are scheduled for, or that took place
	 * on, the specified date (ignoring the time).
	 * NB, although the name of this method is misleading, it will not be changed
	 * as this would violate the method's contract.
	 * 
	 * If there are none, the returned list will be empty. Otherwise, the list
	 * will be chronologically sorted and will not contain any duplicates.
	 * 
	 * @param date the date
	 * @return the list of meetings (maybe empty)
	 **/
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// Fetch meetings on this date
		Set<Meeting> requestedMeetings = meetingDates.get(CalendarUtil.trimTime(date));
		
		// If no meetings on this date, requestedMeetings is null
		if(requestedMeetings == null) {
			// Return empty list
			return new LinkedList<Meeting>();
		}
		
		return new LinkedList<Meeting>(requestedMeetings);
	}

	/**
	 * Returns the list of past meetings in which this contact has participated.
	 * 
	 * If there are none, the returned list will be empty. Otherwise, the list
	 * will be chronologically sorted and will not contain any duplicates.
	 * 
	 * @param contact one of the user's contacts
	 * @return the list of past meeting(s) in which this contact participated(maybe empty)
	 * @throws IllegalArgumentException if the contact does not exist
	 **/
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// Check that this contact is known and not null
		if(contact == null) {
			throw new NullPointerException("Given contact is null");
		}
		if(!knownContacts.contains(contact)) {
			throw new IllegalArgumentException("Given contact does not exist");
		}
		
		// Fetch the set of past meetings this contact attended
		// (tree set has taken care of chronological ordering)
		// (may be empty)
		return new LinkedList<PastMeeting>(pastMeetingContacts.get(contact));		
	}

	/**
	 * Create a new record for a meeting that took place in the past.
	 * 
	 * @param contacts a list of participants
	 * @param date the date on which the meeting took place
	 * @param text messages to be added about the meeting
	 * @throws IllegalArgumentException if the list of contacts is empty, or any of the 
	 * 	contacts does not exist
	 * @throws NullPointerException if any of the arguments is null
	 **/
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		
	}

	/**
	 * Add notes to a meeting.
	 * 
	 * This method is used when a future meeting takes place, and is then
	 * converted to a past meeting (with notes).
	 * 
	 * It can also be used to add notes to a past meeting at a later date.
	 * 
	 * @param id
	 *            the ID of the meeting
	 * @param test
	 *            messages to be added about the meeting
	 * @throws IllegalArgumentException
	 *             if the meeting does not exist
	 * @throws IllegalStateException
	 *             if the meeting is set for a date in the future
	 * @throws NullPointerException
	 *             if the notes are null
	 **/
	@Override
	void addMeetingNotes(int id, String text);

	/**
	 * Create a new contact with the specified name and notes.
	 * 
	 * @param name
	 *            the name of the contact
	 * @param notes
	 *            notes to be added about the contact
	 * @throws NullPointerException
	 *             if the name or the notes are null
	 **/
	@Override
	void addNewContact(String name, String notes);

	/**
	 * Returns a list containing the contacts that correspond to the IDs.
	 * 
	 * @param ids
	 *            an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs
	 * @throws IllegalArgumentException
	 *             if any of the IDs does not correspond to a real contact
	 **/
	@Override
	Set<Contact> getContacts(int... ids);

	/**
	 * Returns a list with the contacts whose name contains that string.
	 * 
	 * @param name
	 *            the string to search for
	 * @return a list with the contacts whose name contains that string
	 * @throws NullPointerException
	 *             if the parameter is null
	 **/
	@Override
	Set<Contact> getContacts(String name);

	/**
	 * Saves all data to disk.
	 * 
	 * This method must be executed when the program is closed and when/if the
	 * the user requests it.
	 **/
	@Override
	void flush();
}