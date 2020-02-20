package chrisbrn.iqs_api.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class RoleTests {

	@Test
	void canGetRoleFromString_OnFailureProvidesNO_ROLE() {

		Role bad_noWhereNear = Role.roleFromString("Not A Role");
		Role bad_lower = Role.roleFromString("admin");
		Role bad_proper = Role.roleFromString("Candidate");
		Role good_admin = Role.roleFromString("ADMIN");
		Role good_employee = Role.roleFromString("EMPLOYEE");
		Role good_candidate = Role.roleFromString("CANDIDATE");
		Role bad_noRole = Role.roleFromString("NO_ROLE");

		assertSame(bad_noWhereNear, Role.NO_ROLE);
		assertSame(bad_lower, Role.NO_ROLE);
		assertSame(bad_proper, Role.NO_ROLE);
		assertSame(good_admin, Role.ADMIN);
		assertSame(good_employee, Role.EMPLOYEE);
		assertSame(good_candidate, Role.CANDIDATE);

		assertSame(bad_noRole, Role.NO_ROLE);
	}
}