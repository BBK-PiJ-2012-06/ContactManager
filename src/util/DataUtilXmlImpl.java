package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.IOException;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
	private Map<Integer, Contact> contactIdMap = new HashMap<Integer, Contact>(); // Used only when loading data (the contacts associated with a meeting are stored by ID only)
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

	@Override
	public void loadData(String filename) throws IOException {		
		//Clear contacts and meetings
		knownContacts.clear();
		pastMeetings.clear();
		futureMeetings.clear();
		
		try {
			//Get a fresh document
			clearDocument();
			
			//Load the file into a DOM document
			loadFile(filename);
			
			//Extract object information from the document
			extractContacts();
			////////////////////////////////////////////////
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Could not parse XML in file: " + filename);
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Could not read file: " + filename);
			e.printStackTrace();
		}
	}

	/**
	 * Loads data contained in the given XML file into a DOM document tree. 
	 * 
	 * @param filename the path to the XML file
	 * @throws ParserConfigurationException if a document builder cannot be created
	 * @throws IOException if the file cannot be opened
	 * @throws SAXException if the XML cannot be parsed
	 */
	private void loadFile(String filename) throws ParserConfigurationException, SAXException, IOException {
		//With help from http://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html
		
		//Initial checks:
		//-- filename cannot be null
		if(filename == null) {
			throw new NullPointerException("Path to file is null");
		}
		//-- file must exist
		File file = new File(filename);
		if(!file.exists()) {
			throw new IllegalArgumentException("Specified file " + filename + " does not exist");
		}
		
		//Obtain an instance of a factory that can give us a document builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		//Get an instance of a builder, and use it to parse the desired file
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(file);
	
	}

	@Override
	public void saveData(String filename) throws IOException {
		// Written with the help of the tutorial found at http://www.roseindia.net/xml/dom/
		// as well as http://docs.oracle.com/javaee/1.4/tutorial/doc/JAXPXSLT4.html for writing
		// to file.
		
		try {
			//Create blank DOM tree
			clearDocument();
		  
			//Create the root element
			Element root = doc.createElement("ContactManagerData");
			//add it to the xml tree
			doc.appendChild(root);
			
			//Append the Contacts, PastMeetings & FutureMeetings data under root
			appendContacts(root);
			appendPastMeetings(root);
			appendFutureMeetings(root);
			
			//Finally, write out the DOM tree to file
			writeFile(filename);
		} catch (ParserConfigurationException e) {
				e.printStackTrace();
		} catch (TransformerException e) {
				//Thrown from writeFile -- throw up an IOException for ContactManager to catch
				throw new IOException("Problem writing to file: " + filename, e);
		}	
	}
	
	/**
	 * Extracts the contact data from the DOM document and uses it to create Contact
	 * objects in memory.
	 */
	private void extractContacts() {
		//Clear the ID-Contact map (useful when we come to extract meeting data)
		contactIdMap.clear();
		
		//Get hold of the element containing contact data -- tag is "Contacts"
		Node contactsRoot = doc.getElementsByTagName("Contacts").item(0);
		
		//Contact data is stored in the children of contactsRoot, so for(each of these children...)
		for(Node contactNode = contactsRoot.getFirstChild(); contactNode != null; contactNode.getNextSibling()) {
			
			//Contact IDs are stored as the element's attribute
			int id = getIdAttribute(contactNode);
			
			//Retrieve the name and notes of this contact
			String name = getData(contactNode, "name");
			String notes = getData(contactNode, "notes");
			
			//Add a new contact to list of known contacts using this data,
			//and add to the ID map
			Contact contact = new ContactImpl(id, name, notes);
			knownContacts.add(contact);
			contactIdMap.put(id, contact);
		}
	}
	
	/**
	 * Extracts the data stored under the given node with the given tag; e.g., for a contact
	 * node <contact><name>Alice</name></contact>, the tag "name" would return the string "Alice".
	 * 
	 * @param node the node to extract data from
	 * @param tag the identifying tag of the desired data
	 * @return the data stored under the given node and tag
	 */
	private String getData(Node node, String tag) {
		Node dataNode = ((Element) node).getElementsByTagName(tag).item(0);
		return dataNode.getTextContent();
	}


	/**
	 * Returns the integer value of the attribute of the given node, representing the ID of 
	 * object; e.g. if the given node reads <contact id="3"></contact>, getAttribute would
	 * return the integer value of the string "3".
	 * 
	 * @param node the node to extract the attribute value from
	 * @return the value of the given node's attribute
	 */
	private int getIdAttribute(Node node) {
		return Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
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
		//Create container element for PastMeetings
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
	 * Appends data for all future meetings as "meeting" elements which are children of a "FutureMeetings"
	 * element, which is in turn appended as a child of the given root element.
	 *  
	 * @param root the element to append FutureMeetings to
	 */
	private void appendFutureMeetings(Element root) {
		//Create container element for FutureMeetings
		Element futureMeetingsRoot = doc.createElement("FutureMeetings");
		root.appendChild(futureMeetingsRoot);
		
		//Append all future meetings as meeting elements under futureMeetingsRoot
		for(FutureMeeting futureMeeting : futureMeetings) {
			Element meetingElem = createMeetingElement(futureMeeting);
			futureMeetingsRoot.appendChild(meetingElem);
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
	
	/**
	 * Writes the DOM document tree to the file specified at the given path.
	 * If the file already exists, it will be overwritten.
	 * 
	 * @param filename the path to the file to write to
	 * @throws TransformerException if something serious occurs when configuring the Transfomer or TransformerFactory
	 */
	private void writeFile(String filename) throws TransformerException {
		//Use a Transformer for output -- changes the DOM tree into XML string
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		
		//Configure the transformer to write pretty, human-readable XML;
		//thanks to http://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		//Transformers convert a Source into a Result (funnily)
		//--prepare Result (a StreamResult made from our file)
		Result result = new StreamResult(new File(filename));
		
		//--prepare Source (a DOMSource made from our doc)
		Source source = new DOMSource(doc);
		
		//Call transform to write the file
		transformer.transform(source, result);
	}
}