package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.AuthenticationService;
import chrisbrn.iqs_api.services.TokenService;
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

	@Autowired
	public LoginController(AuthenticationService authService, TokenService tokenService) {
		this.authService = authService;
		this.tokenService = tokenService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> processLogin(@ModelAttribute Credentials credentials) {

		final String token = tokenService.generateJWT();

		if(token == null){
			return HttpResponsesKt.serverError();
		}

		return authService.areCredentialsValid(credentials) ?
			HttpResponsesKt.okLogin(token) :
			HttpResponsesKt.badRequest("Invalid Credentials");
	}
}
