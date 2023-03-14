package gambyt.proxy.controllers;

import gambyt.backend.Database;
import gambyt.backend.RemoteFrontend;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.rmi.RemoteException;

@RestController
@RequestMapping("/api/v1/database")
public class DatabaseController {
    @PostMapping("/register")
    public ResponseEntity<String> registerBackend(HttpServletRequest req) throws RemoteException {
        // If first instance, just add it to the rotation.
        String ip = req.getRemoteAddr();
        if (!RMIInstance.instanceExists()) {
            System.out.println("No instances currently connected. New server being added...");
            RMIInstance.registerInstance(ip);
        }
        // register backend and send the db copy to new backend
        else {
            Database db = RMIInstance.getInstance().getDatabase();
            RemoteFrontend newServer = RMIInstance.registerRequestedInstance(ip);
            newServer.setDatabase(db);
            RMIInstance.addInstanceToRotation(newServer, ip);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
