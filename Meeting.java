import java.util.Calendar;
import java.util.Set;

/**
 * A class to represent meetings.
 *
 * Meetings have unique IDs, scheduled date and a list of participating contacts.
 **/
 public interface Meeting {
	/**
	 * Returns the ID of the meeting.
	 *
	 * @return the ID of the meeting
	 **/
	int getID();
	
	/**
	 * Returns the date of the meeting.
	 *
	 * @return the date of the meeting
	 **/
	Calendar getDate();
	
	/**
	 * Returns the details of people that attended the meeting.
	 *
	 * The list contains a minimum of one contact (if there were
	 * just two people: the user and the contact) and may contain an 
	 * arbitrary number of them.
	 *
	 * @return the details of people that attended the meeting
	 **/
	Set<Contact> getContacts();
 }