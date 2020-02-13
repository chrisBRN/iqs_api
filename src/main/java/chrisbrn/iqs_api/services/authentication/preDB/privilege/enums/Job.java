package chrisbrn.iqs_api.services.authentication.preDB.privilege.enums;

public enum Job {

	ADD_USER (PrivilegeType.HIERARCHY),
	DELETE_USER (PrivilegeType.HIERARCHY),
	EDIT_SELF (PrivilegeType.SELF);

	private final PrivilegeType required;

	Job(PrivilegeType required){
		this.required = required;
	}

	public PrivilegeType getRequiredPrivilege() {
		return this.required;
	}
}
