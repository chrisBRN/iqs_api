package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.models.DecodedToken;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.utilities.AuthenticationUtilities;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class TokenService {

	@Autowired private AuthenticationUtilities utils;

	private Algorithm algorithm;
	private JWTVerifier verifier;
	private final String issuer = "ChrisBRN";

	// Called each time the signer is updated via the DatabaseUpdate
	public void updateTokenParts(String signer) {
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
	}

	public String generateToken(User userDB) {
		try {
			long hour = 1000L * 60L * 60L;
			return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(new Date(hour + System.currentTimeMillis()))
				.withClaim("role", userDB.getRole())
				.withClaim("username", userDB.getUsername())
				.withClaim("email", userDB.getEmail())
				.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new ResponseStatusException(
				HttpStatus.SERVICE_UNAVAILABLE, "Error - Please Try Again", e
			);
		}
	}

	public DecodedToken getDecodedJWT(String token) {
		try {
			DecodedJWT jwt = verifier.verify(token);

			return new DecodedToken(
				jwt.getClaim("role").asString(),
				jwt.getClaim("username").asString(),
				jwt.getClaim("email").asString()
			);
		} catch (JWTVerificationException e) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "Invalid Token - Please Login", e
			);
		}
	}
}