package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Estabelecimento;
import br.ufrn.imd.bd.repository.EstabelecimentoRepositoryImpl;
import br.ufrn.imd.bd.repository.OrganizadorRepositoryImpl;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepositoryImpl estabelecimentoRepository;

	@Autowired
	private OrganizadorRepositoryImpl organizadorRepository;

	public List<Estabelecimento> getAllEstabelecimentos() throws SQLException {
		return estabelecimentoRepository.findAll();
	}

	public Estabelecimento getEstabelecimentoById(String cnpj) throws SQLException {
		return estabelecimentoRepository.findById(cnpj);
	}

	public void createEstabelecimento(Estabelecimento estabelecimento, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimento.setOrganizadorCriadorId(organizadorId);
			estabelecimentoRepository.save(estabelecimento);
		} else {
			throw new IllegalStateException("Somente organizadores podem criar estabelecimentos.");
		}
	}

	public void updateEstabelecimento(Estabelecimento estabelecimento, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimentoRepository.update(estabelecimento);
		} else {
			throw new IllegalStateException("Somente organizadores podem atualizar estabelecimentos.");
		}
	}

	public void deleteEstabelecimento(String cnpj, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimentoRepository.delete(cnpj);
		} else {
			throw new IllegalStateException("Somente organizadores podem deletar estabelecimentos.");
		}
	}
}