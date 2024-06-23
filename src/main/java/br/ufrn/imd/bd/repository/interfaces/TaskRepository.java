package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Task;

public interface TaskRepository extends RepositoryGeneric {
	List<Task> findAllByEventoId(Long id);
}
