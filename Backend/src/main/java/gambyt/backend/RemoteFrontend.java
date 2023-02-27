package gambyt.backend;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RemoteFrontend extends Remote {

    public String newTicket(Ticket ticket) throws RemoteException;

    public void deleteTicket(String tID) throws RemoteException;

    public ArrayList<String> getUserInbox(String userID) throws RemoteException;

    public void updateTicket(String tID, Ticket ticket) throws RemoteException;

    public void clearUserInbox(String userID) throws RemoteException;

    public HashMap<String,Ticket> getTicketByUser(String userID) throws RemoteException;

    public HashMap<String,Ticket> getAllTickets() throws RemoteException;

    public HashMap<String, Ticket> getAllUnassigned() throws RemoteException;
    
    public Ticket getTicket(String tID) throws RemoteException;

    public String getPathToData() throws RemoteException;

    public void setPathToData(String pathToData) throws RemoteException;

    public Database getDatabase() throws RemoteException;

}
