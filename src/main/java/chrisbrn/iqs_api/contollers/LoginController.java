package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.Credentials;

import chrisbrn.iqs_api.services.AuthService;
import chrisbrn.iqs_api.services.HttpServiceKt;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	AuthService authService;

	@Autowired
	public LoginController(AuthService authService){
		this.authService = authService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> processLogin(@ModelAttribute Credentials credentials) {

		if (authService.validateCredentials(credentials)) {

			final String token = authService.generateJWT();

			return token == null ?
				HttpServiceKt.badRequest() :
				HttpServiceKt.ok(token);
		}

		return HttpServiceKt.badRequest();
	}
}
