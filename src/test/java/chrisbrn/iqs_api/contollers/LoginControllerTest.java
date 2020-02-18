package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.models.in.LoginDetails;
import chrisbrn.iqs_api.models.in.User;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource", "testLoginDetails"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Jdbi jdbi;

	@Autowired
	private DatabaseUpdate dbUpdate;


	@BeforeAll
	public void beforeAll() {
		User testUser = new User();
		testUser.setUsername("testUsername");
		testUser.setPassword("test_P@ssw0rd");
		dbUpdate.addUser(testUser);
	}

	@Test
	void returns_OkStatusAndToken_WithCorrectLoginDetails() throws Exception {

		LoginDetails correctLogin = new LoginDetails("testUsername", "test_P@ssw0rd");
		String json = objectMapper.writeValueAsString(correctLogin);

		mockMvc.perform(post("http://localhost:8080/login")
			.content(json)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token", is(notNullValue())))
			.andReturn();
	}

	@Test
	void returns_BadStatus_WithValidButIncorrectLoginDetails() throws Exception {

		LoginDetails incorrectLogin = new LoginDetails("wrongUsername", "wrong_P@ssw0rd");
		String json = objectMapper.writeValueAsString(incorrectLogin);

		mockMvc.perform(post("http://localhost:8080/login")
			.content(json)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.information", is("Invalid Credentials")))
			.andReturn();
	}

	@Test
	void returns_BadStatusAndMessage_WithInvalidLoginDetails() throws Exception {

		LoginDetails incorrectUsernameFormatLogin = new LoginDetails("us@r", "wrong");

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(incorrectUsernameFormatLogin))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(4)))
			.andExpect(jsonPath("$.[*].message", hasItem("Minimum length 5, max length 32")))
			.andExpect(jsonPath("$.[*].message", hasItem("Only letters and / or numbers")))
			.andExpect(jsonPath("$.[*].message", hasItem("Minimum length 8, max length 32")))
			.andExpect(jsonPath("$.[*].message", hasItem("Password must contain at least one uppercase letter, one lowercase letter, a number & a symbol")))
			.andReturn();
	}

}