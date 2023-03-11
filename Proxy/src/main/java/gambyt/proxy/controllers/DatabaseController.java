package gambyt.proxy.controllers;

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
    @PostMapping("")
    public ResponseEntity<String> registerBackend(/*@RequestBody String?? */) throws RemoteException {
        // register backend and send the db copy to new backend

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
