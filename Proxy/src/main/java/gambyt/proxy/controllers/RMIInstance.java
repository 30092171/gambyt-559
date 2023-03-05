package gambyt.proxy.controllers;

import java.rmi.Naming;

import gambyt.backend.RemoteFrontend;

public class RMIInstance {
	private static RemoteFrontend[] INSTANCES;
	private static int INDEX = 0;

	public static void initRMI(String[] ips) {
		System.out.println("Using the following IP addresses for backend:");
		if (ips.length == 0) { // use localhost
			System.out.println("\tlocalhost");
			INSTANCES = new RemoteFrontend[1];
			String url = "rmi://localhost/";
			try {
				INSTANCES[0] = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
				System.out.println("RMI Lookup Success");
				System.out.println("Remote FS location: " + INSTANCES[0].getPathToData());
			} catch (Exception e) {
				System.out.println("IP: localhost");
				System.out.println("Exception occurred initiating RMI: " + e.toString());
				e.printStackTrace();
			}
		} else {
			INSTANCES = new RemoteFrontend[ips.length];
			for (int i = 0; i < ips.length; i++) {
				System.out.println('\t' + ips[i]);
				String url = "rmi://" + ips[i] + '/';
				try {
					INSTANCES[i] = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
					System.out.println("RMI Lookup Success");
					System.out.println("Remote FS location: " + INSTANCES[i].getPathToData());
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
		return INSTANCES[INDEX++];
	}
}
