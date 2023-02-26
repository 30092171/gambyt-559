package gambyt.proxy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gambyt.backend.*;

import java.rmi.*;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	private RemoteFrontend server;

	public TaskController() {
		try {
			String url = "rmi://localhost/";
			server = (RemoteFrontend) Naming.lookup(url + "FrontendImpl");
			System.out.println("inited stuff");
			System.out.println(server.getPathToData());
		} catch (Exception e) {
			System.out.println("Exception occurred while running client: " + e.toString());
			e.printStackTrace();
		}
	}

	@GetMapping("")
	public String getAllTasks() throws RemoteException {
//        Endpoint to return all tickets
		HashMap<String, Ticket> tickets = server.getAllTickets();
		return tickets.toString();
	}

	@PostMapping(path = "",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addNewTask(@RequestBody Ticket nt) throws RemoteException {
		// Endpoint to add new task
		nt.PrintTicketInfo();
		String tID = server.newTicket(nt);
		server.getDatabase().addTicket(tID, nt);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateTask(@PathVariable("id") long id, @RequestBody Ticket ut) throws RemoteException {
//    Endpoint to update a given ticket by its id
		ut.PrintTicketInfo();
		String tID = Long.toString(id);
		server.updateTicket(tID, ut);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable("id") long id) throws RemoteException {
//        Endpoint to delete a task by id
		String tID = Long.toString(id);
		server.deleteTicket(tID);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public void getUserTasks(@PathVariable("id") long id) throws RemoteException {
//        Endpoint to get all of a specific users tasks
		String uID = Long.toString(id);
		HashMap<String, Ticket> tickets = server.getTicketByUser(uID);
		
	}
}