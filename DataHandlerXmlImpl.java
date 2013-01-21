import java.util.Set;
import java.util.List;
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

/*
import java.io.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
*/

/**
 * An implementation of the DataHandler interface.
 *
 * ***********************************************
 **/
public class DataHandlerXmlImpl implements DataHandler {
	/**
	 * Adds the given contacts to the stored data.
	 * 
	 * @param contacts the set of contacts to add
	 * @throws NullPointerException if contacts is null
	 **/
	public void addContacts(Set<Contact> contacts);
	
	/**
	 * Retrieves the contacts from the stored data, or null if there are none.
	 *
	 * @return the set of stored contacts
	 **/
	public Set<Contact> getContacts();
	
	/**
	 * Adds the given past meetings to the stored data.
	 * 
	 * @param meetings the list of past meetings to add
	 * @throws NullPointerException if meetings is null
	 **/
	public void addPastMeetings(List<PastMeeting> meetings);
	
	/**
	 * Retrieves the past meetings from the stored data, or null if there are none.
	 *
	 * @return the list of stored past meetings
	 **/
	public List<PastMeeting> getPastMeetings();
	
	/**
	 * Adds the given future meetings to the stored data.
	 * 
	 * @param meetings the list of past meetings to add
	 * @throws NullPointerException if meetings is null
	 **/
	public void addFutureMeetings(List<FutureMeeting> meetings);
	
	/**
	 * Retrieves the future meetings from the stored data, or null if there are none.
	 *
	 * @return the list of stored future meetings
	 **/
	public List<FutureMeeting> getFutureMeetings();
	
	/**
	 * Loads the data stored in the file at the given path.
	 *
	 * @param filename the path to the file to load
	 * @throws IOException if the file cannot be read
	 **/
	public void loadFile(String filename) {
		/* EXAMPLE -- from http://tutorials.jenkov.com/java-xml/dom.html *
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();  
		}
		
		try {
			Document document = builder.parse(new FileInputStream("data\\text.xml"));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		NodeList nodes = element.getChildNodes();

		for(int i=0; i<nodes.getLength(); i++){
			Node node = nodes.item(i);

			if(node instanceof Element){
				//a child element to process
				Element child = (Element) node;
				String attribute = child.getAttribute("width");
			}
		}
		 */
	}
	
	/**
	 * Writes the data stored in memory to the file at the given path.
	 *
	 * If the given file already exists, it will be overwritten.
	 *
	 * @param filename the path to the file to write to
	 * @throws IOException if the file cannot be written
	 **/
	public void writeFile(String filename) {
		/* EXAMPLE 1 -- see http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/ *
		try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// Root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("data");
		doc.appendChild(rootElement);
 
		// Contact elements
		Element contacts = doc.createElement("contacts");
		rootElement.appendChild(contacts);
 
		// set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);
 
		// shorten way
		// staff.setAttribute("id", "1");
 
		// firstname elements
		Element firstname = doc.createElement("firstname");
		firstname.appendChild(doc.createTextNode("yong"));
		staff.appendChild(firstname);
 
		// lastname elements
		Element lastname = doc.createElement("lastname");
		lastname.appendChild(doc.createTextNode("mook kim"));
		staff.appendChild(lastname);
 
		// nickname elements
		Element nickname = doc.createElement("nickname");
		nickname.appendChild(doc.createTextNode("mkyong"));
		staff.appendChild(nickname);
 
		// salary elements
		Element salary = doc.createElement("salary");
		salary.appendChild(doc.createTextNode("100000"));
		staff.appendChild(salary);
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:\\file.xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
	******************************************************************
	******************************************************************
		 */
		 
		/* EXAMPLE 2  -- see http://www.genedavis.com/library/xml/java_dom_xml_creation.jsp *
		try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Element root = doc.createElement("root");
            doc.appendChild(root);

            //create a comment and put it in the root element
            Comment comment = doc.createComment("Just a thought");
            root.appendChild(comment);

            //create child element, add an attribute, and add to root
            Element child = doc.createElement("child");
            child.setAttribute("name", "value");
            root.appendChild(child);

            //add a text element to the child
            Text text = doc.createTextNode("Filler, ... I could have had a foo!");
            child.appendChild(text);

            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            //print xml
            System.out.println("Here's the xml:\n\n" + xmlString);

        } catch (Exception e) {
            System.out.println(e);
        }
		**********************************************************************
		**********************************************************************
		 */
	}
}