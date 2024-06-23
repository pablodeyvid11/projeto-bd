package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.VendedorProdutos;

public interface ProdutoRepository extends RepositoryGeneric {
	List<VendedorProdutos> findAll();

	List<VendedorProdutos> findByVendedorId(Long vendedorId);

	VendedorProdutos findByNomeAndVendedorId(String nome, Long vendedorId);

	void save(VendedorProdutos produto);

	void update(VendedorProdutos produto);

	void delete(String nome, Long vendedorId);
}
