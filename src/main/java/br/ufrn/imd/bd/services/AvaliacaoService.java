package br.ufrn.imd.bd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;
import br.ufrn.imd.bd.repository.AvaliacaoRepositoryImpl;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepositoryImpl avaliacaoRepository;

	public List<FesteiroAvaliaEvento> getAvaliacoesByEventoId(Long eventoId) {
		return avaliacaoRepository.findAllByEventoId(eventoId);
	}

	public Double getAverageRatingByEventoId(Long eventoId) {
		return avaliacaoRepository.findAverageRatingByEventoId(eventoId);
	}
}