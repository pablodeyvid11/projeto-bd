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

import br.ufrn.imd.bd.model.VendedorProdutos;
import br.ufrn.imd.bd.services.ProdutoService;
import br.ufrn.imd.bd.services.UserService;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private UserService userService;

	@GetMapping("/produtos")
	public List<VendedorProdutos> getAllMyProducts(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		return produtoService.getProdutosByVendedorId(vendedorId);
	}

	@GetMapping("/produtos/{nome}")
	public VendedorProdutos getProdutoByNomeAndVendedorId(@PathVariable String nome,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		return produtoService.getProdutoByNomeAndVendedorId(nome, vendedorId);
	}

	@PostMapping("/produtos")
	public void createProduto(@RequestBody VendedorProdutos produto,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		produtoService.createProduto(produto, vendedorId);
	}

	@PutMapping("/produtos/{nome}")
	public void updateProduto(@PathVariable String nome, @RequestBody VendedorProdutos produto,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		produto.setProductName(nome);
		produto.setVendedorId(vendedorId);
		produtoService.updateProduto(produto, vendedorId);
	}

	@DeleteMapping("/produtos/{nome}")
	public void deleteProduto(@PathVariable String nome,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
		produtoService.deleteProduto(nome, vendedorId);
	}
}
