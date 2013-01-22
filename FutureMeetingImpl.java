import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

/**
 * A meeting to be held in the future.
 *
 **/
 public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	// No new functionality here, this is just a naming class
	// (i.e. only necessary for type checking and/or downcasting)

	/**
	 * Constructs a FutureMeetingImpl with the given set of contacts and date.
	 *
	 * @param contacts the set of contacts attending the meeting
	 * @param date the date on which the meeting is to take place
	 **/
	public FutureMeetingImpl(int id, Set<Contact> contacts, Calendar date) {
		super(id, contacts, date);
	}
	
	@Override // see http://www.artima.com/lejava/articles/equality.html
	public boolean equals(Object other) {
		boolean result = false;
		if(other != null && other instanceof FutureMeetingImpl) {
			FutureMeetingImpl that = (FutureMeetingImpl) other;
			result = (this.getID() == that.getID());
			result = result && (this.getContacts().equals(that.getContacts()));
			result = result && (this.getDate().equals(that.getDate())); 
			result = result && (this.getNotes().equals(that.getNotes()));
		}
		return result;
	}
	
	@Override // see link above
	public int hashCode() {
		return super.hashCode();
	}
 }