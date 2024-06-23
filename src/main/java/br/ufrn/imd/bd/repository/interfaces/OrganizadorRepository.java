package br.ufrn.imd.bd.repository.interfaces;

public interface OrganizadorRepository extends RepositoryGeneric {
	void save(Long id, String cargo);
	void updateOrganizador(Long id, String cargo);
	void deleteOrganizador(Long id);
}