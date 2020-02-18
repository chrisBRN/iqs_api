package chrisbrn.iqs_api.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.jdbi.v3.core.Jdbi;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableScheduling
public class AppConfig {

	@Bean(name = "dataSource")
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
