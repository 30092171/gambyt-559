import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RemoteFrontend extends Remote{

    public void newTicket(String tID, Ticket ticket) throws RemoteException;

    public void deleteTicket(String tID) throws RemoteException;

    public ArrayList<String> getUserInbox(String userID) throws RemoteException;

    public void updateTicket(String tID, Ticket ticket) throws RemoteException;

    public void clearUserInbox(String userID) throws RemoteException;

    public HashMap<String,Ticket> getTicketByUser(String userID) throws RemoteException;

    public HashMap<String,Ticket> getAllTickets() throws RemoteException;

    public HashMap<String, Ticket> getAllUnassigned() throws RemoteException;

    public String getPathToData() throws RemoteException;

    public void setPathToData(String pathToData) throws RemoteException;

    public Database getDatabase() throws RemoteException;

}
