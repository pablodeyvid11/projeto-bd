package br.ufrn.imd.bd.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.ufrn.imd.bd.model.UserDetailsImpl;

@Service
public class JwtTokenService {

	@Value("festivalle.jwt.secret")
	private String SECRET_KEY;
	@Value("festivalle.jwt.issuer")
	private String ISSUER;

	public String generateToken(UserDetailsImpl user) {
		try {

			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

			return JWT.create().withIssuer(ISSUER) 
					.withIssuedAt(creationDate()) 
					.withExpiresAt(expirationDate())
					.withSubject(user.getUsername())
					.sign(algorithm); 
			
		} catch (JWTCreationException exception) {
			throw new JWTCreationException("Erro ao gerar token.", exception);
		}
	}

	public String getSubjectFromToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

			return JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();

		} catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Token inválido ou expirado.");
		}
	}

	private Instant creationDate() {
		return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
	}

	private Instant expirationDate() {
		return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
	}

}