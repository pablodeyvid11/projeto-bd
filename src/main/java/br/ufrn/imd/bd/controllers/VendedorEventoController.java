package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.VendedorHasEvento;
import br.ufrn.imd.bd.services.UserService;
import br.ufrn.imd.bd.services.VendedorEventoService;

@RestController
@RequestMapping("/vendedor-evento")
public class VendedorEventoController {

	@Autowired
	private VendedorEventoService vendedorEventoService;

	@Autowired
	private UserService userService;

	@PostMapping("/solicitar")
	public void solicitarVenda(@RequestParam Long eventoId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		vendedorEventoService.solicitarVenda(vendedorId, eventoId);
	}

	@PutMapping("/aprovar")
	public void aprovarVenda(@RequestParam Long vendedorId, @RequestParam Long eventoId,
			@RequestParam Boolean isAprovado, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		vendedorEventoService.aprovarVenda(vendedorId, eventoId, organizadorId, isAprovado);
	}

	@GetMapping("/status")
	public VendedorHasEvento getStatus(@RequestParam Long eventoId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		return vendedorEventoService.findByVendedorIdAndEventoId(vendedorId, eventoId);
	}

	@GetMapping("/aprovados")
	public List<VendedorHasEvento> getVendedoresAprovados(@RequestParam Long eventoId) {
		return vendedorEventoService.findVendedoresAprovados(eventoId);
	}

	@GetMapping("/negados")
	public List<VendedorHasEvento> getVendedoresNegados(@RequestParam Long eventoId) {
		return vendedorEventoService.findVendedoresNegados(eventoId);
	}

	@GetMapping("/pendentes")
	public List<VendedorHasEvento> getVendedoresPendentes(@RequestParam Long eventoId) {
		return vendedorEventoService.findVendedoresPendentes(eventoId);
	}
}