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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Noticia;
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.NoticiaService;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obter todas as notícias de um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícias obtidas com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/evento/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNoticiasByEventoId(@PathVariable Long eventoId, HttpServletRequest request) {
        try {
            List<Noticia> noticias = noticiaService.getNoticiasByEventoId(eventoId);
            return ResponseEntity.ok(noticias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter notícia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNoticiaById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Noticia noticia = noticiaService.getNoticiaById(id);
            return ResponseEntity.ok(noticia);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Criar uma nova notícia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notícia criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNoticia(@RequestBody Noticia noticia, @RequestParam Long eventoId,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                           HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            noticia.setEventoId(eventoId);
            noticiaService.createNoticia(noticia, organizadorId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar uma notícia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateNoticia(@PathVariable Long id, @RequestBody Noticia noticia,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                           HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            noticia.setId(id);
            noticiaService.updateNoticia(noticia, organizadorId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Deletar uma notícia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNoticia(@PathVariable Long id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                           HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            noticiaService.deleteNoticia(id, organizadorId);
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
