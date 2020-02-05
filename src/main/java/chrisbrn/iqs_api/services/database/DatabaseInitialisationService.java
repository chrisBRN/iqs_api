package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitialisationService {

	private Credentials initAdminCredentials;
	private DatabaseUpdateService dbService;

	@Autowired
	public DatabaseInitialisationService(Credentials initAdminCredentials, DatabaseUpdateService dbService) {
		this.initAdminCredentials = initAdminCredentials;
		this.dbService = dbService;
	}

	@EventListener(ApplicationReadyEvent.class)
	private void initializeAdminUserIfNoOtherUsersExist(){

		String username = initAdminCredentials.getUsername();
		String password = dbService.hashPassword(initAdminCredentials.getPassword());

		String sql = (

			"DO " +
			"$init_mode_user$ " +
			"BEGIN " +
			"IF (SELECT COUNT (*) from USERS) = 0 THEN " +
			"    INSERT INTO users (username, password, role) " +
			"    VALUES ('" + username + "', '" + password + "', 'INITIAL'); " +
			"END IF; " +
			"END " +
			"$init_mode_user$;"
		);

		dbService.updateDatabase(sql);
	}
}