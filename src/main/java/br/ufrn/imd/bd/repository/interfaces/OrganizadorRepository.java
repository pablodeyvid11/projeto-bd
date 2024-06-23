package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;

public interface OrganizadorRepository extends RepositoryGeneric {
	void save(Long id, String cargo) throws SQLException;

	void updateOrganizador(Long id, String cargo) throws SQLException;

	void deleteOrganizador(Long id) throws SQLException;
}