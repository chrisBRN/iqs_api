package chrisbrn.iqs_api.contollers.user_management;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.model.BeanValidator;
import chrisbrn.iqs_api.services.authentication.privilege.PrivilegeValidator;
import chrisbrn.iqs_api.services.authentication.privilege.enums.Role;
import chrisbrn.iqs_api.services.authentication.privilege.RoleUtilities;
import chrisbrn.iqs_api.models.api.DecodedToken;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class DeleteUserController {

	@Autowired BeanValidator beanValidator;
	@Autowired PrivilegeValidator privilegeValidator;
	@Autowired RoleUtilities roleUtilities;
	@Autowired DatabaseQuery dbQuery;
	@Autowired DatabaseUpdate dbUpdate;

	@RequestMapping(value = "/delete-user", method = POST)
	public ResponseEntity<String> deleteUser(@RequestHeader(value = "token") DecodedToken token, @RequestParam User user) {

		beanValidator.checkUserModel(user);
		privilegeValidator.hasRequiredHIERARCHY(token, user);

		if (roleUtilities.getRoleFromString(user.getRole()) == Role.ADMIN ){

			if (dbQuery.getUserTypeCount(Role.ADMIN) < 2){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Be Deleted");
			}
		}

		return dbUpdate.deleteUser(user) ?
			ResponseEntity.status(HttpStatus.OK).body("Success - User Deleted") :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To Delete User");
	}
}
