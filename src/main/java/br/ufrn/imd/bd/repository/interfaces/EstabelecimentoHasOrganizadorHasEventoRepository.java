package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.EstabelecimentoHasOrganizadorHasEvento;

public interface EstabelecimentoHasOrganizadorHasEventoRepository extends RepositoryGeneric {
	
	List<EstabelecimentoHasOrganizadorHasEvento> findAllByEventoOrganizador(Long eventoId, Long organizadorId);
	
	void save(EstabelecimentoHasOrganizadorHasEvento relacao);

	void deleteByEventoId(Long eventoId);

	boolean isEventoCreatedByOrganizador(Long eventoId, Long organizadorId);

	void addOrganizadorToEvento(String estabelecimentoCnpj, Long organizadorId, Long eventoId);
}
