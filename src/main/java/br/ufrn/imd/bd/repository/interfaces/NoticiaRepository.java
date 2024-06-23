package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Noticia;

public interface NoticiaRepository extends RepositoryGeneric {
	List<Noticia> findAllByEventoId(Long id);
}
