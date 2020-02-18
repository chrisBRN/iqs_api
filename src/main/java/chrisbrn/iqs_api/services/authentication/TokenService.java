package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.DecodedToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

	private Algorithm algorithm;
	private JWTVerifier verifier;
	private final String issuer = "ChrisBRN";

	// Called each time the signer is updated via the DatabaseUpdate
	public void updateTokenParts(String signer) {
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
	}

	public String generateToken(UserDB user) {
		try {
			long hour = 1000L * 60L * 60L;
			return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(new Date(hour + System.currentTimeMillis()))
				.withClaim("role", user.getRole())
				.withClaim("username", user.getUsername())
				.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new ResponseStatusException(
				HttpStatus.SERVICE_UNAVAILABLE, "error - please try again", e
			);
		}
	}

	public DecodedToken getDecodedJWT(String token) {
		try {
			DecodedJWT jwt = verifier.verify(token);
			return new DecodedToken(
				jwt.getClaim("username").asString(),
				Role.valueOf(jwt.getClaim("role").asString()),
				jwt.getClaim("email").asString()
			);
		} catch (Exception e) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "invalid token - please login", e
			);
		}
	}
}