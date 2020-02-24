package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource"})
class DatabaseUpdateTest {

	@Autowired DatabaseUpdate dbUpdate;

	@Test
	void throws_ifGivenInvalidSQL() {
		assertThrows(UnableToExecuteStatementException.class, () -> dbUpdate.updateDatabase("test"));
	}

	@Test
	void DbUpdateReturnsFalseIfProvidedASyntacticallyValidButInvalidSQLUpdate() {

		String sql = (
			"INSERT INTO USERS (username) " +
				"VALUES ('test_username') " +
				"ON CONFLICT DO NOTHING;"
		);

		assertTrue(dbUpdate.updateDatabase(sql)); // valid update
		assertFalse(dbUpdate.updateDatabase(sql)); // duplicate key violation - valid SQL invalid update
	}

	@Test
	void DbUpdateReturnsTrueIfProvidedASyntacticallyValidAndValidSQLUpdate() {
		assertTrue(dbUpdate.updateDatabase("UPDATE SIGNER SET signer = 'test'"));
	}


	@Test
	void initUpdateCallsAddUserIfAdminCountIsZero() {
		DatabaseUpdate spy = Mockito.spy(dbUpdate);
		spy.initUpdate(0);
		verify(spy, times(1)).addUser(Mockito.any());
	}

	@Test
	void initUpdateDoesNothingIfAdminCountIsNotZero() {
		DatabaseUpdate spy = Mockito.spy(dbUpdate);

		for (int i = 1; i < 2500; i++) {
			spy.initUpdate(i);
			verify(spy, never()).addUser(Mockito.any());
		}
	}
}