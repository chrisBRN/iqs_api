package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.Credentials;

import chrisbrn.iqs_api.services.DatabaseService;
import chrisbrn.iqs_api.services.AuthenticationService;
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

	AuthenticationService authService;

	@Autowired
	public LoginController(AuthenticationService authService) {
		this.authService = authService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> processLogin(@ModelAttribute Credentials credentials) {

		if (authService.areCredentialsValid(credentials)) {

			final String token = authService.generateJWT();

			return token == null ?
				HttpServiceKt.badRequest("There Was An Error Please Try Again") :
				HttpServiceKt.ok(token);
		}

		return HttpServiceKt.badRequest("Invalid Credentials");
	}
}
