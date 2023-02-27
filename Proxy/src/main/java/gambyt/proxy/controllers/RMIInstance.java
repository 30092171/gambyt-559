package gambyt.proxy.controllers;

import java.rmi.Naming;

import gambyt.backend.RemoteFrontend;

public class RMIInstance {
	private static RemoteFrontend INSTANCE;
	
	public static RemoteFrontend getInstance() {
		if(INSTANCE != null) return INSTANCE;
		try {
			String url = "rmi://localhost/";
			INSTANCE = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
			System.out.println("RMI Lookup Success");
			System.out.println("Remote FS location: " + INSTANCE.getPathToData());
		} catch (Exception e) {
			System.out.println("Exception occurred while running client: " + e.toString());
			e.printStackTrace();
		}
		return INSTANCE;
	}
}
