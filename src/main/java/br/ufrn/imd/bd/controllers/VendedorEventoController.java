package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.VendedorHasEvento;
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.UserService;
import br.ufrn.imd.bd.services.VendedorEventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vendedor-evento")
public class VendedorEventoController {

    @Autowired
    private VendedorEventoService vendedorEventoService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Solicitar venda em um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitação realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(value = "/solicitar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> solicitarVenda(@RequestParam Long eventoId,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                            HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            vendedorEventoService.solicitarVenda(vendedorId, eventoId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Aprovar ou rejeitar solicitação de venda em um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitação atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(value = "/aprovar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> aprovarVenda(@RequestParam Long vendedorId, @RequestParam Long eventoId,
                                          @RequestParam Boolean isAprovado,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                          HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            vendedorEventoService.aprovarVenda(vendedorId, eventoId, organizadorId, isAprovado);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter status da solicitação de venda em um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status obtido com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatus(@RequestParam Long eventoId,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                       HttpServletRequest request) {
        try {
            Long vendedorId = userService.findUserFromToken(authorizationHeader).getId();
            VendedorHasEvento status = vendedorEventoService.findByVendedorIdAndEventoId(vendedorId, eventoId);
            return ResponseEntity.ok(status);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter lista de vendedores aprovados para um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vendedores aprovados obtida com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/aprovados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVendedoresAprovados(@RequestParam Long eventoId, HttpServletRequest request) {
        try {
            List<VendedorHasEvento> vendedores = vendedorEventoService.findVendedoresAprovados(eventoId);
            return ResponseEntity.ok(vendedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter lista de vendedores negados para um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vendedores negados obtida com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/negados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVendedoresNegados(@RequestParam Long eventoId, HttpServletRequest request) {
        try {
            List<VendedorHasEvento> vendedores = vendedorEventoService.findVendedoresNegados(eventoId);
            return ResponseEntity.ok(vendedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter lista de vendedores pendentes para um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vendedores pendentes obtida com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/pendentes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVendedoresPendentes(@RequestParam Long eventoId, HttpServletRequest request) {
        try {
            List<VendedorHasEvento> vendedores = vendedorEventoService.findVendedoresPendentes(eventoId);
            return ResponseEntity.ok(vendedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }
}
