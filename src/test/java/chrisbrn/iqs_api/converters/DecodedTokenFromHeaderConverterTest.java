package chrisbrn.iqs_api.converters;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.services.authentication.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource"})
class DecodedTokenFromHeaderConverterTest {

	@Autowired private TokenService tokenService;
	@Autowired private DecodedTokenFromHeaderConverter converter;

	@Test
	void decodesAndConvertsValidToken() {
		UserDB user = new UserDB();
		user.setUsername("testAdminIn");
		user.setPassword("test_P@ssw0rd");
		user.setRole(Role.ADMIN.name());
		user.setEmail("a@a.com");

		String goodToken = tokenService.generateToken(user);
		DecodedToken goodConverted = converter.convert(goodToken);
		DecodedToken goodDecoded = tokenService.getDecodedJWT(goodToken);

		assertNotNull(goodConverted);
		assertEquals(goodConverted.getUsername(), goodDecoded.getUsername());
		assertEquals(goodConverted.getRole(), goodDecoded.getRole());
		assertEquals(goodConverted.getEmail(), goodDecoded.getEmail());
	}

	@Test
	void decodesAndAttemptsToConvert_ThrowsAndReturnsNull() {

		DecodedToken badConverted = converter.convert("badToken");

		assertNotNull(badConverted);
		assertSame(badConverted.getRole(), Role.NO_ROLE);
	}
}