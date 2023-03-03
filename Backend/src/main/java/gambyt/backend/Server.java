package gambyt.backend;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String args[]) {
        try {
            // Change path to the actual database post-testing
            RemoteFrontend front = new FrontendImpl("src/JSON_Test.json");

            // Create stub for remote object. Port 0 indicates port decided dynamically.
            // RemoteFrontend stub = (RemoteFrontend) UnicastRemoteObject.exportObject(front, 0);

            // Create registry local to server on port 1099 (default port)
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("FrontendImpl", front);
            System.err.println("Server Running Succesfully Bois");

        }
        catch (Exception e) {
            System.out.println("Exception occurred while running server: " + e.toString());
            e.printStackTrace();
        }


    }
}
