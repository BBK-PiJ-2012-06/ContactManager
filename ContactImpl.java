/**
 * A contact is a person we are making business with or may do in the future.
 * 
 * Contacts have an ID (unique), a name (probably unique, but maybe 
 * not), and notes that the user may want to save about them.
 **/
 public class ContactImpl implements Contact {
	private int id;
	private String name, notes;
	// nextAvailableId is used to assign IDs to new contacts, is static as it is only needed
	// by the class, not objects. 
	private static int nextAvailableId = 0; 
	
	public ContactImpl(String name, String notes) {
		this.name = name;
		this.notes = notes;
		this.id = nextAvailableId++; // assigns nextAvailableId to this.id, then increments nextAvailableId.
	}
	
	/**
	 * Returns the ID of the contact.
	 *
	 * @return the ID of the contact
	 **/
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of the contact.
	 * 
	 * @return the name of the contact
	 **/
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
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Add notes about the contact.
	 * 
	 * @param note the notes to be added
	 **/
	public void addNotes(String note) {
		this.notes = note;
	}
 }