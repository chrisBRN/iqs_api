package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.api.LoginDetails;
import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.token.TokenService;
import chrisbrn.iqs_api.utilities.HttpResponse;
import chrisbrn.iqs_api.services.authentication.AuthenticateLogin;
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

	@Autowired AuthenticateLogin authLogin;
	@Autowired TokenService tokenService;

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> Login(@ModelAttribute LoginDetails loginDetails) {

		Optional<User> user = 	authLogin.getUserIfDetailsMatchDB(loginDetails);

		if(user.isEmpty()) {
			return HttpResponse.forbidden();
		}

		String token = tokenService.generateToken(user.get());

		return token == null ?
			HttpResponse.tryAgain() :
			HttpResponse.loginSuccess(token);
	}
}