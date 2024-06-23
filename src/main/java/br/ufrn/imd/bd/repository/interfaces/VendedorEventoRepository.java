package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.VendedorHasEvento;

public interface VendedorEventoRepository extends RepositoryGeneric {
	void solicitarVenda(VendedorHasEvento vendedorEvento) throws SQLException;

	void aprovarVenda(Long vendedorId, Long eventoId, Boolean isAprovado) throws SQLException;

	VendedorHasEvento findByVendedorIdAndEventoId(Long vendedorId, Long eventoId) throws SQLException;

	List<VendedorHasEvento> findAllByEventoIdAndIsAprovado(Long eventoId, Boolean isAprovado) throws SQLException;

	List<VendedorHasEvento> findAllByEventoIdAndIsAprovadoIsNull(Long eventoId) throws SQLException;
}
