package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.bd.model.VendedorHasEvento;
import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;
import br.ufrn.imd.bd.repository.interfaces.VendedorEventoRepository;

@Service
public class VendedorEventoService {
	@Autowired
	private VendedorEventoRepository vendedorEventoRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	@Transactional(rollbackFor = SQLException.class)
	public void solicitarVenda(Long vendedorId, Long eventoId) throws SQLException {
		VendedorHasEvento vendedorEvento = new VendedorHasEvento();
		vendedorEvento.setVendedorId(vendedorId);
		vendedorEvento.setEventoId(eventoId);
		vendedorEventoRepository.solicitarVenda(vendedorEvento);
	}

	@Transactional(rollbackFor = SQLException.class)
	public void aprovarVenda(Long vendedorId, Long eventoId, Long organizadorId, Boolean isAprovado)
			throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			vendedorEventoRepository.aprovarVenda(vendedorId, eventoId, isAprovado);
		} else {
			throw new IllegalStateException("Somente organizadores podem aprovar vendas.");
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	public VendedorHasEvento findByVendedorIdAndEventoId(Long vendedorId, Long eventoId) throws SQLException {
		return vendedorEventoRepository.findByVendedorIdAndEventoId(vendedorId, eventoId);
	}

	@Transactional(rollbackFor = SQLException.class)
	public List<VendedorHasEvento> findVendedoresAprovados(Long eventoId) throws SQLException {
		return vendedorEventoRepository.findAllByEventoIdAndIsAprovado(eventoId, true);
	}

	@Transactional(rollbackFor = SQLException.class)
	public List<VendedorHasEvento> findVendedoresNegados(Long eventoId) throws SQLException {
		return vendedorEventoRepository.findAllByEventoIdAndIsAprovado(eventoId, false);
	}

	@Transactional(rollbackFor = SQLException.class)
	public List<VendedorHasEvento> findVendedoresPendentes(Long eventoId) throws SQLException {
		return vendedorEventoRepository.findAllByEventoIdAndIsAprovadoIsNull(eventoId);
	}
}
