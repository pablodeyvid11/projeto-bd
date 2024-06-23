package br.ufrn.imd.bd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Task;
import br.ufrn.imd.bd.repository.TaskRepositoryImpl;

@Service
public class TaskService {

	@Autowired
	private TaskRepositoryImpl taskRepository;

	public List<Task> getTasksByEventoId(Long eventoId) {
		return taskRepository.findAllByEventoId(eventoId);
	}
}
