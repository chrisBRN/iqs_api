package chrisbrn.iqs_api.config;


import chrisbrn.iqs_api.models.Credentials;
import org.jdbi.v3.core.Jdbi;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// https://stackoverflow.com/questions/33633243/connecting-to-heroku-postgres-from-spring-boot

@Configuration
public class AppConfig {

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

	@Bean
	@Profile("loginConfig")
	public Credentials getCredentials() {
		return new Credentials(System.getenv("INIT_USERNAME"), System.getenv("INIT_PASSWORD"));
	}

	@Bean
	public Jdbi getJdbi(DataSource dataSource) {
		return Jdbi.create(dataSource);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}
}
