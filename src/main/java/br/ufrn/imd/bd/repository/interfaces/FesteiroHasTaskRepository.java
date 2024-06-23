package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;

import br.ufrn.imd.bd.model.FesteiroHasTask;

public interface FesteiroHasTaskRepository extends RepositoryGeneric {
	void save(FesteiroHasTask festeiroHasTask) throws SQLException;

	void update(FesteiroHasTask festeiroHasTask) throws SQLException;

	boolean existsByFesteiroIdAndTaskId(Long festeiroId, Long taskId) throws SQLException;

	FesteiroHasTask findByTaskIdAndFesteiroId(Long taskId, Long festeiroId) throws SQLException;
}
