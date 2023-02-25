package gambyt.proxy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gambyt.*;

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
		String tID = server.newTicket("bruh");
		server.getDatabase().addTicket(tID, nt);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public void updateTask(@PathVariable("id") long id) {
//    Endpoint to update a given ticket by its id
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable("id") long id) {
//        Endpoint to delete a task by id
	}

	@GetMapping("/user/{id}")
	public void getUserTasks(@PathVariable("id") long id) {
//        Endpoint to get all of a specific users tasks
	}
}