package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.models.Task;
import app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> findTasks() {
       return new ArrayList<>();
    }

    @PostMapping("/task")
    public ResponseEntity createTask(@RequestBody Task task) {
        taskService.createTask(task);
        return new ResponseEntity(HttpStatus.OK);
    }
}