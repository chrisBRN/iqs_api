package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.LoginDetails;
import chrisbrn.iqs_api.services.HttpService;
import chrisbrn.iqs_api.services.authentication.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
@RequestMapping("/login")
public class LoginController {

	private DatabaseQuery databaseQuery;
	private TokenService tokenService;
	private HttpService httpService;

	@Autowired
	public LoginController(DatabaseQuery databaseQuery, TokenService tokenService, HttpService httpService) {
		this.databaseQuery = databaseQuery;
		this.tokenService = tokenService;
		this.httpService = httpService;
	}

	@ResponseBody
	@RequestMapping(value = "", method = POST, consumes = "application/json", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@Valid @RequestBody LoginDetails loginDetails) {

		Optional<UserDB> dbUser = (
			databaseQuery.getUserWhereLoginDetailsMatchDB(loginDetails)
		);

		return dbUser.isPresent() ?
			httpService.loginSuccess(tokenService.generateToken(dbUser.get())) :
			httpService.loginFailure();
	}
}