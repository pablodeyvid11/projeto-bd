package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;

public interface VendedorRepository extends RepositoryGeneric {
	void save(Long id, String descricao) throws SQLException;

	void updateVendedor(Long id, String descricao) throws SQLException;

	void deleteVendedor(Long id) throws SQLException;
}