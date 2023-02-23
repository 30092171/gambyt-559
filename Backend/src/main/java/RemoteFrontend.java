import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RemoteFrontend extends Remote{

    public void newTicket() throws RemoteException;

    public void deleteTicket() throws RemoteException;

    public ArrayList<String> getUserInbox() throws RemoteException;

    public void updateTicket() throws RemoteException;

    public void clearUserInbox() throws RemoteException;

    public ArrayList<Ticket> getTicketByUser() throws RemoteException;

    public HashMap<String,Ticket> getAllTickets() throws RemoteException;

    public HashMap<String, Ticket> getAllUnassigned() throws RemoteException;

    public String getPathToData() throws RemoteException;

    public void setPathToData(String pathToData) throws RemoteException;

    public Database getDatabase() throws RemoteException;

}
