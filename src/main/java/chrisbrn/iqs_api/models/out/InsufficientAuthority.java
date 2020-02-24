package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InsufficientAuthority implements Serializable {

	private final HttpStatus status = HttpStatus.FORBIDDEN;
	private final Integer statusCode = status.value();
	private final String information = "user lacks required permissions, please contract the administration team if this is unexpected";

	public HttpStatus getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getInformation() {
		return information;
	}
}
