package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.Estabelecimento;

public interface EstabelecimentoRepository extends RepositoryGeneric {
	List<Estabelecimento> findAll() throws SQLException;

	Estabelecimento findById(String cnpj) throws SQLException;

	void save(Estabelecimento estabelecimento) throws SQLException;

	void update(Estabelecimento estabelecimento) throws SQLException;

	void delete(String cnpj) throws SQLException;
}