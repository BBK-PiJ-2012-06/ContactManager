/**
 * A contact is a person we are making business with or may do in the future.
 * 
 * Contacts have an ID (unique), a name (probably unique, but maybe 
 * not), and notes that the user may want to save about them.
 **/
 public class ContactImpl implements Contact {
	private final int id;
	private final String name; 
	private String notes = ""; 
	
	/**
	 * Constructs a ContactImpl with the given name and notes about the contact.
	 *
	 * @param name the name of the contact
	 * @param notes notes to be added about this contact
	 **/
	public ContactImpl(int id, String name, String notes) {
		this.name = name;
		this.notes += notes;
		this.id = id;
	}
	
	/**
	 * Returns the ID of the contact.
	 *
	 * @return the ID of the contact
	 **/
	@Override
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of the contact.
	 * 
	 * @return the name of the contact
	 **/
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Returns our notes about the contact, if any.
	 *
	 * If we have not written anything about the contact, the empty
	 * string is returned.
	 * 
	 * @return a string with notes about the contact, maybe empty
	 **/
	@Override
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Add notes about the contact.
	 * 
	 * @param note the notes to be added
	 **/
	@Override
	public void addNotes(String note) {
		this.notes += note; // add to notes, not overwrite
	}
	
	@Override // see http://www.artima.com/lejava/articles/equality.html
	public boolean equals(Object other) {
		boolean result = false;
		if(other instanceof ContactImpl) {
			ContactImpl that = (ContactImpl) other;
			result = (this.getID() == that.getID() && this.getName() == that.getName() && this.getNotes() == that.getNotes());
		}
		return result;
	}
	
	@Override // see link above
	public int hashCode() {
		int result = 13 + id;
		result = 31 * result + name.hashCode();
		result = 41 * result + notes.hashCode();
		return result;
	}
 }