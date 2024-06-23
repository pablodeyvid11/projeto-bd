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
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Estabelecimento;
import br.ufrn.imd.bd.services.EstabelecimentoService;
import br.ufrn.imd.bd.services.UserService;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

	@Autowired
	private EstabelecimentoService estabelecimentoService;

	@Autowired
	private UserService userService;

	@GetMapping
	public List<Estabelecimento> getAllEstabelecimentos() {
		return estabelecimentoService.getAllEstabelecimentos();
	}

	@GetMapping("/{cnpj}")
	public Estabelecimento getEstabelecimentoById(@PathVariable Long cnpj) {
		return estabelecimentoService.getEstabelecimentoById(cnpj);
	}

	@PostMapping
	public void createEstabelecimento(@RequestBody Estabelecimento estabelecimento,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		estabelecimentoService.createEstabelecimento(estabelecimento, organizadorId);
	}

	@PutMapping("/{cnpj}")
	public void updateEstabelecimento(@PathVariable Long cnpj, @RequestBody Estabelecimento estabelecimento,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		estabelecimento.setCnpj(cnpj + "");
		estabelecimentoService.updateEstabelecimento(estabelecimento, organizadorId);
	}

	@DeleteMapping("/{cnpj}")
	public void deleteEstabelecimento(@PathVariable Long cnpj,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
		estabelecimentoService.deleteEstabelecimento(cnpj, organizadorId);
	}
}