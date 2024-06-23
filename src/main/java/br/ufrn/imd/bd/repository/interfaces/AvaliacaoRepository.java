package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;

public interface AvaliacaoRepository extends RepositoryGeneric {

	List<FesteiroAvaliaEvento> findAll() throws SQLException;

	List<FesteiroAvaliaEvento> findAllByEventoId(Long eventoId) throws SQLException;

	Double findAverageRatingByEventoId(Long eventoId) throws SQLException;

	FesteiroAvaliaEvento findByEventoIdAndFesteiroId(Long eventoId, Long festeiroId) throws SQLException;

	void save(FesteiroAvaliaEvento avaliacao) throws SQLException;

	void update(FesteiroAvaliaEvento avaliacao) throws SQLException;

	void delete(Long eventoId, Long festeiroId) throws SQLException;

}
