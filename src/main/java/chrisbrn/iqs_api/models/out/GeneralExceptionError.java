package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GeneralExceptionError {
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	private Integer statusCode = status.value();
	private String message = "there was an error, please try again. if the problem persists please contact an administrator";

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
