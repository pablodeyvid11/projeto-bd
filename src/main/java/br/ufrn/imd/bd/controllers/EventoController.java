package br.ufrn.imd.bd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.services.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@GetMapping
	public ResponseEntity<?> getAllEventos() {
		return ResponseEntity.ok(eventoService.getAllEventos());
	}
}
