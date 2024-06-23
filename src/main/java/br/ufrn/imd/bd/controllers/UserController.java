package br.ufrn.imd.bd.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import br.ufrn.imd.bd.model.dto.RecoveryJWTDTO;
import br.ufrn.imd.bd.model.dto.SigninDTO;
import br.ufrn.imd.bd.model.dto.UserDTO;
import br.ufrn.imd.bd.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<?> signon(@RequestBody(required = true) @Valid UserDTO signonRecord) {

		try {
			User userCreated = service.signon(signonRecord);
			URI uri = URI.create("/user/" + userCreated.getId());
			return ResponseEntity.created(uri).build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error on register user");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> signin(@RequestBody(required = true) @Valid SigninDTO signonRecord) {

		try {
			RecoveryJWTDTO token = service.login(signonRecord);
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Error on Login");
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody(required = true) @Valid UserDTO signonRecord,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		User u = service.findUserFromToken(authorizationHeader);
		if (!u.getEmail().equals(signonRecord.email())) {
			return ResponseEntity.badRequest().body("You cant edit others user");
		}

		service.update(signonRecord);

		return ResponseEntity.ok(service.findByEmail(signonRecord.email()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		User u = service.findUserFromToken(authorizationHeader);
		if (!u.getId().equals(id)) {
			return ResponseEntity.badRequest().body("You cant edit others user");
		}

		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
