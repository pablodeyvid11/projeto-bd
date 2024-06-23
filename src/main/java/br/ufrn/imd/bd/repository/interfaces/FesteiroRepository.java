package br.ufrn.imd.bd.repository.interfaces;

public interface FesteiroRepository extends RepositoryGeneric {
	void save(Long id);
	void deleteFesteiro(Long id);
}