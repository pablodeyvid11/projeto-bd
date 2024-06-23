package br.ufrn.imd.bd.repository.interfaces;

import br.ufrn.imd.bd.model.Ingresso;

public interface IngressoRepository extends RepositoryGeneric {
	void save(Ingresso ingresso);

	int countByEventoId(Long eventoId);

	Ingresso findByEventoIdAndFesteiroId(Long eventoId, Long festeiroId);
}
