package gambyt.backend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String args[]) {
        try {
            if (args.length != 1) {
                System.out.println("Expected a command line argument for Proxy IP Address.");
                return;
            }
            // Get proxy IP from cmd-line args
            String proxyIp = args[0];

            // Change path to the actual database post-testing
            RemoteFrontend front = new FrontendImpl("src/JSON_Test.json");

            // Create stub for remote object. Port 0 indicates port decided dynamically.
            // RemoteFrontend stub = (RemoteFrontend) UnicastRemoteObject.exportObject(front, 0);

            // Create registry local to server on port 1099 (default port)
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("FrontendImpl", front);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://" + proxyIp + "/api/v1/database/register"))
                    .headers("Content-Type", "text/plain;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString("Initiate Connection"))
                    .build();

            HttpResponse resp = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() != 200) {
                System.out.println(resp.toString());
            }
            else {
                System.out.println("Server connected to " + proxyIp);
            }
        }
        catch (Exception e) {
            System.out.println("Exception occurred while running server: " + e.toString());
            e.printStackTrace();
        }


    }
}
