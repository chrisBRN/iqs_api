package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MediaTypeNotSupportedError {
	private final HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
	private final Integer statusCode = status.value();
	private final String message = "please format your request as json";

	public HttpStatus getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}
}