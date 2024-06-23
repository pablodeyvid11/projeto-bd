package br.ufrn.imd.bd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.EstabelecimentoHasOrganizadorHasEvento;
import br.ufrn.imd.bd.model.Evento;
import br.ufrn.imd.bd.repository.EstabelecimentoHasOrganizadorHasEventoRepositoryImpl;
import br.ufrn.imd.bd.repository.EventoRepositoryImpl;
import br.ufrn.imd.bd.repository.OrganizadorRepositoryImpl;

@Service
public class EventoService {
	@Autowired
	private EventoRepositoryImpl eventoRepository;

	@Autowired
	private EstabelecimentoHasOrganizadorHasEventoRepositoryImpl estabelecimentoEventoOrganizadorRepository;

	@Autowired
	private OrganizadorRepositoryImpl organizadorRepository;

	public List<Evento> getAllEventos() {
		return eventoRepository.findAll();
	}

	public Evento getEventoById(Long id) {
		return eventoRepository.findById(id);
	}
	
	public void createEvento(Evento evento, Long organizadorId, Long estabelecimentoCnpj) {
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

	public void updateEvento(Evento evento, Long organizadorId) {
		if (organizadorRepository.existsById(organizadorId)) {
			eventoRepository.update(evento);
		} else {
			throw new IllegalStateException("Somente organizadores podem atualizar eventos.");
		}
	}

	public void deleteEvento(Long eventoId, Long organizadorId) {
		if (organizadorRepository.existsById(organizadorId)) {
			estabelecimentoEventoOrganizadorRepository.deleteByEventoId(eventoId);
			eventoRepository.delete(eventoId);
		} else {
			throw new IllegalStateException("Somente organizadores podem deletar eventos.");
		}
	}
}
