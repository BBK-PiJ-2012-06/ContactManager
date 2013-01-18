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
	
	/**
	 * Constructs a MeetingImpl with the given set of contacts and date.
	 *
	 * @param contacts the set of contacts attending the meeting
	 * @param date the date on which the meeting is to take place
	 **/
	public MeetingImpl(int id, Set<Contact> contacts, Calendar date) {
		this.contacts = contacts;
		this.date = date;
		this.id = id; 
	}
	
	/**
	 * Returns the ID of the meeting.
	 *
	 * @return the ID of the meeting
	 **/
	@Override
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the date of the meeting.
	 *
	 * @return the date of the meeting
	 **/
	@Override
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
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}
 }