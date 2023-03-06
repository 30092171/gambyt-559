package gambyt.proxy.controllers;

import java.rmi.Naming;

import gambyt.backend.RemoteFrontend;

public class RMIInstance {
	private static RemoteFrontend[] INSTANCES;
	private static String[] IPS;
	private static int INDEX = 0;

	public static void initRMI(String[] ips) {
		System.out.println("Using the following IP addresses for backend:");
		if (ips.length == 0) { // use localhost
			INSTANCES = new RemoteFrontend[1];
			IPS = new String[] { "localhost" };
			String url = "rmi://localhost/";
			try {
				INSTANCES[0] = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
				System.out.println("\tlocalhost (success): " + INSTANCES[0].getPathToData());
			} catch (Exception e) {
				System.out.println("IP: localhost");
				System.out.println("Exception occurred initiating RMI: " + e.toString());
				e.printStackTrace();
			}
		} else {
			INSTANCES = new RemoteFrontend[ips.length];
			IPS = ips;
			for (int i = 0; i < ips.length; i++) {
				String url = "rmi://" + ips[i] + '/';
				try {
					INSTANCES[i] = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
					System.out.println('\t' + ips[i] + " (success): " + INSTANCES[i].getPathToData());
				} catch (Exception e) {
					System.out.println("IP: " + ips[i]);
					System.out.println("Exception occurred initiating RMI: " + e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	public static RemoteFrontend getInstance() {
		if (INDEX >= INSTANCES.length)
			INDEX = 0;
		System.out.println(INDEX + ": " + IPS[INDEX]);
		return INSTANCES[INDEX++];
	}
}
