package main;

/**
 * A contact is a person we are making business with or may do in the future.
 * 
 * Contacts have an ID (unique), a name (probably unique, but maybe not), and
 * notes that the user may want to save about them.
 **/
public class ContactImpl implements Contact {
	private final int id;
	private final String name;
	private String notes = "";

	/**
	 * Constructs a ContactImpl with the given ID, name and notes about the
	 * contact.
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
	 * Constructs a ContactImpl with the given ID and name (for use when notes
	 * will be added at a later date).
	 * 
	 * @param name the name of the contact
	 **/
	public ContactImpl(int id, String name) {
		this.name = name;
		this.id = id;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
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
		if (other instanceof ContactImpl) {
			ContactImpl that = (ContactImpl) other;
			result = (this.getID() == that.getID());
			result = result && (this.getName().equals(that.getName()));
			result = result && (this.getNotes().equals(that.getNotes()));
		}
		return result;
	}

	@Override
	// see link above
	public int hashCode() {
		int result = 13 + id;
		result = 31 * result + name.hashCode();
		result = 41 * result + notes.hashCode();
		return result;
	}
}