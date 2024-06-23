package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import br.ufrn.imd.bd.model.Noticia;
import br.ufrn.imd.bd.services.NoticiaService;
import br.ufrn.imd.bd.services.UserService;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

	@Autowired
	private NoticiaService noticiaService;

	@Autowired
	private UserService userService;

	@GetMapping("/evento/{eventoId}")
	public List<Noticia> getNoticiasByEventoId(@PathVariable Long eventoId) {
		return noticiaService.getNoticiasByEventoId(eventoId);
	}

	@GetMapping("/{id}")
	public Noticia getNoticiaById(@PathVariable Long id) {
		return noticiaService.getNoticiaById(id);
	}

	@PostMapping
	public void createNoticia(@RequestBody Noticia noticia, @RequestParam Long eventoId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		noticia.setEventoId(eventoId);
		noticiaService.createNoticia(noticia, organizadorId);
	}

	@PutMapping("/{id}")
	public void updateNoticia(@PathVariable Long id, @RequestBody Noticia noticia,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		noticia.setId(id);
		noticiaService.updateNoticia(noticia, organizadorId);
	}

	@DeleteMapping("/{id}")
	public void deleteNoticia(@PathVariable Long id,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		noticiaService.deleteNoticia(id, organizadorId);
	}
}