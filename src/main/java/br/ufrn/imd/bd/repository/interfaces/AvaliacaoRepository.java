package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;

public interface AvaliacaoRepository extends RepositoryGeneric {

	List<FesteiroAvaliaEvento> findAll();

	List<FesteiroAvaliaEvento> findAllByEventoId(Long eventoId);

	Double findAverageRatingByEventoId(Long eventoId);

	FesteiroAvaliaEvento findByEventoIdAndFesteiroId(Long eventoId, Long festeiroId);

	void save(FesteiroAvaliaEvento avaliacao);

	void update(FesteiroAvaliaEvento avaliacao);

	void delete(Long eventoId, Long festeiroId);

}
