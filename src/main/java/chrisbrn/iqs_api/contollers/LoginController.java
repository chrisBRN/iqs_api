package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.AuthenticationService;
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
	AuthenticationService authService;
	DatabaseQueryService dbQueryService;

	@Autowired
	public LoginController(AuthenticationService authService, DatabaseQueryService dbQueryService, TokenService tokenService) {
		this.authService = authService;
		this.tokenService = tokenService;
		this.dbQueryService = dbQueryService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> Login(@ModelAttribute Credentials credentials) {

		Optional<User> user = dbQueryService.getUser(credentials.getUsername());

		if (user.isEmpty() || !authService.isPasswordValid(credentials, user.get())) {
			return HttpResponsesKt.badRequest("Invalid Credentials");
		}

		String token = tokenService.generateJWT(user.get().getRole());

		return token == null ?
			HttpResponsesKt.serverError() :
			HttpResponsesKt.okLogin(tokenService.generateJWT(user.get().getRole()));
	}
}
