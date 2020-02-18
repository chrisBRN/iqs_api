package chrisbrn.iqs_api.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> modelValidationError(MethodArgumentNotValidException exception){

		List<ModelValidationError> errors = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()){

			String field 	= ((FieldError) error).getField();
			String rejected = (String) ((FieldError) error).getRejectedValue();
			String message 	= error.getDefaultMessage();

			if (field.equals("password")){
				rejected = "********";
			}

			errors.add(new ModelValidationError(field, rejected, message));
		}

		return ResponseEntity.status(BAD_REQUEST).body(errors);
	}

	@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
	private static class ModelValidationError {

		private String rejectedField;
		private String rejectedValue;
		private String message;

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
}
