package gambyt.proxy.controllers;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.lang.*;

import gambyt.backend.Database;
import gambyt.backend.RemoteFrontend;
import gambyt.backend.Server;
import gambyt.backend.Ticket;
import gambyt.proxy.ServerNotFoundException;

public class RMIInstance implements RemoteFrontend {
	private static ArrayList<RemoteFrontend> INSTANCES = new ArrayList<RemoteFrontend>();
	private static ArrayList<String> IPS = new ArrayList<String>();
	private static RemoteFrontend SELF;
	private static int INDEX = 0;

	private static ArrayBlockingQueue<Request> Q;
	private static QueueThread QThread;

	static {
		Q = new ArrayBlockingQueue<Request>(100);
		QThread = new QueueThread(Q);
	}

	/*
	 * Initialize Proxy with servers at first startup
	 */
	public static void initRMI(String[] ips) {
		System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");
		SELF = new RMIInstance();
		QThread.start();
		System.out.println("RMI Init Complete");
//		System.out.println("Using the following IP addresses for backend:");
//		if (ips.length == 0) { // use localhost
//			IPS.add("localhost");
//			registerInstance("localhost");
//		} else {
//			IPS = new ArrayList<String>(Arrays.asList(ips));
//			for (int i = 0; i < ips.length; i++) {
//				registerInstance(ips[i]);
//			}
//		}
	}

	public static void registerFirstInstance(String ip) {
		String url = "rmi://" + ip + '/';
		try {
			RemoteFrontend newInstance = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
			INSTANCES.add(newInstance);
			IPS.add(ip);
			System.out.println('\t' + ip + " (success): " + newInstance.getPathToData());
		} catch (Exception e) {
			System.out.println("IP: " + ip);
			System.out.println("Exception occurred initiating RMI: " + e.toString());
			e.printStackTrace();
		}
	}

	public static RemoteFrontend registerNewInstance(String ip) throws RemoteException {
		String url = "rmi://" + ip + '/';
		try {
			RemoteFrontend newInstance = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
			return newInstance;
		} catch (Exception e) {
			System.out.println("IP: " + ip);
			System.out.println("Exception occurred initiating RMI: " + e.toString());
			throw new RemoteException(e.toString());
		}
	}

	public static void addInstanceToRotation(RemoteFrontend inst, String ip) throws RemoteException {
		INSTANCES.add(inst);
		IPS.add(ip);
		try {
			System.out.println('\t' + ip + " (success): " + inst.getPathToData());
		} catch (ServerNotFoundException e) {
			e.printStackTrace();
			System.err.println("should not happen");
		}
	}

	public static RemoteFrontend getInstance() {
		return SELF;
	}

	private static synchronized RemoteFrontend getBackendRR() throws ServerNotFoundException {
		while (instanceExists()) {
			INDEX = INDEX % INSTANCES.size();
//			if (INDEX >= INSTANCES.size())
//				INDEX = 0;
			System.out.println(INDEX + ": " + IPS.get(INDEX));
			try {
				INSTANCES.get(INDEX).checkStatus();
				return INSTANCES.get(INDEX++);
			} catch (RemoteException e) {
				System.out.println("Remote Server " + IPS.get(INDEX) + " is not responding. Dropping it from list...");
				INSTANCES.remove(INDEX);
				IPS.remove(INDEX);
			}
		}
		throw new ServerNotFoundException("No more servers, please wait for another server to connect");
	}

	public static boolean instanceExists() {
		return INSTANCES.size() > 0;
	}

	public static boolean ipInRotation(String ip) {
		return IPS.contains(ip);
	}

	public static void removeServer(String ip) {
		int i = IPS.indexOf(ip);
		IPS.remove(i);
		INSTANCES.remove(i);
	}

	private <R> R queueAndBlock(RemoteSupplier<R> sf) throws RemoteException {
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
	public String newTicket(Ticket ticket) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.newTicket(ticket);
		});
	}

	@Override
	public void deleteTicket(String tID) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		queueAndBlock(() -> {
			be.deleteTicket(tID);
			return null;
		});
	}

	@Override
	public ArrayList<String> getUserInbox(String userID) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getUserInbox(userID);
		});
	}

	@Override
	public void updateTicket(String tID, Ticket ticket) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		queueAndBlock(() -> {
			be.updateTicket(tID, ticket);
			return null;
		});
	}

	@Override
	public void clearUserInbox(String userID) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		queueAndBlock(() -> {
			be.clearUserInbox(userID);
			return null;
		});
	}

	@Override
	public HashMap<String, Ticket> getTicketByUser(String userID) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getTicketByUser(userID);
		});
	}

	@Override
	public HashMap<String, Ticket> getAllTickets() throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getAllTickets();
		});
	}

	@Override
	public HashMap<String, Ticket> getAllUnassigned() throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getAllUnassigned();
		});
	}

	@Override
	public Ticket getTicket(String tID) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getTicket(tID);
		});
	}

	@Override
	public String getPathToData() throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getPathToData();
		});
	}

	@Override
	public void setPathToData(String pathToData) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		queueAndBlock(() -> {
			be.setPathToData(pathToData);
			return null;
		});
	}

	@Override
	public Database getDatabase() throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.getDatabase();
		});
	}

	@Override
	public void setDatabase(Database db) throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		queueAndBlock(() -> {
			be.setDatabase(db);
			return null;
		});
	}

	@Override
	public int checkStatus() throws RemoteException, ServerNotFoundException {
		RemoteFrontend be = getBackendRR();
		return queueAndBlock(() -> {
			return be.checkStatus();
		});
	}
}
