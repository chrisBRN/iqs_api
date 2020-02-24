package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ModelValidationError {

	private final String rejectedField;
	private final String rejectedValue;
	private final String message;

	public ModelValidationError(String rejectedField, String rejectedValue, String message) {
		this.rejectedField = rejectedField;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}

	public String getRejectedField() {
		return rejectedField;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

	public String getMessage() {
		return message;
	}
}