package gambyt;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Locale;

public class Server {

    public static void main(String args[]) {
        try {
            // Change path to the actual database post-testing
            RemoteFrontend front = new FrontendImpl("src/JSON_Test.json");

            // Create stub for remote object. Port 0 indicates port decided dynamically.
            // RemoteFrontend stub = (RemoteFrontend) UnicastRemoteObject.exportObject(front, 0);

            // Create registry local to server on port 1099 (default port)
            Registry registry = LocateRegistry.createRegistry(1099);

            //Naming.rebind("FrontendImpl", front);
            registry.bind("FrontendImpl", front);
        }
        catch (Exception e) {
            System.out.println("Exception occurred while running server: " + e.toString());
            e.printStackTrace();
        }


    }
}
