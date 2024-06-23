package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Task;

public interface TaskRepository extends RepositoryGeneric {
	List<Task> findAllByEventoId(Long id);
    List<Task> findAll();
    Task findById(Long id);
    void save(Task task);
    void update(Task task);
    void delete(Long id);
}
