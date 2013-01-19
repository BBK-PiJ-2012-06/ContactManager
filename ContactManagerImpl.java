import java.util.*;
import util.CalendarHandler;
//import java.lang.Exception;

/**
 * A class to manage your contacts and meetings.
 **/
public class ContactManagerImpl implements ContactManager {
	private int next_contact_id = 0;
	private int next_meeting_id = 0;
	private Map<Integer, Contact> known_contacts = new HashMap<Integer, Contact>();
	private Map<Integer, Meeting> meetings = new HashMap<Integer, Meeting>();
	
	public ContactManagerImpl(/*params?*/) {
		// This is where we recover previous session info from file
	}
	
	/**
	 * Add a new meeting to be held in the future.
	 * 
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past, 
	 *		or if any contact is unknown / non-existent
	 **/
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// Check that the given date is in the future
		if(!CalendarHandler.isInFuture(date)) {
			throw new IllegalArgumentException
		}
		////////// throw new etc. etc.
		// Second, check that each contact is known
		////////// throw
		// Third, if we've got this far, add the meeting
		int id = next_meeting_id++;
		meetings.put(id, new MeetingImpl(id, contacts, date));		
	}
	
	/**
	 * Returns the PAST meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	 **/
	public PastMeeting getPastMeeting(int id) {
		// get() the meeting with this id (if no mapping for id, returns null, which is what we want)
		Meeting requestedMeeting = meetings.get(id);
		// check date is in past if not null
		if(requestedMeeting == null) {
			return requestedMeeting;
		}
		// if dateIsInPast
		//// return requestedMeeting
		// else throw illegal argument ex
	}
	
	/**
	 * Returns the FUTURE meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 **/
	FutureMeeting getFutureMeeting(int id);
	
	/**
	 * Returns the meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 **/
	Meeting getMeeting(int id);
	
	/**
	 * Returns the list of future meetings scheduled with this contact.
	 *
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any 
	 * duplicates.
	 *
	 * @param contact one of the user's contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty)
	 * @throws IllegalArgumentException if the contact does not exist
	 **/
	List<FutureMeeting> getFutureMeetingList(Contact contact);
	
	/**
	 * Returns the list of meetings that are scheduled for, or that took
	 * place on, the specified date
	 *
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any 
	 * duplicates.
	 *
	 * @param date the date
	 * @return the list of meetings (maybe empty)
	 **/
	List<Meeting> getFutureMeetingList(Calendar date);
	
	/**
	 * Returns the list of past meetings in which this contact has participated.
	 *
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any 
	 * duplicates.
	 *
	 * @param contact one of the user's contacts
	 * @return the list of past meeting(s) in which this contact participated(maybe empty)
	 * @throws IllegalArgumentException if the contact does not exist
	 **/
	List<PastMeeting> getPastMeetingList(Contact contact);
	
	/**
	 * Create a new record for a meeting that took place in the past.
	 *
	 * @param contacts a list of participants
	 * @param date the date on which the meeting took place
	 * @param text messages to be added about the meeting
	 * @throws IllegalArgumentException if the list of contacts is 
	 *		empty, or any of the contacts does not exist
	 * @throws NullPointerException if any of the arguments is null
	 **/
	void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text);
	
	/**
	 * Add notes to a meeting.
	 *
	 * This method is used when a future meeting takes place, and is 
	 * then converted to a past meeting (with notes).
	 *
	 * It can also be used to add notes to a past meeting at a later date.
	 *
	 * @param id the ID of the meeting
	 * @param test messages to be added about the meeting
	 * @throws IllegalArgumentException if the meeting does not exist
	 * @throws IllegalStateException if the meeting is set for a date in the future
	 * @throws NullPointerException if the notes are null
	 **/
	void addMeetingNotes(int id, String text);
	
	/**
	 * Create a new contact with the specified name and notes.
	 *
	 * @param name the name of the contact
	 * @param notes notes to be added about the contact
	 * @throws NullPointerException if the name or the notes are null
	 **/
	void addNewContact(String name, String notes);
	
	/**
	 * Returns a list containing the contacts that correspond to the IDs.
	 *
	 * @param ids an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs
	 * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
	 **/
	Set<Contact> getContacts(int... ids);
	
	/**
	 * Returns a list with the contacts whose name contains that string.
	 *
	 * @param name the string to search for
	 * @return a list with the contacts whose name contains that string
	 * @throws NullPointerException if the parameter is null
	 **/
	Set<Contact> getContacts(String name);
	
	/**
	 * Saves all data to disk.
	 *
	 * This method must be executed when the program is
	 * closed and when/if the the user requests it.
	 **/
	void flush();
}