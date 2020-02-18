package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.LoginDetails;
import chrisbrn.iqs_api.services.HttpService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired DatabaseQuery databaseQuery;
	@Autowired HttpService httpService;

	@RequestMapping(value = "", method = POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Login(@Valid @RequestBody LoginDetails loginDetails) {

		Optional<UserDB> dbUser = (
			databaseQuery.getUserWhereLoginDetailsMatchDB(loginDetails)
		);

		return dbUser.isPresent() ?
			httpService.loginSuccess(dbUser.get()) :
			httpService.loginFailure();
	}
}