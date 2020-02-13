package chrisbrn.iqs_api.contollers.user_management;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.preDB.model.BeanValidator;
import chrisbrn.iqs_api.models.api.DecodedToken;
import chrisbrn.iqs_api.services.authentication.preDB.privilege.PrivilegeValidator;
import chrisbrn.iqs_api.services.authentication.preDB.privilege.enums.Role;
import chrisbrn.iqs_api.services.authentication.token.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class EditSelfController {

	@Autowired TokenService tokenService;
	@Autowired BeanValidator beanValidator;
	@Autowired PrivilegeValidator privilegeValidator;

	@Autowired DatabaseUpdate dbUpdate;

	@RequestMapping(value = "/edit-self", method = POST)
	public ResponseEntity<String> editSelf(@RequestHeader(value = "token") DecodedToken token, @ModelAttribute User updated) {

		beanValidator.userBeanIsValid(updated);
		privilegeValidator.isAtLeast(Role.CANDIDATE, token);

		return dbUpdate.editSelf(token.getUsername(), updated) ?
			ResponseEntity.status(HttpStatus.OK).body(tokenService.generateToken(updated)) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To Updated User");
	}
}
