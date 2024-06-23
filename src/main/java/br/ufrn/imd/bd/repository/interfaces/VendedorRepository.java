package br.ufrn.imd.bd.repository.interfaces;

public interface VendedorRepository extends RepositoryGeneric {
	void save(Long id, String descricao);
	void updateVendedor(Long id, String descricao);
	void deleteVendedor(Long id);
}