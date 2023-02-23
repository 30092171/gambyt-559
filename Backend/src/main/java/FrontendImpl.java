import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/*
	Remote object to be called from Proxy (client). Handles logic between proxy
	requests and database queries.
 */
public class FrontendImpl extends UnicastRemoteObject implements RemoteFrontend {
	private String pathToData;
	private Database database;

	public FrontendImpl(String path) throws RemoteException {
		setPathToData(path);
		this.database = new Database();
	}

	public String newTicket(String userId) {

		return "";
	}

	public void deleteTicket() {

	}

	public ArrayList<String> getUserInbox() {

		return new ArrayList<String>();
	}

	public void updateTicket() {

	}

	public void clearUserInbox() {

	}

	public ArrayList<Ticket> getTicketByUser() {

		return new ArrayList<Ticket>();
	}

	public HashMap<String,Ticket> getAllTickets() {

		return new HashMap<String, Ticket>();
	}

	public HashMap<String, Ticket> getAllUnassigned() {

		return new HashMap<String, Ticket>();
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
