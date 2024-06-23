package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Estabelecimento;
import br.ufrn.imd.bd.model.EstabelecimentoHasOrganizadorHasEvento;
import br.ufrn.imd.bd.model.Evento;
import br.ufrn.imd.bd.repository.EstabelecimentoHasOrganizadorHasEventoRepositoryImpl;
import br.ufrn.imd.bd.repository.EventoRepositoryImpl;
import br.ufrn.imd.bd.repository.OrganizadorRepositoryImpl;
import br.ufrn.imd.bd.repository.interfaces.EstabelecimentoRepository;

@Service
public class EventoService {
	@Autowired
	private EventoRepositoryImpl eventoRepository;

	@Autowired
	private EstabelecimentoHasOrganizadorHasEventoRepositoryImpl estabelecimentoEventoOrganizadorRepository;

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private OrganizadorRepositoryImpl organizadorRepository;

	public List<Evento> getAllEventos() throws SQLException {
		return eventoRepository.findAll();
	}

	public Evento getEventoById(Long id) throws SQLException {
		return eventoRepository.findById(id);
	}

	public void createEvento(Evento evento, Long organizadorId, String estabelecimentoCnpj) throws SQLException {

		Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoCnpj);
		if (estabelecimento == null) {
			throw new IllegalStateException("Estabelecimento não encontrado.");
		}

		if (!estabelecimento.getOrganizadorCriadorId().equals(organizadorId)) {
			throw new IllegalStateException("Usuário não é dono do estabelecimento.");
		}

		if (organizadorRepository.existsById(organizadorId)) {
			evento.setId(eventoRepository.getNextId());
			eventoRepository.save(evento);
			EstabelecimentoHasOrganizadorHasEvento relacao = new EstabelecimentoHasOrganizadorHasEvento();
			relacao.setEstabelecimentoId(estabelecimentoCnpj);
			relacao.setOrganizadorId(organizadorId);
			relacao.setEventoId(evento.getId());
			estabelecimentoEventoOrganizadorRepository.save(relacao);
		} else {
			throw new IllegalStateException("Somente organizadores podem criar eventos.");
		}
	}

	public void addOrganizadorToEvento(Long eventoId, Long organizadorId, Long donoId) throws SQLException {
		Evento evento = eventoRepository.findById(eventoId);
		if (evento == null) {
			throw new IllegalStateException("Evento não encontrado.");
		}

		if (!estabelecimentoEventoOrganizadorRepository.isEventoCreatedByOrganizador(eventoId, organizadorId)) {
			throw new IllegalStateException("Usuário não é dono do estabelecimento.");
		}

		List<EstabelecimentoHasOrganizadorHasEvento> eoeList = estabelecimentoEventoOrganizadorRepository
				.findAllByEventoOrganizador(eventoId, organizadorId);

		for (EstabelecimentoHasOrganizadorHasEvento eoe : eoeList) {
			estabelecimentoEventoOrganizadorRepository.addOrganizadorToEvento(eoe.getEstabelecimentoId(), organizadorId,
					eventoId);
		}
	}

	public void updateEvento(Evento evento, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			eventoRepository.update(evento);
		} else {
			throw new IllegalStateException("Somente organizadores podem atualizar eventos.");
		}
	}

	public void deleteEvento(Long eventoId, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimentoEventoOrganizadorRepository.deleteByEventoId(eventoId);
			eventoRepository.delete(eventoId);
		} else {
			throw new IllegalStateException("Somente organizadores podem deletar eventos.");
		}
	}
}
