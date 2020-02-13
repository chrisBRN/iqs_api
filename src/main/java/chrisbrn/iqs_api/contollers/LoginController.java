package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.LoginDetails;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.authentication.BeanValidator;
import chrisbrn.iqs_api.services.authentication.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired BeanValidator beanValidator;
	@Autowired DatabaseQuery dbQuery;
	@Autowired TokenService tkService;

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<String> Login(@ModelAttribute LoginDetails loginDetails) {
		beanValidator.checkLoginDetailsModel(loginDetails);

		Optional<User> user = dbQuery.loginIfSuppliedDetailsMatchDB(loginDetails);

		return user.isEmpty() ?
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials") :
			ResponseEntity.status(HttpStatus.OK).body(tkService.generateToken(user.get()));
	}
}