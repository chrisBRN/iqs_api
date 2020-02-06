package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;

import chrisbrn.iqs_api.services.AuthenticationKt;
import chrisbrn.iqs_api.services.TestKt;
import chrisbrn.iqs_api.services.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	TokenService tokenService;
	DatabaseQueryService dbQueryService;

	@Autowired
	public LoginController(DatabaseQueryService dbQueryService, TokenService tokenService) {
		this.tokenService = tokenService;
		this.dbQueryService = dbQueryService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> Login(@ModelAttribute Credentials credentials) {

		TestKt.dosomething();

		Optional<User> user = dbQueryService.getUser(credentials.getUsername());

		if (user.isEmpty() || !AuthenticationKt.isPasswordValid(credentials, user.get())) {
			return HttpResponsesKt.badRequest("Invalid Credentials");
		}

		String token = tokenService.generateJWT(user.get());

		return token == null ?
			HttpResponsesKt.serverError() :
			HttpResponsesKt.okLogin(token);
	}
}
