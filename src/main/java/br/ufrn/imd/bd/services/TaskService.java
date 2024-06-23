package br.ufrn.imd.bd.services;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.FesteiroHasTask;
import br.ufrn.imd.bd.model.Ingresso;
import br.ufrn.imd.bd.model.Task;
import br.ufrn.imd.bd.repository.interfaces.EstabelecimentoHasOrganizadorHasEventoRepository;
import br.ufrn.imd.bd.repository.interfaces.FesteiroHasTaskRepository;
import br.ufrn.imd.bd.repository.interfaces.IngressoRepository;
import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;
import br.ufrn.imd.bd.repository.interfaces.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private EstabelecimentoHasOrganizadorHasEventoRepository estabelecimentoHasOrganizadorHasEventoRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	@Autowired
	private FesteiroHasTaskRepository festeiroHasTaskRepository;

	@Autowired
	private IngressoRepository ingressoRepository;

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public List<Task> getTasksByEventoId(Long eventoId) {
		return taskRepository.findAllByEventoId(eventoId);
	}

	public Task getTaskById(Long id) {
		return taskRepository.findById(id);
	}

	public void createTask(Task task, Long organizadorId) {
		if (organizadorRepository.existsById(organizadorId)) {
			if (estabelecimentoHasOrganizadorHasEventoRepository.isEventoCreatedByOrganizador(task.getEventId(),
					organizadorId)) {
				task.setId(taskRepository.getNextId());
				taskRepository.save(task);
			} else {
				throw new IllegalStateException("Organizador só pode criar tarefas em eventos que ele criou.");
			}
		} else {
			throw new IllegalStateException("Somente organizadores podem criar tarefas.");
		}
	}

	public void updateTask(Task task, Long organizadorId) {
		if (organizadorRepository.existsById(organizadorId)) {
			if (estabelecimentoHasOrganizadorHasEventoRepository.isEventoCreatedByOrganizador(task.getEventId(),
					organizadorId)) {
				taskRepository.update(task);
			} else {
				throw new IllegalStateException("Organizador só pode atualizar tarefas em eventos que ele criou.");
			}
		} else {
			throw new IllegalStateException("Somente organizadores podem atualizar tarefas.");
		}
	}

	public void deleteTask(Long taskId, Long organizadorId) {
		Task task = taskRepository.findById(taskId);
		if (task != null && organizadorRepository.existsById(organizadorId)) {
			if (estabelecimentoHasOrganizadorHasEventoRepository.isEventoCreatedByOrganizador(task.getEventId(),
					organizadorId)) {
				taskRepository.delete(taskId);
			} else {
				throw new IllegalStateException("Organizador só pode deletar tarefas em eventos que ele criou.");
			}
		} else {
			throw new IllegalStateException("Somente organizadores podem deletar tarefas.");
		}
	}

	public void iniciarTask(Long taskId, Long festeiroId) {
		Task task = taskRepository.findById(taskId);
		if (task == null) {
			throw new IllegalStateException("Task não encontrada.");
		}

		Date finalDeadline = Date.from(task.getFinalDeadline().atZone(ZoneId.systemDefault()).toInstant());

		if (new Date().after(finalDeadline)) {
			throw new IllegalStateException("O prazo para iniciar esta tarefa já passou.");
		}

		Ingresso ingresso = ingressoRepository.findByEventoIdAndFesteiroId(task.getEventId(), festeiroId);
		if (ingresso == null) {
			throw new IllegalStateException("Festeiro não possui ingresso para este evento.");
		}

		if (festeiroHasTaskRepository.existsByFesteiroIdAndTaskId(festeiroId, taskId)) {
			throw new IllegalStateException("Festeiro já iniciou esta tarefa.");
		}

		FesteiroHasTask festeiroHasTask = new FesteiroHasTask();
		festeiroHasTask.setFesteiroId(festeiroId);
		festeiroHasTask.setTaskId(taskId);
		festeiroHasTask.setPointsWin(null);
		festeiroHasTask.setIsValidated(false);

		festeiroHasTaskRepository.save(festeiroHasTask);
	}
}
