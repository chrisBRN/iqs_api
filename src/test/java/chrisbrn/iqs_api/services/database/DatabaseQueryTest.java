package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.models.in.UserIn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseQueryTest {

	@Autowired DatabaseQuery databaseQuery;
	@Autowired DatabaseUpdate databaseUpdate;

	@Test
	void storedUserMatchesTokenUser() {

		UserIn fakeDatabase = new UserIn();
		fakeDatabase.setUsername("fake_username");
		fakeDatabase.setPassword("test");
		fakeDatabase.setRole("EMPLOYEE");
		fakeDatabase.setEmail("a@a.com");

		databaseUpdate.addUser(fakeDatabase);

		assertTrue(databaseQuery.storedUserMatchesTokenUser(new DecodedToken("fake_username", Role.EMPLOYEE, "a@a.com")));
		assertFalse(databaseQuery.storedUserMatchesTokenUser(new DecodedToken("nope", Role.ADMIN, "a@a.com")));
		assertFalse(databaseQuery.storedUserMatchesTokenUser(new DecodedToken("fake_username", Role.ADMIN, "a@a.com")));
		assertFalse(databaseQuery.storedUserMatchesTokenUser(new DecodedToken("fake_username", Role.ADMIN, "nope@a.com")));
	}
}