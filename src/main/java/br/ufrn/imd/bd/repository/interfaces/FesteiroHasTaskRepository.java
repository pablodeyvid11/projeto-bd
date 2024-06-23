package br.ufrn.imd.bd.repository.interfaces;

import br.ufrn.imd.bd.model.FesteiroHasTask;

public interface FesteiroHasTaskRepository extends RepositoryGeneric {
	void save(FesteiroHasTask festeiroHasTask);

	void update(FesteiroHasTask festeiroHasTask);

	boolean existsByFesteiroIdAndTaskId(Long festeiroId, Long taskId);

	FesteiroHasTask findByTaskIdAndFesteiroId(Long taskId, Long festeiroId);
}
