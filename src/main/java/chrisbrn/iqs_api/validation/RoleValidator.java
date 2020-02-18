package chrisbrn.iqs_api.validation;



import chrisbrn.iqs_api.constants.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<RoleConstraint, String> {

	@Override
	public void initialize(RoleConstraint role) {}

	@Override
	public boolean isValid(String role, ConstraintValidatorContext cxt) {
		try {
			Role.valueOf(role);
			return true;
		} catch (Exception e){
			return false;
		}
	}
}