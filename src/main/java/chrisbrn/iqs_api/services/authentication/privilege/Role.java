package chrisbrn.iqs_api.services.authentication.privilege;

public enum Role {
	ADMIN (3),
	EMPLOYEE (2),
	CANDIDATE (1);

	private final int hierarchy;

	Role(int hierarchy){
		this.hierarchy = hierarchy;
	}

	public int getHierarchy() {
		return this.hierarchy;
	}
}