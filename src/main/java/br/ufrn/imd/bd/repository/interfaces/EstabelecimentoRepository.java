package br.ufrn.imd.bd.repository.interfaces;

import java.util.List;

import br.ufrn.imd.bd.model.Estabelecimento;

public interface EstabelecimentoRepository extends RepositoryGeneric {
	List<Estabelecimento> findAll();

	Estabelecimento findById(String cnpj);

	void save(Estabelecimento estabelecimento);

	void update(Estabelecimento estabelecimento);

	void delete(String cnpj);
}