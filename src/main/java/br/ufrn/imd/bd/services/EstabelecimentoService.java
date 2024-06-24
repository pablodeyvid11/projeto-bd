package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.bd.model.Estabelecimento;
import br.ufrn.imd.bd.repository.interfaces.EstabelecimentoRepository;
import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	@Transactional(rollbackFor = SQLException.class)
	public List<Estabelecimento> getAllEstabelecimentos() throws SQLException {
		return estabelecimentoRepository.findAll();
	}

	@Transactional(rollbackFor = SQLException.class)
	public Estabelecimento getEstabelecimentoById(String cnpj) throws SQLException {
		return estabelecimentoRepository.findById(cnpj);
	}

	@Transactional(rollbackFor = SQLException.class)
	public void createEstabelecimento(Estabelecimento estabelecimento, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimento.setOrganizadorCriadorId(organizadorId);
			estabelecimentoRepository.save(estabelecimento);
		} else {
			throw new IllegalStateException("Somente organizadores podem criar estabelecimentos.");
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	public void updateEstabelecimento(Estabelecimento estabelecimento, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimentoRepository.update(estabelecimento);
		} else {
			throw new IllegalStateException("Somente organizadores podem atualizar estabelecimentos.");
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	public void deleteEstabelecimento(String cnpj, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimentoRepository.delete(cnpj);
		} else {
			throw new IllegalStateException("Somente organizadores podem deletar estabelecimentos.");
		}
	}
}