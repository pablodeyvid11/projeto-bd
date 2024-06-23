package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.EstabelecimentoHasOrganizadorHasEvento;

public interface EstabelecimentoHasOrganizadorHasEventoRepository extends RepositoryGeneric {

	List<EstabelecimentoHasOrganizadorHasEvento> findAllByEventoOrganizador(Long eventoId, Long organizadorId)
			throws SQLException;

	void save(EstabelecimentoHasOrganizadorHasEvento relacao) throws SQLException;

	void deleteByEventoId(Long eventoId) throws SQLException;

	boolean isEventoCreatedByOrganizador(Long eventoId, Long organizadorId) throws SQLException;

	void addOrganizadorToEvento(String estabelecimentoCnpj, Long organizadorId, Long eventoId) throws SQLException;
}
