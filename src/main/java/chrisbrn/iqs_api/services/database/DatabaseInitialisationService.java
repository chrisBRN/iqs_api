package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.authentication.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitialisationService {

	private DatabaseUpdateService dbUpdateService;
	private DatabaseQueryService dbQueryService;

	@Autowired
	public DatabaseInitialisationService(DatabaseUpdateService dbUpdateService, DatabaseQueryService dbQueryService) {
		this.dbUpdateService = dbUpdateService;
		this.dbQueryService = dbQueryService;
	}

	@EventListener(ApplicationReadyEvent.class)
	private void initialSetup() {

		dbUpdateService.updateSigner();

		User init = new User();

		init.setUsername("Admin");
		init.setPassword("ChangeThis");
		init.setRole(Role.ADMIN.name());
		init.setEmail("");

		if (!dbQueryService.userExists(init.getUsername())) {
			dbUpdateService.addUser(init);
		}
	}
}