package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;

import br.ufrn.imd.bd.model.Ingresso;

public interface IngressoRepository extends RepositoryGeneric {
	void save(Ingresso ingresso) throws SQLException;

	int countByEventoId(Long eventoId) throws SQLException;

	Ingresso findByEventoIdAndFesteiroId(Long eventoId, Long festeiroId) throws SQLException;
}
