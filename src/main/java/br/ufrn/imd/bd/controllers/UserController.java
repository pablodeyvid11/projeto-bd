package br.ufrn.imd.bd.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.User;
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.model.dto.RecoveryJWTDTO;
import br.ufrn.imd.bd.model.dto.SigninDTO;
import br.ufrn.imd.bd.model.dto.UserDTO;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Registrar um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signon(@RequestBody(required = true) @Valid UserDTO signonRecord) {
        try {
            User userCreated = service.signon(signonRecord);
            URI uri = URI.create("/user/" + userCreated.getId());
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "/user", "Error on register user"));
        }
    }

    @Operation(summary = "Login de um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Login incorreto")
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(@RequestBody(required = true) @Valid SigninDTO signonRecord) {
        try {
            RecoveryJWTDTO token = service.login(signonRecord);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), "/user/login", "Login incorreto"));
        }
    }

    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody(required = true) @Valid UserDTO signonRecord,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            User u = service.findUserFromToken(authorizationHeader);
            if (!u.getEmail().equals(signonRecord.email())) {
                return ResponseEntity.badRequest().body(
                    new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), "/user", "You can't edit others user"));
            }
            service.update(signonRecord);
            return ResponseEntity.ok(service.findByEmail(signonRecord.email()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), "/user", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "/user", "Error on update user"));
        }
    }

    @Operation(summary = "Deletar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            User u = service.findUserFromToken(authorizationHeader);
            if (!u.getId().equals(id)) {
                return ResponseEntity.badRequest().body(
                    new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), "/user/" + id, "You can't delete others user"));
            }
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), "/user/" + id, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "/user/" + id, "Error on delete user"));
        }
    }
}
