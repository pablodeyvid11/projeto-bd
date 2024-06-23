package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.Noticia;

public interface NoticiaRepository extends RepositoryGeneric {
	List<Noticia> findAllByEventoId(Long id) throws SQLException;

	List<Noticia> findAll() throws SQLException;

	Noticia findById(Long id) throws SQLException;

	void save(Noticia noticia) throws SQLException;

	void update(Noticia noticia) throws SQLException;

	void delete(Long id) throws SQLException;
}
