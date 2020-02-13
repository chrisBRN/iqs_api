package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.preDB.privilege.enums.Role;
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
			User user = new User();
			user.setUsername("Admin");
			user.setRole(Role.ADMIN.name());
			user.setPassword("Ch@ngeTh1s!");
			dbUpdateService.addUser(user);
		}
	}
}