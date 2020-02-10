package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitialisation {

	@Autowired private DatabaseUpdate dbUpdateService;
	@Autowired private DatabaseQuery dbQueryService;

	@EventListener(ApplicationReadyEvent.class)
	private void initialSetup() {

		dbUpdateService.updateSigner();

		User init = new User();

		init.setUsername("Admin");
		init.setPassword("ChangeThis_4E@6d6u?");
		init.setRole("ADMIN");
		init.setEmail("");

		if (!dbQueryService.userExists(init.getUsername())) {
			dbUpdateService.addUser(init);
		}
	}
}