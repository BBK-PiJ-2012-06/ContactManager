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
	public void writeFile(String filename) {
		// Written with the help of the tutorial found at http://www.roseindia.net/xml/dom/
		
		//Create instance of DocumentBuilderFactory
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//Get the DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
		
		  //Create blank DOM Document
		  doc = builder.newDocument();
		  
		  //create the root element
		  Element root = doc.createElement("root");
		  //all it to the xml tree
		  doc.appendChild(root);
		  

		  //create child element
		  Element childElement = doc.createElement("Child");
		  //Add the attribute to the child
		  childElement.setAttribute("attribute1","The value of Attribute 1");
		  root.appendChild(childElement);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}