package chrisbrn.iqs_api.config;

import chrisbrn.iqs_api.models.LoginDetails;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;

@TestConfiguration
public class IntegrationTestConfig {

	@Bean
	@Profile("testDataSource")
	public DataSource getDataSource() throws IOException {
		EmbeddedPostgres pg = EmbeddedPostgres.builder().start();
		return pg.getPostgresDatabase();
	}

	@Bean
	@Profile("testLoginDetails")
	public LoginDetails getLoginDetails() {
		return new LoginDetails(
			"test_username",
			"test_P@ssw0rd"
		);
	}

}
