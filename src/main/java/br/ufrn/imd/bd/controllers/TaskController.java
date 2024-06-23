package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Task;
import br.ufrn.imd.bd.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/by-evento/{eventoId}")
	public List<Task> getTasksByEventoId(@PathVariable Long eventoId) {
		return taskService.getTasksByEventoId(eventoId);
	}
}