package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.Task;

public interface TaskRepository extends RepositoryGeneric {
	List<Task> findAllByEventoId(Long id) throws SQLException;

	List<Task> findAll() throws SQLException;

	Task findById(Long id) throws SQLException;

	void save(Task task) throws SQLException;

	void update(Task task) throws SQLException;

	void delete(Long id) throws SQLException;
}
