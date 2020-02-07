package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;


import chrisbrn.iqs_api.services.authentication.AuthUtilitiesKt;
import chrisbrn.iqs_api.services.authentication.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	DatabaseQueryService dbQueryService;
	TokenService tokenService;

	@Autowired
	public LoginController(DatabaseQueryService dbQueryService, TokenService tokenService) {
		this.dbQueryService = dbQueryService;
		this.tokenService = tokenService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> Login(@ModelAttribute Credentials credentials) {

		User user = dbQueryService.getUser(credentials.getUsername());

		if (user == null || !AuthUtilitiesKt.isPasswordValid(credentials, user)) {
			return HttpResponsesKt.badRequest("Invalid Credentials");
		}

		String token = tokenService.generateToken(user);

		return token == null ?
			HttpResponsesKt.serverError() :
			HttpResponsesKt.okLogin(token);
	}
}
