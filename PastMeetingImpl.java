import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

/**
 * A meeting that was held in the past.
 *
 * It includes your notes about what happened and what was agreed.
 **/
 public class PastMeetingImpl implements PastMeeting {
	private int id;
	private Calendar date = null;
	private Set<Contact> contacts = new HashSet<Contact>();
	// nextAvailableId is used to assign IDs to new meetings, is static as it is 
	// only needed by the class, not objects.
	private static int nextAvailableId = 0;
	// the above is lifted directly from MeetingImpl (as PastMeeting extends Meeting, the
	// implementation of PastMeeting must implement the methods inherited from Meeting),
	// the new code here is just for adding text notes.
	private String notes = "";
	
	/**
	 * Constructs a new PastMeetingImpl with the given set of contacts, date and notes
	 * about what occurred during the meeting.
	 *
	 * @param contacts the set of contacts attending the meeting
	 * @param date the date on which the meeting is to take place
	 * @param notes the notes to be added 
	 **/
	public PastMeetingImpl(Set<Contact> contacts, Calendar date, String notes) {
		this.contacts = contacts;
		this.date = date;
		this.notes = notes;
		this.id = nextAvailableId++; // assigns nextAvailableId to id, then increments nextAvailableId.
	}
	
	/**
	 * Constructs a PastMeetingImpl from an existing MeetingImpl with additional notes
	 * about what occurred during the meeting.
	 *
	 * @param meeting the meeting that has taken place
	 * @param notes the notes to be added 
	 **/
	public PastMeetingImpl(MeetingImpl meeting, String notes) {
		this.contacts = meeting.getContacts();
		this.date = meeting.getDate();
		this.notes = notes;
		this.id = meeting.getId();
	}
	
	/**
	 * Returns the notes from the meeting.
	 *
	 * If there are no notes, the empty string is returned.
	 *
	 * @return the notes from the meeting
	 **/
	@Override
	public String getNotes() {
		return notes;
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