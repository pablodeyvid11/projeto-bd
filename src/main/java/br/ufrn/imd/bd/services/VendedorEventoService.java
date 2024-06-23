package br.ufrn.imd.bd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.VendedorHasEvento;
import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;
import br.ufrn.imd.bd.repository.interfaces.VendedorEventoRepository;

@Service
public class VendedorEventoService {
	@Autowired
	private VendedorEventoRepository vendedorEventoRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	public void solicitarVenda(Long vendedorId, Long eventoId) {
		VendedorHasEvento vendedorEvento = new VendedorHasEvento();
		vendedorEvento.setVendedorId(vendedorId);
		vendedorEvento.setEventoId(eventoId);
		vendedorEventoRepository.solicitarVenda(vendedorEvento);
	}

	public void aprovarVenda(Long vendedorId, Long eventoId, Long organizadorId, Boolean isAprovado) {
		if (organizadorRepository.existsById(organizadorId)) {
			vendedorEventoRepository.aprovarVenda(vendedorId, eventoId, isAprovado);
		} else {
			throw new IllegalStateException("Somente organizadores podem aprovar vendas.");
		}
	}

	public VendedorHasEvento findByVendedorIdAndEventoId(Long vendedorId, Long eventoId) {
		return vendedorEventoRepository.findByVendedorIdAndEventoId(vendedorId, eventoId);
	}

	public List<VendedorHasEvento> findVendedoresAprovados(Long eventoId) {
		return vendedorEventoRepository.findAllByEventoIdAndIsAprovado(eventoId, true);
	}

	public List<VendedorHasEvento> findVendedoresNegados(Long eventoId) {
		return vendedorEventoRepository.findAllByEventoIdAndIsAprovado(eventoId, false);
	}

	public List<VendedorHasEvento> findVendedoresPendentes(Long eventoId) {
		return vendedorEventoRepository.findAllByEventoIdAndIsAprovadoIsNull(eventoId);
	}
}
