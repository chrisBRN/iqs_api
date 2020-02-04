package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.Credentials;

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

	@Autowired
	public LoginController(TokenService tokenService){
		this.tokenService = tokenService;
	}

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> processLogin(@ModelAttribute Credentials credentials) {

		if (tokenService.validateCredentials(credentials)) {

			final String token = tokenService.generateJWT();

			return token == null ?
				HttpServiceKt.badRequest() :
				HttpServiceKt.ok(token);
		}

		return HttpServiceKt.badRequest();
	}
}
