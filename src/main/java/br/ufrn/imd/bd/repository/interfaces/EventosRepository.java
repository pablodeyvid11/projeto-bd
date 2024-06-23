package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Evento;

public interface EventosRepository extends RepositoryGeneric {
	public List<Evento> findAll();
	
}
