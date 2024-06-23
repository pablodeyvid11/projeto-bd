package br.ufrn.imd.bd.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public String comprarIngresso(Long eventoId, Long festeiroId) {
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
