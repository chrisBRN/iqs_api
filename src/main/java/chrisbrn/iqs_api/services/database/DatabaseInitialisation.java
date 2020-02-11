package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseInitialisation {

	@Autowired private DatabaseUpdate dbUpdateService;
	@Autowired private DatabaseQuery dbQueryService;

	@EventListener(ApplicationReadyEvent.class)
	private void initialSetup() {

		dbUpdateService.updateSigner();

		Optional<User> init = dbQueryService.getUserByUsername("Admin");

		if(init.isPresent()){

			if(init.get().getPassword() == null){
				dbUpdateService.updatePassword("Admin","ChangeThis_4E@6d6u?");
			}
		}
	}
}