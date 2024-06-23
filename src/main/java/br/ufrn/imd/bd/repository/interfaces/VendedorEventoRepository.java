package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.VendedorHasEvento;

public interface VendedorEventoRepository extends RepositoryGeneric {
	void solicitarVenda(VendedorHasEvento vendedorEvento);

	void aprovarVenda(Long vendedorId, Long eventoId, Boolean isAprovado);

	VendedorHasEvento findByVendedorIdAndEventoId(Long vendedorId, Long eventoId);

	List<VendedorHasEvento> findAllByEventoIdAndIsAprovado(Long eventoId, Boolean isAprovado);

	List<VendedorHasEvento> findAllByEventoIdAndIsAprovadoIsNull(Long eventoId);
}
