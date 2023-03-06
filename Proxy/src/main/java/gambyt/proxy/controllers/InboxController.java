package gambyt.proxy.controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gambyt.backend.RemoteFrontend;

@RestController
@RequestMapping("/api/v1/inbox")
public class InboxController {

	public InboxController() {
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public ArrayList<String> getUserInbox(@PathVariable("id") String uID) throws RemoteException {
		ArrayList<String> inbox = RMIInstance.getInstance().getUserInbox(uID);
		return inbox;
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> clearUserInbox(@PathVariable("id") String uID) throws RemoteException {
		RMIInstance.getInstance().clearUserInbox(uID);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

}
