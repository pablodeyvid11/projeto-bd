package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;

public interface FesteiroRepository extends RepositoryGeneric {
	void save(Long id) throws SQLException;

	void deleteFesteiro(Long id) throws SQLException;
}