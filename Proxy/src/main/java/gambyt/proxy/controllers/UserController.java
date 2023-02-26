package gambyt.proxy.controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gambyt.backend.RemoteFrontend;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private RemoteFrontend server;

	public UserController() {
		server = RMIInstance.getInstance();
	}
	// not sure what to do with this yet
//    @GetMapping("")
//    public void checkLogin() {
////        Endpoint to login a user
//    }

	@GetMapping("/{id}/inbox")
	public ArrayList<String> getUserInbox(@PathVariable("id") String uID) throws RemoteException {
		ArrayList<String> inbox = server.getUserInbox(uID);
		return inbox;
	}

	@DeleteMapping("/{id}/inbox")
	public ResponseEntity<String> clearUserInbox(@PathVariable("id") String uID) throws RemoteException {
		server.clearUserInbox(uID);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	
}
