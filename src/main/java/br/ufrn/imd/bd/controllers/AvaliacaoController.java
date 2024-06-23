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

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.AvaliacaoService;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obter todas as avaliações de um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliações obtidas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @GetMapping(value = "/evento/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAvaliacoesByEventoId(@PathVariable Long eventoId, HttpServletRequest request) {
        try {
            List<FesteiroAvaliaEvento> avaliacoes = avaliacaoService.getAvaliacoesByEventoId(eventoId);
            return ResponseEntity.ok(avaliacoes);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter a média de avaliações de um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Média de avaliações obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @GetMapping(value = "/evento/{eventoId}/media", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAverageRatingByEventoId(@PathVariable Long eventoId, HttpServletRequest request) {
        try {
            Double averageRating = avaliacaoService.getAverageRatingByEventoId(eventoId);
            return ResponseEntity.ok(averageRating);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter a avaliação de um evento por um festeiro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @GetMapping(value = "/{eventoId}/{festeiroId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAvaliacaoByEventoIdAndFesteiroId(@PathVariable Long eventoId,
                                                                 @PathVariable Long festeiroId,
                                                                 HttpServletRequest request) {
        try {
            FesteiroAvaliaEvento avaliacao = avaliacaoService.getAvaliacaoByEventoIdAndFesteiroId(eventoId, festeiroId);
            return ResponseEntity.ok(avaliacao);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Criar uma avaliação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarAvaliacao(@RequestBody FesteiroAvaliaEvento avaliacao,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                            HttpServletRequest request) {
        try {
            Long festeiroId = userService.findUserFromToken(authorizationHeader).getId();
            avaliacaoService.criarAvaliacao(avaliacao, festeiroId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar uma avaliação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping(value = "/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizarAvaliacao(@PathVariable Long eventoId,
                                                @RequestBody FesteiroAvaliaEvento avaliacao,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                HttpServletRequest request) {
        try {
            Long festeiroId = userService.findUserFromToken(authorizationHeader).getId();
            avaliacao.setEventoId(eventoId);
            avaliacaoService.atualizarAvaliacao(avaliacao, festeiroId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Deletar uma avaliação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping(value = "/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletarAvaliacao(@PathVariable Long eventoId,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                              HttpServletRequest request) {
        try {
            Long festeiroId = userService.findUserFromToken(authorizationHeader).getId();
            avaliacaoService.deletarAvaliacao(eventoId, festeiroId);
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
