package chrisbrn.iqs_api.models;

public enum Role {
	ADMIN (3),
	STAFF (2),
	CANDIDATE (1);

	private final int hierarchyLevel;

	Role(int hierarchyLevel){
		this.hierarchyLevel = hierarchyLevel;
	}

	public int getPermissionLevel() {
		return this.hierarchyLevel;
	}
}