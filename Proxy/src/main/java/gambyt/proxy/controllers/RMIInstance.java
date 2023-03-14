package gambyt.proxy.controllers;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;

import gambyt.backend.RemoteFrontend;

public class RMIInstance {
	private static ArrayList<RemoteFrontend> INSTANCES = new ArrayList<RemoteFrontend>();
	private static ArrayList<String> IPS = new ArrayList<String>();
	private static int INDEX = 0;

	/*
		Initialize Proxy with servers at first startup
	 */
	public static void initRMI(String[] ips) {
		System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");
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

	public static void registerInstance(String ip) {
		String url = "rmi://" + ip + '/';
		try {
			RemoteFrontend newInstance = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
			INSTANCES.add(newInstance);
			System.out.println('\t' + ip + " (success): " + newInstance.getPathToData());
		} catch (Exception e) {
			System.out.println("IP: " + ip);
			System.out.println("Exception occurred initiating RMI: " + e.toString());
			e.printStackTrace();
		}
	}

	public static RemoteFrontend registerRequestedInstance(String ip) throws RemoteException {
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
		System.out.println('\t' + ip + " (success): " + inst.getPathToData());
	}

	public static RemoteFrontend getInstance() {
		if (INDEX >= INSTANCES.size())
			INDEX = 0;
		System.out.println(INDEX + ": " + IPS.get(INDEX));
		return INSTANCES.get(INDEX++);
	}

	public static Boolean instanceExists() {
		return INSTANCES.size() > 0;
	}
}
