package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Evento;
import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;
import br.ufrn.imd.bd.model.Ingresso;
import br.ufrn.imd.bd.repository.interfaces.AvaliacaoRepository;
import br.ufrn.imd.bd.repository.interfaces.EventosRepository;
import br.ufrn.imd.bd.repository.interfaces.IngressoRepository;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Autowired
	private EventosRepository eventoRepository;

	@Autowired
	private IngressoRepository ingressoRepository;

	public List<FesteiroAvaliaEvento> getAllAvaliacoes() throws SQLException {
		return avaliacaoRepository.findAll();
	}

	public List<FesteiroAvaliaEvento> getAvaliacoesByEventoId(Long eventoId) throws SQLException {
		return avaliacaoRepository.findAllByEventoId(eventoId);
	}

	public Double getAverageRatingByEventoId(Long eventoId) throws SQLException {
		return avaliacaoRepository.findAverageRatingByEventoId(eventoId);
	}

	public FesteiroAvaliaEvento getAvaliacaoByEventoIdAndFesteiroId(Long eventoId, Long festeiroId)
			throws SQLException {
		return avaliacaoRepository.findByEventoIdAndFesteiroId(eventoId, festeiroId);
	}

	public void criarAvaliacao(FesteiroAvaliaEvento avaliacao, Long festeiroId) throws SQLException {
		Evento evento = eventoRepository.findById(avaliacao.getEventoId());
		if (evento == null) {
			throw new IllegalStateException("Evento não encontrado.");
		}

		Ingresso ingresso = ingressoRepository.findByEventoIdAndFesteiroId(avaliacao.getEventoId(), festeiroId);
		if (ingresso == null) {
			throw new IllegalStateException("Festeiro não possui ingresso para este evento.");
		}

		avaliacao.setFesteiroId(festeiroId);
		avaliacaoRepository.save(avaliacao);
	}

	public void atualizarAvaliacao(FesteiroAvaliaEvento avaliacao, Long festeiroId) throws SQLException {
		FesteiroAvaliaEvento existingAvaliacao = avaliacaoRepository
				.findByEventoIdAndFesteiroId(avaliacao.getEventoId(), festeiroId);
		if (existingAvaliacao == null) {
			throw new IllegalStateException("Avaliação não encontrada.");
		}

		avaliacao.setFesteiroId(festeiroId);
		avaliacaoRepository.update(avaliacao);
	}

	public void deletarAvaliacao(Long eventoId, Long festeiroId) throws SQLException {
		FesteiroAvaliaEvento existingAvaliacao = avaliacaoRepository.findByEventoIdAndFesteiroId(eventoId, festeiroId);
		if (existingAvaliacao == null) {
			throw new IllegalStateException("Avaliação não encontrada.");
		}

		avaliacaoRepository.delete(eventoId, festeiroId);
	}
}