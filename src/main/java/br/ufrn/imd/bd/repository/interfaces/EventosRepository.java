package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.Evento;

public interface EventosRepository extends RepositoryGeneric {
	List<Evento> findAll() throws SQLException;

	Evento findById(Long id) throws SQLException;

	void save(Evento evento) throws SQLException;

	void update(Evento evento) throws SQLException;

	void delete(Long id) throws SQLException;
}
