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
public class InitialisationService {

	private Credentials initAdminCredentials;
	private Jdbi jdbi;
	private AuthenticationService authService;

	@Autowired
	public InitialisationService(Credentials initAdminCredentials, Jdbi jdbi, AuthenticationService authService) {
		this.initAdminCredentials = initAdminCredentials;
		this.jdbi = jdbi;
		this.authService = authService;
	}

	@EventListener(ApplicationReadyEvent.class)
	private void initializeAdminUserIfNoOtherUsersExist(){

		String username = initAdminCredentials.getUsername();
		String password = authService.hashPassword(initAdminCredentials.getPassword());

		String sql = (

			"DO " +
			"$init_mode_user$ " +
			"BEGIN " +
			"IF (SELECT COUNT (*) from USERS) = 0 THEN " +
			"    INSERT INTO users (username, password, role) " +
			"    VALUES ('" + username + "', '" + password + "', 'ADMIN'); " +
			"END IF; " +
			"END " +
			"$init_mode_user$;"
		);

		jdbi.withHandle(handle -> handle.createUpdate(sql).execute());
	}

}


