import java.util.Set;
import java.util.List;
import java.io.IOException;

/**
 * A class to handle the file interactions needed by ContactManager.
 **/
public interface DataHandler {
	/**
	 * Adds the given contacts to the stored data.
	 * 
	 * @param contacts the set of contacts to add
	 * @throws NullPointerException if contacts is null
	 **/
	void addContacts(Set<Contact> contacts);
	
	/**
	 * Retrieves the contacts from the stored data, or null if there are none.
	 *
	 * @return the set of stored contacts
	 **/
	Set<Contact> getContacts();
	
	/**
	 * Adds the given past meetings to the stored data.
	 * 
	 * @param meetings the list of past meetings to add
	 * @throws NullPointerException if meetings is null
	 **/
	void addPastMeetings(List<PastMeeting> meetings);
	
	/**
	 * Retrieves the past meetings from the stored data, or null if there are none.
	 *
	 * @return the list of stored past meetings
	 **/
	List<PastMeeting> getPastMeetings();
	
	/**
	 * Adds the given future meetings to the stored data.
	 * 
	 * @param meetings the list of past meetings to add
	 * @throws NullPointerException if meetings is null
	 **/
	void addFutureMeetings(List<FutureMeeting> meetings);
	
	/**
	 * Retrieves the future meetings from the stored data, or null if there are none.
	 *
	 * @return the list of stored future meetings
	 **/
	List<FutureMeeting> getFutureMeetings();
	
	/**
	 * Loads the data stored in the file at the given path.
	 *
	 * @param filename the path to the file to load
	 * @throws IOException if the file cannot be read
	 **/
	void loadFile(String filename);
	
	/**
	 * Writes the data stored in memory to the file at the given path.
	 *
	 * If the given file already exists, it will be overwritten.
	 *
	 * @param filename the path to the file to write to
	 * @throws IOException if the file cannot be written
	 **/
	void writeFile(String filename);
}