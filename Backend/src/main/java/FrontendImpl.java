import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/*
	Remote object to be called from Proxy (client). Handles logic between proxy
	requests and database queries.
 */
public class FrontendImpl extends UnicastRemoteObject implements RemoteFrontend {
	private String pathToData;
	private Database database;

	public FrontendImpl(String path) throws RemoteException {
		setPathToData(path);
		this.database = new Database(path);
	}

	// TO-DO
	public String newTicket(String userId) {
		String tID = generateTicketID();

		try {
			database.saveJSON(pathToData);
		}
		catch (IOException e) {
			System.out.println("Failed to add ticket due to the following exception:" + e.getMessage());
		}
		return "";
	}

	public void deleteTicket(String tID) {
		String tName = database.getTicket(tID).name;
		database.removeTicket(tID);
		database.notifySubscribers(tID, "Ticket " + tID + ": " + tName + " has been deleted.");
		try {
			database.saveJSON(pathToData);
		}
		catch (IOException e) {
			System.out.println("Failed to delete ticket due to the following exception:" + e.getMessage());
		}
	}

	public ArrayList<String> getUserInbox(String userID) {
		return database.getInboxes().get(userID);
	}

	// TO-DO
	public void updateTicket(String tID) {
		Ticket ticket = database.getTicket(tID);

		try {
			database.saveJSON(pathToData);
		}
		catch (IOException e) {
			System.out.println("Failed to update ticket due to the following exception:" + e.getMessage());
		}
	}

	public void clearUserInbox(String userID) {
		if (database.doesUserHaveMessages(userID)) {
			database.clearMessages(userID);
			try {
				database.saveJSON(pathToData);
			}
			catch (IOException e) {
				System.out.println("Failed to clear user inbox due to the following exception:" + e.getMessage());
			}
		}
	}

	public HashMap<String, Ticket> getTicketByUser(String userID) {
		ArrayList<String> tIDs = database.getTicketsByUser(userID);
		ArrayList<Ticket> tickets = database.getTickets(tIDs);
		HashMap<String, Ticket> ticketMap = new HashMap<String, Ticket>();
		for (int i = 0; i < tIDs.size(); i++) {
			ticketMap.put(tIDs.get(i), tickets.get(i));
		}

		return ticketMap;
	}

	public HashMap<String,Ticket> getAllTickets() {
		return database.getTickets();
	}

	public HashMap<String, Ticket> getAllUnassigned() {
		ArrayList<String> tIDs = database.getTicketsUnassigned();
		ArrayList<Ticket> tickets = database.getTickets(tIDs);
		HashMap<String, Ticket> ticketMap = new HashMap<String, Ticket>();
		for (int i = 0; i < tIDs.size(); i++) {
			ticketMap.put(tIDs.get(i), tickets.get(i));
		}
		return ticketMap;
	}

	private String generateTicketID() {
		// From: https://blog.devgenius.io/7-famous-approaches-to-generate-distributed-id-with-comparison-table-af89afe4601f
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public String getPathToData() {
		return pathToData;
	}

	public void setPathToData(String pathToData) {
		this.pathToData = pathToData;
	}

	public Database getDatabase() {
		return database;
	}
	
}
