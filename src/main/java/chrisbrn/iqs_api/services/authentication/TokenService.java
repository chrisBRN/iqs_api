package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.services.Log;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.logging.Level;

@Service
public class TokenService {

	private Algorithm algorithm;
	private JWTVerifier verifier;
	private final String issuer = "ChrisBRN";

	final Log logger = new Log();

	public void updateTokenParts(String signer) {

		if (signer == null) {
			logger.LOGGER.log(Level.SEVERE, "Database May Not Have Initialised Correctly");
			signer = "aR7mYV.-`R7(Gc5]Y&S965W:?N4w+*X@M";
		}

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
				.withClaim("email", user.getEmail())
				.sign(algorithm);
		} catch (Exception e) {
			throw new ResponseStatusException(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"there was an error, please try again"
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
				HttpStatus.FORBIDDEN,
				"please login"
			);
		}
	}
}