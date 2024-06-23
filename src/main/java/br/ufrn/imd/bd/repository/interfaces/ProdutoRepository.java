package br.ufrn.imd.bd.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.bd.model.VendedorProdutos;

public interface ProdutoRepository extends RepositoryGeneric {
	List<VendedorProdutos> findAll() throws SQLException;

	List<VendedorProdutos> findByVendedorId(Long vendedorId) throws SQLException;

	VendedorProdutos findByNomeAndVendedorId(String nome, Long vendedorId) throws SQLException;

	void save(VendedorProdutos produto) throws SQLException;

	void update(VendedorProdutos produto) throws SQLException;

	void delete(String nome, Long vendedorId) throws SQLException;
}
