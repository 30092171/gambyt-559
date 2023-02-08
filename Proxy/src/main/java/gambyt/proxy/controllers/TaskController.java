package gambyt.proxy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    @GetMapping("")
    public String getAllTasks() {
//        Endpoint to return all tickets
        return "Hello World!!";
    }

    @PostMapping("")
    public void addNewTask() {
        // Endpoint to add new task
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable("id") long id) {
//    Endpoint to update a given ticket by its id
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id" long id) {
//        Endpoint to delete a task by id
    }

}
