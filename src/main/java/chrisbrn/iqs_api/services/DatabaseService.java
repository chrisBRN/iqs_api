package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.Credentials;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DatabaseService {

	private Jdbi jdbi;

	@Autowired
	public DatabaseService(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	public Optional<String> getStoredPassword(String username) {

		String sql = "SELECT password FROM USERS WHERE username = '" + username + "'";

		return jdbi.withHandle(handle -> handle
				.createQuery(sql)
				.mapTo(String.class)
				.findFirst());
	}
}


