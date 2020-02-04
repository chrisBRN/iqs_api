package chrisbrn.iqs_api.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// https://stackoverflow.com/questions/33633243/connecting-to-heroku-postgres-from-spring-boot

@Configuration
public class DatabaseConfig {

	@Bean
	@Profile("dataSource")
	public DataSource getDataSource() throws URISyntaxException, IOException {
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

	@Bean(name="signer")
	@Profile("signer")
	public String getSigner(){
		return System.getenv("SIGNER");
	}

}
