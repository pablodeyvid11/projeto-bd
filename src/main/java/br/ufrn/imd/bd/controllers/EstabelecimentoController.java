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

import br.ufrn.imd.bd.model.Estabelecimento;
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.EstabelecimentoService;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obter todos os estabelecimentos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estabelecimentos obtidos com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEstabelecimentos(HttpServletRequest request) {
        try {
            List<Estabelecimento> estabelecimentos = estabelecimentoService.getAllEstabelecimentos();
            return ResponseEntity.ok(estabelecimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter estabelecimento por CNPJ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estabelecimento obtido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @GetMapping(value = "/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEstabelecimentoById(@PathVariable String cnpj, HttpServletRequest request) {
        try {
            Estabelecimento estabelecimento = estabelecimentoService.getEstabelecimentoById(cnpj);
            return ResponseEntity.ok(estabelecimento);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Criar um novo estabelecimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Estabelecimento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEstabelecimento(@RequestBody Estabelecimento estabelecimento,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                   HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            estabelecimentoService.createEstabelecimento(estabelecimento, organizadorId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar um estabelecimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estabelecimento atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(value = "/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEstabelecimento(@PathVariable Long cnpj,
                                                   @RequestBody Estabelecimento estabelecimento,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                   HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            estabelecimento.setCnpj(cnpj + "");
            estabelecimentoService.updateEstabelecimento(estabelecimento, organizadorId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Deletar um estabelecimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estabelecimento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping(value = "/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEstabelecimento(@PathVariable String cnpj,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                   HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            estabelecimentoService.deleteEstabelecimento(cnpj, organizadorId);
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
