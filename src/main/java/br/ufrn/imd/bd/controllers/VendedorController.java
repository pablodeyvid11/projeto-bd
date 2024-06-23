package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.ProdutoService;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obter todos os produtos do vendedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos obtidos com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMyProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                              HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            List<VendedorProdutos> produtos = produtoService.getProdutosByVendedorId(vendedorId);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter produto do vendedor por nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto obtido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/produtos/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProdutoByNomeAndVendedorId(@PathVariable String nome,
                                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                           HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            VendedorProdutos produto = produtoService.getProdutoByNomeAndVendedorId(nome, vendedorId);
            return ResponseEntity.ok(produto);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Criar um novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(value = "/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduto(@RequestBody VendedorProdutos produto,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                           HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            produtoService.createProduto(produto, vendedorId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar um produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(value = "/produtos/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduto(@PathVariable String nome, @RequestBody VendedorProdutos produto,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                           HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            produto.setProductName(nome);
            produto.setVendedorId(vendedorId);
            produtoService.updateProduto(produto, vendedorId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Deletar um produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping(value = "/produtos/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduto(@PathVariable String nome,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                           HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            produtoService.deleteProduto(nome, vendedorId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }
}
