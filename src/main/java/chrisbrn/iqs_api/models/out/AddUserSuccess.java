package chrisbrn.iqs_api.models.out;

import chrisbrn.iqs_api.models.in.UserIn;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddUserSuccess implements Serializable {

	private final HttpStatus status = HttpStatus.OK;
	private final Integer statusCode = status.value();

	@JsonProperty("added_user")
	private final UserIn user;

	public AddUserSuccess(UserIn user) {
		this.user = user;
		user.setPassword("********");
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public UserIn getUser() {
		return user;
	}
}
