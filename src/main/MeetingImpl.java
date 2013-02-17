package main;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

/**
 * A class to represent meetings.
 * 
 * Meetings have unique IDs, scheduled date and a list of participating
 * contacts.
 **/
public class MeetingImpl implements Meeting {
	private final int id;
	private final Calendar date;
	private final Set<Contact> contacts;

	/**
	 * Constructs a MeetingImpl with the given set of contacts and date.
	 * 
	 * @param contacts
	 *            the set of contacts attending the meeting
	 * @param date
	 *            the date on which the meeting is to take place
	 **/
	public MeetingImpl(int id, Set<Contact> contacts, Calendar date) {
		this.contacts = new HashSet<Contact>(contacts);
		this.date = date;
		this.id = id;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}

	@Override
	// see http://www.artima.com/lejava/articles/equality.html
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof MeetingImpl) {
			MeetingImpl that = (MeetingImpl) other;
			result = (this.getID() == that.getID());
			result = result && (this.getContacts().equals(that.getContacts()));
			result = result && (this.getDate().equals(that.getDate()));
		}
		return result;
	}

	@Override
	// see link above
	public int hashCode() {
		int result = 13 + id;
		result = 31 * result + contacts.hashCode();
		result = 41 * result + date.hashCode();
		return result;
	}
}