package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.bd.model.Ingresso;
import br.ufrn.imd.bd.repository.interfaces.EventosRepository;
import br.ufrn.imd.bd.repository.interfaces.FesteiroRepository;
import br.ufrn.imd.bd.repository.interfaces.IngressoRepository;

@Service
public class IngressoService {
	@Autowired
	private IngressoRepository ingressoRepository;

	@Autowired
	private EventosRepository eventoRepository;

	@Autowired
	private FesteiroRepository festeiroRepository;

	@Transactional(rollbackFor = SQLException.class)
	public String comprarIngresso(Long eventoId, Long festeiroId) throws SQLException {
		if (!festeiroRepository.existsById(festeiroId)) {
			throw new IllegalStateException("Festeiro não encontrado.");
		}

		int ingressosVendidos = ingressoRepository.countByEventoId(eventoId);
		int qtdIngressos = eventoRepository.findById(eventoId).getQtyIngressos();

		if (ingressosVendidos >= qtdIngressos) {
			throw new IllegalStateException("Ingressos esgotados.");
		}

		String token = UUID.randomUUID().toString();
		Ingresso ingresso = new Ingresso();
		ingresso.setToken(token);
		ingresso.setEventoId(eventoId);
		ingresso.setFesteiroId(festeiroId);

		ingressoRepository.save(ingresso);

		return token;
	}
}
