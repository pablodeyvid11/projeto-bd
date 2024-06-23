package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Noticia;

public interface NoticiaRepository extends RepositoryGeneric {
	List<Noticia> findAllByEventoId(Long id);

	List<Noticia> findAll();

	Noticia findById(Long id);

	void save(Noticia noticia);

	void update(Noticia noticia);

	void delete(Long id);
}
