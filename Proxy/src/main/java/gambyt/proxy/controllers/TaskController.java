package gambyt.proxy.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
		server = RMIInstance.getInstance();
	}

	@GetMapping("")
	public JSONObject getAllTasks() throws RemoteException {
//        Endpoint to return all tickets
		HashMap<String, Ticket> tickets = server.getAllTickets();
		JSONObject wrapper = new JSONObject();
		JSONArray array = new JSONArray();
		wrapper.put("tickets", array);
		tickets.forEach((tID, tic) -> {
			JSONObject ticpair = new JSONObject();
			ticpair.put("id", tID);
			ticpair.put("ticket", tic);
			array.add(ticpair);
		});
		return wrapper;
	}

	@PostMapping(path = "/new",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addNewTask(@RequestBody Ticket nt) throws RemoteException {
		// Endpoint to add new task
		nt.PrintTicketInfo();
		String tID = server.newTicket(nt);
		server.getDatabase().addTicket(tID, nt);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateTask(@PathVariable("id") String tID, @RequestBody Ticket ut) throws RemoteException {
//    Endpoint to update a given ticket by its id
		ut.PrintTicketInfo();
		server.updateTicket(tID, ut);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable("id") String tID) throws RemoteException {
//        Endpoint to delete a task by id
		server.deleteTicket(tID);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public JSONObject getUserTasks(@PathVariable("id") long id) throws RemoteException {
//        Endpoint to get all of a specific users tasks
		String uID = Long.toString(id);
		HashMap<String, Ticket> tickets = server.getTicketByUser(uID);
		JSONObject wrapper = new JSONObject();
		JSONArray array = new JSONArray();
		wrapper.put("tickets", array);
		tickets.forEach((tID, tic) -> {
			JSONObject ticpair = new JSONObject();
			ticpair.put("id", tID);
			ticpair.put("ticket", tic);
			array.add(ticpair);
		});
		return wrapper;
	}
}