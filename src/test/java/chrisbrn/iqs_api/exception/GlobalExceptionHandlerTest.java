package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.converters.DecodedTokenFromHeaderConverter;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.models.in.LoginDetails;
import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.services.authentication.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource"})
class GlobalExceptionHandlerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private DecodedTokenFromHeaderConverter converter;
	@MockBean private TokenService tokenService;

	private String login = "http://localhost:8080/login";
	private String addUser = "http://localhost:8080/admin/add-user";

	@Test
	void handlesMediaUnsupportedException_ReturnsStatusAndMessage_All() throws Exception {

		mockMvc.perform(post(login)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.ALL)
			.content("This Isn't Json Oh No!"))
			.andDo(print())
			.andExpect(status().isUnsupportedMediaType())
			.andExpect(jsonPath("$.message", is("please format your request as json")))
			.andReturn();
	}

	@Test
	void handlesMediaUnsupportedException_ReturnsStatusAndMessage_Params() throws Exception {

		mockMvc.perform(post(addUser)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
			.param("username", "username")
			.param("password", "test_P@ssw0rd")
			.param("role", "ADMIN")
			.param("email", "test@test.com"))
			.andExpect(status().isUnsupportedMediaType())
			.andExpect(jsonPath("$.message", is("please format your request as json")))
			.andReturn();
	}

	@Test
	void handlesMethodArgumentNotValidException_ReturnsStatusAndMessage_Login() throws Exception {

		String badUsername = "H@sSymbol";
		String badPassword = "short";

		LoginDetails badDetails = new LoginDetails(badUsername, badPassword);

		mockMvc.perform(post(login)
			.content(objectMapper.writeValueAsString(badDetails))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[*].rejected_field", hasItem("username")))
			.andExpect(jsonPath("$.[*].rejected_value", hasItem(badUsername)))
			.andExpect(jsonPath("$.[*].rejected_field", hasItem("password")))
			.andExpect(jsonPath("$.[*].rejected_value", hasItem("********")))
			.andReturn();
	}

	@Test
	void handlesMethodArgumentNotValidException_ReturnsStatusAndMessage_AddUser() throws Exception {

		String token = "test";

		String badUsername = "H@sSymbol";
		String badPassword = "short";
		String badRole = "Not A Role";
		String badEmail = "Not An Email";

		UserIn badUserIn = new UserIn();
		badUserIn.setUsername(badUsername);
		badUserIn.setPassword(badPassword);
		badUserIn.setRole(badRole);
		badUserIn.setEmail(badEmail);

		when(converter.convert(Mockito.anyString())).thenReturn(new DecodedToken("", Role.NO_ROLE, ""));

		mockMvc.perform(post(addUser)
			.header("token", token)
			.content(objectMapper.writeValueAsString(badUserIn))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(4)))
			.andExpect(jsonPath("$.[*].rejected_field", hasItem("username")))
			.andExpect(jsonPath("$.[*].rejected_value", hasItem(badUsername)))
			.andExpect(jsonPath("$.[*].rejected_field", hasItem("password")))
			.andExpect(jsonPath("$.[*].rejected_value", hasItem("********")))
			.andExpect(jsonPath("$.[*].rejected_field", hasItem("role")))
			.andExpect(jsonPath("$.[*].rejected_value", hasItem(badRole)))
			.andExpect(jsonPath("$.[*].rejected_field", hasItem("email")))
			.andExpect(jsonPath("$.[*].rejected_value", hasItem(badEmail)))
			.andReturn();
	}
}