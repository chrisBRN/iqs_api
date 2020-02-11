package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.models.authentication.BooleanResponsePair;
import chrisbrn.iqs_api.services.authentication.token.TokenClaimsModel;
import chrisbrn.iqs_api.services.authentication.AuthenticationUtilities;
import chrisbrn.iqs_api.services.authentication.Role;
import chrisbrn.iqs_api.services.authentication.token.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import chrisbrn.iqs_api.utilities.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class UserManagementController {

	@Autowired AuthenticationUtilities authUtils;
	@Autowired TokenService tokenService;

	@Autowired DatabaseQuery dbQuery;
	@Autowired DatabaseUpdate dbUpdate;


	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		Optional<TokenClaimsModel> claims = tokenService.getDecodedJWT(token);

//		BooleanResponsePair pair1 = new BooleanResponsePair(claims.isEmpty(), HttpResponse.forbidden());
//		BooleanResponsePair pair2 = new BooleanResponsePair(authUtils.userModelMatchesExpectedPatterns(user), HttpResponse.badDto());
//		BooleanResponsePair pair3 = new BooleanResponsePair(authUtils.roleHasRequiredMinimumOneToOnePrivilege(claims.get().getRole(), user), HttpResponse.unauthorised());


		if (claims.isEmpty()) {
			return HttpResponse.forbidden();
		}

		if (authUtils.userModelMatchesExpectedPatterns(user)) {
			return HttpResponse.badDto();
		}

		if (authUtils.roleHasRequiredMinimumOneToOnePrivilege(claims.get().getRole(), user)) {
			return HttpResponse.unauthorised();
		}

		if (dbQuery.userExistsByUsername(user.getUsername())) {
			return HttpResponse.bad("Username Taken");
		}

		return dbUpdate.addUser(user) ?
			HttpResponse.ok("User Added") :
			HttpResponse.bad("Failed To Add User");
	}

	@RequestMapping(value = "/edit-self", method = POST)
	public ResponseEntity<String> editSelf(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		Optional<TokenClaimsModel> claims = tokenService.getDecodedJWT(token);

		if (claims.isEmpty()) {
			return HttpResponse.forbidden();
		}

		if (authUtils.userModelMatchesExpectedPatterns(user)) {
			return HttpResponse.badDto();
		}

		// Using an out of date token i.e. token has old username since changed
		if (!dbQuery.userExistsByUsername(claims.get().getUsername())) {
			return HttpResponse.oldToken();
		}

		Optional<String> newToken = tokenService.generateToken(user);

		if (newToken.isEmpty()){
			return HttpResponse.tryAgain();
		}

		return dbUpdate.editSelf(claims.get().getUsername(), user) ?
			HttpResponse.okNewToken(newToken.get()) :
			HttpResponse.bad("Failed To Updated User");
	}

	@RequestMapping(value = "/delete-user", method = POST)
	public ResponseEntity<String> deleteUser(@RequestHeader(value = "token") String token, @RequestParam String userId) {

		Optional<TokenClaimsModel> claims = tokenService.getDecodedJWT(token);

		if (claims.isEmpty()) {
			return HttpResponse.forbidden();
		}

		Optional<User> userToDelete = dbQuery.getUserById(userId);

		if (userToDelete.isEmpty()){
			return HttpResponse.badDto();
		}

		if (authUtils.roleHasRequiredMinimumOneToOnePrivilege(claims.get().getRole(), userToDelete.get())) {
			return HttpResponse.unauthorised();
		}

		if (authUtils.getRoleFromString(claims.get().getRole()) == Role.ADMIN &&
			authUtils.getRoleFromString(userToDelete.get().getRole()) == Role.ADMIN ){

			if (dbQuery.getUserTypeCount(Role.ADMIN) == 1){
				return HttpResponse.bad("Last Admin User - Cannot Be Deleted");
			}
		}

		return dbUpdate.deleteUser(userId) ?
			HttpResponse.ok("Success - User Deleted") :
			HttpResponse.bad("Failed To Delete User");
	}
}
