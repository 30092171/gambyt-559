package gambyt.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.http.HttpRequest;

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
            System.err.println("Server Running Succesfully Bois");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://" + proxyIp + ":8080/api/v1/database/register"))
                    .headers("Content-Type", "text/plain;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString("Initiate Connection"))
                    .build();

            HttpResponse resp = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() != 200) {
                System.out.println(resp.toString());
            }

        }
        catch (Exception e) {
            System.out.println("Exception occurred while running server: " + e.toString());
            e.printStackTrace();
        }


    }

    /*
        From https://www.baeldung.com/java-get-ip-address
     */
//    private static String getIP() {
//        String urlString = "http://checkip.amazonaws.com/";
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(URL.openStream()))) {
//            return br.readLine();
//        }
//        catch (Exception e ) {
//            System.out.println("Error retrieving IP Address of Current Machine: " + e.getMessage());
//        }
//
//    }
}
