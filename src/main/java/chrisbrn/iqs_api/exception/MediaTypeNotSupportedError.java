package chrisbrn.iqs_api.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.MediaType;

import java.util.Optional;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MediaTypeNotSupportedError {
	private String message = "Please use a Json Format";

	public String getMessage() {
		return message;
	}
}
