import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String args[]) {
        try {
            // Change path to the actual database post-testing
            FrontendImpl front = new FrontendImpl("src/JSON_Test.json");
            UnicastRemoteObject.exportObject(front, 0);

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
