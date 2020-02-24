package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.constants.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitialisation {

	@Autowired private DatabaseUpdate dbUpdateService;
	@Autowired private DatabaseQuery dbQueryService;

	@EventListener(ApplicationReadyEvent.class)
	private void setUp() {
		dbUpdateService.updateSigner();
		int adminCount = dbQueryService.getUserTypeCount(Role.ADMIN);
		dbUpdateService.initUpdate(adminCount);
	}
}