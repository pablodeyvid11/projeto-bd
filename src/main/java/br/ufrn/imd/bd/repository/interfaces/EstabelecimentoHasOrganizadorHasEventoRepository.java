package br.ufrn.imd.bd.repository.interfaces;

import br.ufrn.imd.bd.model.EstabelecimentoHasOrganizadorHasEvento;

public interface EstabelecimentoHasOrganizadorHasEventoRepository extends RepositoryGeneric {
	void save(EstabelecimentoHasOrganizadorHasEvento relacao);

	void deleteByEventoId(Long eventoId);

	boolean isEventoCreatedByOrganizador(Long eventoId, Long organizadorId);
}
