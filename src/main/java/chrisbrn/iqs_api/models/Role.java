package chrisbrn.iqs_api.models;

public enum Role {
	ADMIN (3),
	EMPLOYEE (2),
	CANDIDATE (1),
	NO_ROLE (0);

	private final int hierarchy;

	Role(int hierarchy){
		this.hierarchy = hierarchy;
	}

	public int getHierarchy() {
		return this.hierarchy;
	}
}