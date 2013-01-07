import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

/**
 * A class to represent meetings.
 *
 * Meetings have unique IDs, scheduled date and a list of participating contacts.
 **/
 public class MeetingImpl implements Meeting {
	private int id;
	private Calendar date = null;
	private Set<Contact> contacts = new HashSet<Contact>();
	// nextAvailableId is used to assign IDs to new meetings, is static as it is 
	// only needed by the class, not objects.
	private static int nextAvailableId = 0;
	
	/**
	 * Constructs a MeetingImpl with the given set of contacts and date.
	 *
	 * @param contacts the set of contacts attending the meeting
	 * @param date the date on which the meeting is to take place
	 **/
	public MeetingImpl(Set<Contact> contacts, Calendar date) {
		this.contacts = contacts;
		this.date = date;
		this.id = nextAvailableId++; // assigns nextAvailableId to id, then increments nextAvailableId.
	}
	
	/**
	 * Returns the ID of the meeting.
	 *
	 * @return the ID of the meeting
	 **/
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the date of the meeting.
	 *
	 * @return the date of the meeting
	 **/
	public Calendar getDate() {
		return date;
	}
	
	/**
	 * Returns the details of people that attended the meeting.
	 *
	 * The list contains a minimum of one contact (if there were
	 * just two people: the user and the contact) and may contain an 
	 * arbitrary number of them.
	 *
	 * @return the details of people that attended the meeting
	 **/
	public Set<Contact> getContacts() {
		return contacts;
	}
 }