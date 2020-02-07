package chrisbrn.iqs_api.services.authentication;

public enum Role {

	ADMIN (	Privilege.CAN_ADD_ADMIN_USER,
			Privilege.CAN_ADD_STAFF_USER,
			Privilege.CAN_ADD_CANDIDATE_USER
	),

	STAFF (
			Privilege.CAN_ADD_STAFF_USER,
			Privilege.CAN_ADD_CANDIDATE_USER
	),

	CANDIDATE;

	private Privilege[] privileges;

	private Role(Privilege...privileges){
		this.privileges = privileges;
	}

	public Privilege[] getPrivileges() {
		return privileges;
	}
}
