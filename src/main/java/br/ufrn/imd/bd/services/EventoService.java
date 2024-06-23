package br.ufrn.imd.bd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Evento;
import br.ufrn.imd.bd.repository.EventoRepositoryImpl;

@Service
public class EventoService {
	@Autowired
	private EventoRepositoryImpl eventoRepository;

	public List<Evento> getAllEventos() {
		return eventoRepository.findAll();
	}
}
