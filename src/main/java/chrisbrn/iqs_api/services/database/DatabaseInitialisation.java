package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.UserIn;
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

		int count = dbQueryService.getUserTypeCount(Role.ADMIN);

		if (count == 0){
			UserIn userIn = new UserIn();
			userIn.setUsername("Admin");
			userIn.setRole(Role.ADMIN.name());
			userIn.setPassword("Ch@ngeTh1s!");
			userIn.setEmail("a@a.com");
			dbUpdateService.addUser(userIn);
		}
	}
}