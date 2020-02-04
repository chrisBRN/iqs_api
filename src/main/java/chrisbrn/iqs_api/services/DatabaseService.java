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

	private Credentials credentials;
	private Jdbi jdbi;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public DatabaseService(Credentials credentials, Jdbi jdbi, PasswordEncoder passwordEncoder) {
		this.credentials = credentials;
		this.jdbi = jdbi;
		this.passwordEncoder = passwordEncoder;
	}

	@EventListener(ApplicationReadyEvent.class)
	private void initializeAdminUserIfNoOtherUsersExist(){

		String username = credentials.getUsername();
		String password = passwordEncoder.encode(credentials.getPassword());

		String sql = 	"DO " +
						"$init_mode_user$ " +
						"BEGIN " +
						"IF (SELECT COUNT (*) from USERS) = 0 THEN " +
						"    INSERT INTO users (username, password, role) " +
						"    VALUES ('" + username + "', '" + password + "', 'ADMIN'); " +
						"END IF; " +
						"END " +
						"$init_mode_user$;";

		jdbi.withHandle(handle -> handle.createUpdate(sql).execute());
	}

	public boolean areCredentialsValid(Credentials credentials) {
		String sql = "SELECT password FROM USERS WHERE username = '" + credentials.getUsername() + "'";
		Optional<String> stored_hash = jdbi.withHandle(handle -> handle.createQuery(sql).mapTo(String.class).findFirst());

		return 	stored_hash.isPresent() &&
				checkPassword(credentials.getPassword(), stored_hash.get());
	}

	public boolean checkPassword(String submittedPassword, String storedPassword) {
		return passwordEncoder.matches(submittedPassword, storedPassword);
	}
}


