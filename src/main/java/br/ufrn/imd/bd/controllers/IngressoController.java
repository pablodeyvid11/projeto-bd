package br.ufrn.imd.bd.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.IngressoService;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

	@Autowired
	private IngressoService ingressoService;

	@Autowired
	private UserService userService;

	@Operation(summary = "Comprar ingresso para um evento")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Ingresso comprado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor") })
	@PostMapping(value = "/comprar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> comprarIngresso(@RequestParam Long eventoId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, HttpServletRequest request) {
		try {
			Long festeiroId = userService.findUserFromToken(authorizationHeader).getId();
			String token = ingressoService.comprarIngresso(eventoId, festeiroId);
			return ResponseEntity.status(HttpStatus.CREATED).body(token);
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
		}
	}
}
