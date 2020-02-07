package chrisbrn.iqs_api.config;

import chrisbrn.iqs_api.models.Signer;
import org.jdbi.v3.core.Jdbi;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

// https://stackoverflow.com/questions/33633243/connecting-to-heroku-postgres-from-spring-boot

@Configuration
@EnableScheduling
public class AppConfig {

	@Bean
	@Profile("dataSource")
	public DataSource getDataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];

		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		return DataSourceBuilder.create()
			.driverClassName("org.postgresql.Driver")
			.username(username)
			.password(password)
			.url(dbUrl)
			.build();
	}

	@Bean
	public Jdbi getJdbi(DataSource dataSource) {
		return Jdbi.create(dataSource);
	}
}
