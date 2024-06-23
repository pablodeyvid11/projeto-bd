package br.ufrn.imd.bd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Evento;
import br.ufrn.imd.bd.services.EventoService;
import br.ufrn.imd.bd.services.UserService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getAllEventos() {
		return ResponseEntity.ok(eventoService.getAllEventos());
	}

	@GetMapping("/{id}")
	public Evento getEventoById(@PathVariable Long id) {
		return eventoService.getEventoById(id);
	}

	@PostMapping
	public void createEvento(@RequestBody Evento evento, @RequestParam Long estabelecimentoCnpj,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		eventoService.createEvento(evento, organizadorId, estabelecimentoCnpj);
	}

	@PutMapping("/{id}")
	public void updateEvento(@PathVariable Long id, @RequestBody Evento evento,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		evento.setId(id);
		eventoService.updateEvento(evento, organizadorId);
	}

	@DeleteMapping("/{id}")
	public void deleteEvento(@PathVariable Long id,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		eventoService.deleteEvento(id, organizadorId);
	}
}
