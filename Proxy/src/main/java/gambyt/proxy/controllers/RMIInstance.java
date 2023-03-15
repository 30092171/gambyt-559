package gambyt.proxy.controllers;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import gambyt.backend.Database;
import gambyt.backend.RemoteFrontend;
import gambyt.backend.Ticket;

public class RMIInstance implements RemoteFrontend {
	private static RemoteFrontend[] BACKEND;
	private static RemoteFrontend INSTANCE;
	private static String[] IPS;
	private static int INDEX = 0;

	private static ArrayBlockingQueue<Request> Q;
	private static QueueThread QThread;

	static {
		Q = new ArrayBlockingQueue<Request>(100);
		QThread = new QueueThread(Q);
	}

	public static void initRMI(String[] ips) {
		System.out.println("Using the following IP addresses for backend:");
		if (ips.length == 0) { // use localhost
			BACKEND = new RemoteFrontend[1];
			IPS = new String[] { "localhost" };
			String url = "rmi://localhost/";
			try {
				BACKEND[0] = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
				System.out.println("\tlocalhost (success): " + BACKEND[0].getPathToData());
			} catch (Exception e) {
				System.out.println("IP: localhost");
				System.out.println("Exception occurred initiating RMI: " + e.toString());
				e.printStackTrace();
			}
		} else {
			BACKEND = new RemoteFrontend[ips.length];
			IPS = ips;
			for (int i = 0; i < ips.length; i++) {
				String url = "rmi://" + ips[i] + '/';
				try {
					BACKEND[i] = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
					System.out.println('\t' + ips[i] + " (success): " + BACKEND[i].getPathToData());
				} catch (Exception e) {
					System.out.println("IP: " + ips[i]);
					System.out.println("Exception occurred initiating RMI: " + e.toString());
					e.printStackTrace();
				}
			}
		}
		INSTANCE = new RMIInstance();
		QThread.start();
		System.out.println("RMI Init Complete");
	}

	public static RemoteFrontend getInstance() {
		// if (INDEX >= INSTANCES.length)
		// INDEX = 0;
		// System.out.println(INDEX + ": " + IPS[INDEX]);
		// return INSTANCES[INDEX++];
		return INSTANCE;
	}

	private static synchronized RemoteFrontend getBackendRR() {
		if (INDEX >= BACKEND.length)
			INDEX = 0;
		System.out.println(INDEX + ": " + IPS[INDEX]);
		return BACKEND[INDEX++];
	}

	private <R> R queueAndBlock(Supplier<R> sf) {
		Request<R> r = new Request<R>(sf);
		try {
			while (!Q.offer(r, 1000, TimeUnit.MILLISECONDS))
				;
			return r.get();
		} catch (InterruptedException | ExecutionException e) {
			System.err.println(e);
		}
		throw new RuntimeException("no idea how we got here");
	}

	// each call blocks until its released from Q
	@Override
	public String newTicket(Ticket ticket) throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().newTicket(ticket);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public void deleteTicket(String tID) throws RemoteException {
		queueAndBlock(() -> {
			try {
				getBackendRR().deleteTicket(tID);
				return null;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public ArrayList<String> getUserInbox(String userID) throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getUserInbox(userID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public void updateTicket(String tID, Ticket ticket) throws RemoteException {
		queueAndBlock(() -> {
			try {
				getBackendRR().updateTicket(tID, ticket);
				return null;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public void clearUserInbox(String userID) throws RemoteException {
		queueAndBlock(() -> {
			try {
				getBackendRR().clearUserInbox(userID);
				return null;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public HashMap<String, Ticket> getTicketByUser(String userID) throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getTicketByUser(userID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public HashMap<String, Ticket> getAllTickets() throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getAllTickets();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public HashMap<String, Ticket> getAllUnassigned() throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getAllUnassigned();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public Ticket getTicket(String tID) throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getTicket(tID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public String getPathToData() throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getPathToData();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public void setPathToData(String pathToData) throws RemoteException {
		queueAndBlock(() -> {
			try {
				getBackendRR().setPathToData(pathToData);
				return null;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}

	@Override
	public Database getDatabase() throws RemoteException {
		return queueAndBlock(() -> {
			try {
				return getBackendRR().getDatabase();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("error getting response from backend");
		});
	}
}
