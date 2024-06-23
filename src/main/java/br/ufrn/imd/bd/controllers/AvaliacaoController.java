package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;
import br.ufrn.imd.bd.services.AvaliacaoService;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@GetMapping("/evento/{eventoId}")
	public List<FesteiroAvaliaEvento> getAvaliacoesByEventoId(@PathVariable Long eventoId) {
		return avaliacaoService.getAvaliacoesByEventoId(eventoId);
	}

	@GetMapping("/evento/{eventoId}/media")
	public Double getAverageRatingByEventoId(@PathVariable Long eventoId) {
		return avaliacaoService.getAverageRatingByEventoId(eventoId);
	}
}