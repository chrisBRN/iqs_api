package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.constants.Role;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DatabaseInitialisationTest {

	@MockBean private DatabaseQuery dbQueryService;
	@Autowired private DatabaseUpdate dbUpdate;
	@Autowired private DatabaseInitialisation init = new DatabaseInitialisation();

//	@Test
//	public void skipsAddUserWhenDBHasAnAdmin() throws NoSuchMethodException, NoSuchFieldException, IOException {
//
//		EmbeddedPostgres pg = EmbeddedPostgres.builder().start();
//		Jdbi jdbi = new Jdbi(pg);
//
//		when(dbQueryService.getUserTypeCount(Role.ADMIN)).thenReturn(1);
//
//		Method method = DatabaseInitialisation.class.getDeclaredMethod("initialSetup");
//		method.setAccessible(true);
//
//
//		when(dbQueryService.getUserTypeCount(Role.ADMIN)).thenReturn(1);
//
//		assertEquals(1, DatabaseInitialisation.class.getField("count"));
//
//
//	}
}