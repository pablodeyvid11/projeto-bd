package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Evento;

public interface EventosRepository extends RepositoryGeneric {
    List<Evento> findAll();
    Evento findById(Long id);
    void save(Evento evento);
    void update(Evento evento);
    void delete(Long id);
}
