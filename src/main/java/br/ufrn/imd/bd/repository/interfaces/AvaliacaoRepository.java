package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;

public interface AvaliacaoRepository extends RepositoryGeneric {
	List<FesteiroAvaliaEvento> findAllByEventoId(Long eventoId);

	Double findAverageRatingByEventoId(Long eventoId);

}
