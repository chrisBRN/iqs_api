package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class InitModeService {

	private Jdbi jdbi;
	private Credentials credentials;

	@Autowired
	public InitModeService(Credentials credentials, Jdbi jdbi) {
		this.credentials = credentials;
		this.jdbi = jdbi;
	}

	private User initUser (){
		return new User(credentials.getUsername(), BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt()), "ADMIN");
	}

	@EventListener(ApplicationReadyEvent.class)
	private void initializeAdminUserIfNoOtherUsersExist(){

		User initUser = initUser();

		String username = initUser.getUsername();
		String password = initUser.getPassword();
		String role = initUser.getRole();

		String sql = 	"DO " +
						"$init_mode_user$ " +
						"BEGIN " +
						"IF (SELECT COUNT (*) from USERS) = 0 THEN " +
						"    INSERT INTO users (username, password, role) " +
						"    VALUES ('" + username + "', '" + password + "', '" + role + "'); " +
						"ELSE " +
						"    DELETE FROM USERS WHERE username = '" + username + "'; " +
						"END IF; " +
						"END " +
						"$init_mode_user$;";

		jdbi.withHandle(handle -> handle.createUpdate(sql).execute());
	}
}
