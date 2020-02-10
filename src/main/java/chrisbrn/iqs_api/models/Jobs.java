package chrisbrn.iqs_api.models;

public enum Jobs {

	ADD_USER (RequiredPrivilege.ROLE_HIERARCHY),
	EDIT_USER (RequiredPrivilege.ROLE_HIERARCHY);

	private final RequiredPrivilege pLevel;

	Jobs(RequiredPrivilege pLevel){
		this.pLevel = pLevel;
	}

	public RequiredPrivilege getRequiredPrivilege() {
		return this.pLevel;
	}
}



