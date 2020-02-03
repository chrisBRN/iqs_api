package chrisbrn.iqs_api.contollers;



import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.services.AuthServiceKt;
import chrisbrn.iqs_api.services.HttpResonsesKt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> handleLoginAttempt(@ModelAttribute Credentials credentials) {

		if (AuthServiceKt.validateCredentials(credentials)) {

			final String token = AuthServiceKt.generateJWT();

			return token == null ?
				HttpResonsesKt.badRequest() :
				HttpResonsesKt.ok(token);
		};

		return HttpResonsesKt.badRequest();
	}
}
