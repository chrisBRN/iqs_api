package chrisbrn.iqs_api.contollers.user_management;

import chrisbrn.iqs_api.models.Role;
import chrisbrn.iqs_api.models.User;

import chrisbrn.iqs_api.services.authentication.BeanValidator;
import chrisbrn.iqs_api.services.authentication.PrivilegeValidator;
import chrisbrn.iqs_api.models.DecodedToken;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class AddUserController {

	@Autowired BeanValidator beanValidator;
	@Autowired PrivilegeValidator privilegeValidator;
	@Autowired DatabaseUpdate dbUpdate;

	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") DecodedToken token, @ModelAttribute User user) {

		beanValidator.checkUserModel(user);
		privilegeValidator.isAtLeast(Role.EMPLOYEE, token);
		privilegeValidator.hasRequiredHIERARCHY(token, user);

		return dbUpdate.addUser(user) ?
			ResponseEntity.status(HttpStatus.OK).body("User Added") :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To Add User - Username Already Exists");
	}
}
