package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.helpers.Logs;
import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.services.AuthenticationService;
import chrisbrn.iqs_api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	TokenService tokenService;
	AuthenticationService authService;

	@Autowired
	public LoginController(AuthenticationService authService, TokenService tokenService) {
		this.authService = authService;
		this.tokenService = tokenService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> processLogin(@ModelAttribute Credentials credentials) {

		if(authService.areCredentialsValid(credentials) == null) {

			HttpResponsesKt.badRequest("There Was An Error Please Try Again");
			Logs.databaseConnectionError();

		} else {

			final String token = tokenService.generateJWT();

			return token == null ?
				HttpResponsesKt.badRequest("There Was An Error Please Try Again") :
				HttpResponsesKt.ok(token);
		}

		return HttpResponsesKt.badRequest("Invalid Credentials");
	}
}
