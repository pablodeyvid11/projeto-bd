package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Task;
import br.ufrn.imd.bd.services.TaskService;
import br.ufrn.imd.bd.services.UserService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public Task getTaskById(@PathVariable Long id) {
		return taskService.getTaskById(id);
	}

	@GetMapping("/by-evento/{eventoId}")
	public List<Task> getTasksByEventoId(@PathVariable Long eventoId) {
		return taskService.getTasksByEventoId(eventoId);
	}

	@PostMapping
	public void createTask(@RequestBody Task task, @RequestParam Long eventoId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		task.setEventId(eventoId);
		taskService.createTask(task, organizadorId);
	}

	@PutMapping("/{id}")
	public void updateTask(@PathVariable Long id, @RequestBody Task task,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		task.setId(id);
		taskService.updateTask(task, organizadorId);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		taskService.deleteTask(id, organizadorId);
	}
}