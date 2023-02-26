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
	private RemoteFrontend server;

	public InboxController() {
		server = RMIInstance.getInstance();
	}

	@GetMapping("/{id}")
	public ArrayList<String> getUserInbox(@PathVariable("id") String uID) throws RemoteException {
		ArrayList<String> inbox = server.getUserInbox(uID);
		return inbox;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> clearUserInbox(@PathVariable("id") String uID) throws RemoteException {
		server.clearUserInbox(uID);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

}
