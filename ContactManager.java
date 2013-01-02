import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * A class to manage your contacts and meetings.
 **/
public interface ContactManager {
	/**
	 * Add a new meeting to be held in the future.
	 * 
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past, 
	 *		or if any contact is unknown / non-existent
	 **/
	int addFutureMeeting(Set<Contact> contacts, Calendar date);
	
	/**
	 * Returns the PAST meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	 **/
}