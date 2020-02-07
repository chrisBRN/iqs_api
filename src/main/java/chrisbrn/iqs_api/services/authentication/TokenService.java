package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.helpers.Logs;
import chrisbrn.iqs_api.models.TokenClaims;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	private DatabaseQueryService dbQueryService;

	private String signer;
	private Algorithm algorithm;
	private JWTVerifier verifier;
	private String issuer = "ChrisBRN";

	public TokenService(DatabaseQueryService dbQueryService) {
		this.dbQueryService = dbQueryService;
		this.signer = "temp";
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
	}

//	@EventListener(ApplicationReadyEvent.class)
//	@Scheduled(fixedDelay = hour * 24)
//	private void update() {
//		this.signer = dbQueryService.getSigner();
//		this.algorithm = Algorithm.HMAC256(signer);
//		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
//	}

	@EventListener(ApplicationReadyEvent.class)
	private void updateSigner(){
		this.signer = dbQueryService.getSigner();
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
	}

	public String generateToken(User user) {

		try {
			long hour = 1000L * 60L * 60L;
			return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(new Date(hour + System.currentTimeMillis()))
				.withClaim("role", user.getRole())
				.withClaim("username", user.getUsername())
				.withClaim("email", user.getEmail())
				.sign(algorithm);
		} catch (JWTCreationException exception) {
			Logs.tokenGenerationError();
			return null;
		}
	}

	public TokenClaims getDecodedJWT(String token) {
		try {
			DecodedJWT jwt = verifier.verify(token);
			return new TokenClaims(
				jwt.getClaim("role").asString(),
				jwt.getClaim("username").asString(),
				jwt.getClaim("email").asString()
			);
		} catch (JWTVerificationException exception) {
			return null;
		}
	}
}
