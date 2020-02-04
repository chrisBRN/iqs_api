package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.Credentials;

import chrisbrn.iqs_api.services.DatabaseService;
import chrisbrn.iqs_api.services.TokenService;
import chrisbrn.iqs_api.services.HttpServiceKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	TokenService tokenService;
	DatabaseService dbService;

	@Autowired
	public LoginController(TokenService tokenService, DatabaseService databaseService){
		this.tokenService = tokenService;
		this.dbService = databaseService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> processLogin(@ModelAttribute Credentials credentials) {

		if (dbService.areCredentialsValid(credentials)) {

			final String token = tokenService.generateJWT();

			return token == null ?
				HttpServiceKt.badRequest() :
				HttpServiceKt.ok(token);
		}

		return HttpServiceKt.badRequest();
	}
}
