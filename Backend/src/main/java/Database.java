import java.util.ArrayList;
import java.util.HashMap; // import the HashMap class

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;

/**
 * https://www.digitalocean.com/community/tutorials/json-simple-example
 *
 */
public class Database {
	
	private HashMap<String,Ticket> Tickets;
	private HashMap<String, ArrayList<String>> Inbox;
	
	
	/**
	 * Generic Constructor
	 * Populates database with empty values
	 */
	public Database() {
		this.Tickets = new HashMap<String,Ticket>();
		this.Inbox = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * Constructor which populates off of a JSON file.
	 * @param path : This is the filepath to the JSON
	 */
	public Database(String path) {
		this.Tickets = new HashMap<String,Ticket>();
		this.Inbox = new HashMap<String, ArrayList<String>>();
		
		try {
			readJSON(path);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads JSON and populates database
	 * NOTE: REPLACES DATABASE IN MEMORY
	 */
	public void readJSON(String path) throws ParseException, FileNotFoundException, IOException {
		
		JSONParser parser = new JSONParser(); //Creates JSON Parser
		Reader reader = new FileReader(path); //Creates file reader which points to path
		
		JSONObject JObj= (JSONObject) parser.parse(reader); //Attempts to parse JSON
		
		reader.close(); //Closes reader
		
		JSONObject JTickets = (JSONObject) JObj.get("tickets"); //Grabs nested object for tickets
		JSONObject JInbox = (JSONObject) JObj.get("inbox"); //Grabs nested object for inbox messages
		
		
		//First we will read all of the ticket information
		
		//Taken from https://www.tabnine.com/code/java/methods/org.json.simple.JSONObject/keySet
		//Iterates over every Ticket in the JSON and adds it to memory
		for (Iterator iterator = JTickets.keySet().iterator(); iterator.hasNext(); ) {
			
			//Get key and value
			String ticketID = (String) iterator.next();
			JSONObject JTicketObject = (JSONObject) JTickets.get(ticketID);
			
			//populate ticket object
			Ticket t = new Ticket();
			t.name = (String) JTicketObject.get("name");
			t.assignee = (long) JTicketObject.get("assignee");
			t.status = (long) JTicketObject.get("status");
			
			t.subscribers = new ArrayList<String>();
			
			JSONArray subs = (JSONArray) JTicketObject.get("subscribers");
			ArrayList<String> s = new ArrayList<String>();
			
			for(@SuppressWarnings("unchecked")
			Iterator<String> it = subs.iterator();it.hasNext(); ) {
				s.add((String) it.next());
			}
			
			t.subscribers = s;
			
			t.description = (String) JTicketObject.get("description");
			t.dateAssigned = (String) JTicketObject.get("date_assigned");
			t.priority = (long) JTicketObject.get("priority");
			
			
			this.Tickets.put(ticketID, t); //Adds ticket to memory
			
		}
		
		
		//Now that tickets are read, we will read the inbox
		for (Iterator iterator = JInbox.keySet().iterator(); iterator.hasNext(); ) {
			
			//Gets User ID and their messages
			String userID = (String) iterator.next();
			
			JSONArray JMessageList = (JSONArray) JInbox.get(userID);
			
			ArrayList<String> messages = new ArrayList<String>();
			
			//Commits messages to an ArrayList
			for(@SuppressWarnings("unchecked")
			Iterator<String> it = JMessageList.iterator();it.hasNext(); ) {
				messages.add((String) it.next());
			}
			
			this.Inbox.put(userID, messages); //Adds messages to memory		
			
		}	
		
	}
	
	
	/**
	 * Prints all the tickets within memory
	 */
	public void printTickets() {
		for (String key : this.Tickets.keySet()) {
		    System.out.println("Ticket ID: " + key);
		    this.Tickets.get(key).PrintTicketInfo();
		}
	}
	
	/**
	 * Prints all the messages within memory
	 */
	public void printMessages() {
		for(String user : this.Inbox.keySet()) {
			System.out.println("User: " + user);
			System.out.println("Messages: ");
			
			int i = 0;
			for(String message : this.Inbox.get(user)) {
				System.out.println("   [" + i + "]:  " + message);
				i++;
			}
			
			System.out.println("\n");
		}
	}
}
