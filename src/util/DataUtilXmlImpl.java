package util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.Contact;
import main.ContactImpl;
import main.FutureMeeting;
import main.Meeting;
import main.PastMeeting;

/**
 * An implementation of the DataUtil interface.
 * 
 * ***********************************************
 **/
public class DataUtilXmlImpl implements DataUtil {
	private Set<Contact> knownContacts = new HashSet<Contact>();
	private List<FutureMeeting> futureMeetings = new LinkedList<FutureMeeting>();
	private List<PastMeeting> pastMeetings = new LinkedList<PastMeeting>();
	private Document doc; // The DOM object that will model the data stored in the XML file.
	
	@Override
	public void addContacts(Set<Contact> contacts) {		
		if(contacts == null) {
			throw new NullPointerException("contacts is null");
		}
		
		knownContacts.addAll(contacts);
	}


	@Override
	public Set<Contact> getContacts() {		
		return knownContacts;
	}

	@Override
	public void addPastMeetings(List<PastMeeting> meetings) {		
		if(meetings == null) {
			throw new NullPointerException("meetings is null");
		}
		
		pastMeetings.addAll(meetings);
	}

	@Override
	public List<PastMeeting> getPastMeetings() {		
		return pastMeetings;
	}

	@Override
	public void addFutureMeetings(List<FutureMeeting> meetings) {		
		if(meetings == null) {
			throw new NullPointerException("meetings is null");
		}
		
		futureMeetings.addAll(meetings);
	}

	@Override
	public List<FutureMeeting> getFutureMeetings() {		
		return futureMeetings;
	}

	/**
	 * Loads the data stored in the file at the given path.
	 * 
	 * @param filename the path to the file to load
	 * @throws IllegalArgumentException if the file does not exist
	 * @throws IOException if the file cannot be read
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested 
	 * @throws SAXException if the XML file is malformed 
	 **/
	@Override
	public void loadFile(String filename) throws IOException, ParserConfigurationException, SAXException {
		System.out.println("loadFile not yet implemented");
		
		File file = new File(filename);
		if(!file.exists()) {
			throw new IllegalArgumentException("file " + filename + " does not exist");
		}
		// Using tutorial from http://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder(); 
	    doc = builder.parse(new File(filename));
	    // ...
	}

	/**
	 * Writes the data stored in memory to the file at the given path.
	 * 
	 * If the given file already exists, it will be overwritten.
	 * 
	 * @param filename the path to the file to write to
	 * @throws IOException if the file cannot be written
	 **/
	@Override
	public void writeFile(String filename) throws IOException {
		// Written with the help of the tutorial found at http://www.roseindia.net/xml/dom/
		
		try {
			//Create blank DOM tree
			clearDocument();
		  
			//create the root element
			Element root = doc.createElement("ContactManagerData");
			//add it to the xml tree
			doc.appendChild(root);
			
			//Append the Contacts, PastMeetings & FutureMeetings data under root
			appendContacts(root);
			appendPastMeetings(root);
			//**********CONTINUE FROM HERE!!!!***********//
			
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
		}	
	}

	/**
	 * Creates an empty DOM Document (pointed to by private field doc).
	 * 
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be configured
	 */
	private void clearDocument() throws ParserConfigurationException {
		//Create instance of DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//Get the DocumentBuilder
		DocumentBuilder builder = factory.newDocumentBuilder();	
		//Create blank DOM Document
		doc = builder.newDocument();
	}

	/**
	 * Appends data for all known contacts as "contact" elements which are children of a "Contacts"
	 * element, which is in turn appended as a child of the given root element.
	 *  
	 * @param root the element to append Contacts to
	 */
	private void appendContacts(Element root) {
		//Create top-level element to contain Contacts
		Element contactRoot = doc.createElement("Contacts");
		root.appendChild(contactRoot);
		
		//Append all contacts under contactRoot
		//Using http://www.w3schools.com/dtd/dtd_el_vs_attr.asp for guidance as to how to store
		//data as attribute or child element
		for(Contact contact : knownContacts) {
			Element contactElem = doc.createElement("contact");
			contactRoot.appendChild(contactElem);
			
			//add contact id as attribute
			setIdAttr(contactElem, contact.getID());
			
			//add contact data as children
			appendData(contactElem, "name", contact.getName());
			appendData(contactElem, "notes", contact.getNotes());
		}
	}

	/**
	 * Stores the value of the given ID as an attribute of the given element; 
	 * e.g. if the element reads <contact></contact> it will become <contact id="x"></contact>.
	 * 
	 * @param elem the element to assign the attribute to
	 * @param id the ID of the object represented by the element
	 */
	private void setIdAttr(Element elem, int id) {
		elem.setAttribute("id", Integer.toString(id));
	}

	/**
	 * Stores the given data as a text node within a child under the given element with the 
	 * given tag; e.g. if the data to add is a contact's name, and that name is "Alice", the result
	 * will be <contact><name>Alice</name></contact>.
	 * @param elem the element to append data to
	 * @param tag the name for the child element
	 * @param data the data to store in the child element
	 */
	private void appendData(Element elem, String tag, String data) {
		Element dataElem = doc.createElement(tag);
		elem.appendChild(dataElem);
		
		//Store data as TextNode under dataElem
		dataElem.appendChild(doc.createTextNode(data));
	}
	
	/**
	 * Appends data for all past meetings as "meeting" elements which are children of a "PastMeetings"
	 * element, which is in turn appended as a child of the given root element.
	 *  
	 * @param root the element to append PastMeetings to
	 */
	private void appendPastMeetings(Element root) {
		//Create top-level element to contain PastMeetings
		Element pastMeetingsRoot = doc.createElement("PastMeetings");
		root.appendChild(pastMeetingsRoot);
		
		//Append all past meetings as meeting elements under pastMeetingsRoot
		for(PastMeeting pastMeeting : pastMeetings) {
			Element meetingElem = createMeetingElement(pastMeeting);
			pastMeetingsRoot.appendChild(meetingElem);
			
			//createMeetingElement filled meetingElem with all pastMeeting's data 
			//-- except for the notes
			appendData(meetingElem, "notes", pastMeeting.getNotes());
		}
	}

	/**
	 * Creates and returns an element filled with data representing the given meeting.
	 * 
	 * @param meeting the meeting to be represented by the element
	 * @return the element representing the meeting
	 */
	private Element createMeetingElement(Meeting meeting) {
		//Create meeting element
		Element meetingElem = doc.createElement("meeting");
		
		//add meeting id as attribute
		setIdAttr(meetingElem, meeting.getID());
		
		//append meeting data
		//-- date
		appendData(meetingElem, "date", CalendarUtil.format(meeting.getDate()));
		
		//--contacts
		//create element to contain all contacts
		Element contactsElem = doc.createElement("contacts");
		meetingElem.appendChild(contactsElem);
		
		//just need to store the ID of each contact
		for(Contact contact : meeting.getContacts()) {
			Element contactElem = doc.createElement("contact");
			contactsElem.appendChild(contactElem);
			
			setIdAttr(contactElem, contact.getID());
		}
		
		return meetingElem;
	}
}