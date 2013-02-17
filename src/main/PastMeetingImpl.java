package main;

import java.util.Calendar;
import java.util.Set;

/**
 * A meeting that was held in the past.
 * 
 * It includes your notes about what happened and what was agreed.
 **/
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	// inherited functionality from MeetingImpl,
	// the new code here is just for adding text notes.
	private String notes = "";

	/**
	 * Constructs a new PastMeetingImpl with the given set of contacts, date and
	 * notes about what occurred during the meeting.
	 * 
	 * @param contacts the set of contacts attending the meeting
	 * @param date the date on which the meeting is to take place
	 * @param notes the notes to be added
	 **/
	public PastMeetingImpl(int id, Set<Contact> contacts, Calendar date,
			String notes) {
		super(id, contacts, date);
		this.notes += notes;
	}

	/**
	 * Constructs a PastMeetingImpl from an existing MeetingImpl with additional
	 * notes about what occurred during the meeting.
	 * 
	 * @param meeting the meeting that has taken place
	 * @param notes the notes to be added
	 **/
	public PastMeetingImpl(Meeting meeting, String notes) {
		super(meeting.getID(), meeting.getContacts(), meeting.getDate());
		this.notes += notes;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	/**
	 * Add notes about the meeting.
	 * 
	 * @param note the notes to be added
	 **/
	public void addNotes(String note) {
		if (notes.isEmpty()) {
			notes += note;
		} else {
			this.notes += '\n' + note;
		}
	}
	
	@Override
	// see http://www.artima.com/lejava/articles/equality.html
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof PastMeetingImpl) {
			PastMeetingImpl that = (PastMeetingImpl) other;
			result = (this.getID() == that.getID());
			result = result && (this.getContacts().equals(that.getContacts()));
			result = result && (this.getDate().equals(that.getDate()));
			result = result && (this.getNotes().equals(that.getNotes()));
		}
		return result;
	}

	@Override
	// see link above
	public int hashCode() {
		int result = super.hashCode();
		result = 11 * result + notes.hashCode();
		return result;
	}
}