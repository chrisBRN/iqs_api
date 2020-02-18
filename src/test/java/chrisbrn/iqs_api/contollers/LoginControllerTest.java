package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.models.in.LoginDetails;
import chrisbrn.iqs_api.models.in.UserIn;
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
		UserIn testUserIn = new UserIn();
		testUserIn.setUsername("testUsername");
		testUserIn.setPassword("test_P@ssw0rd");
		dbUpdate.addUser(testUserIn);
	}

	@Test
	void returns_OkStatusAndToken_WithCorrectLoginDetails() throws Exception {

		LoginDetails correctLogin = new LoginDetails(
			"testUsername",
			"test_P@ssw0rd");

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(correctLogin))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token", is(notNullValue())))
			.andReturn();
	}

	@Test
	void returns_BadStatus_WithValidButIncorrectLoginDetails() throws Exception {

		LoginDetails ValidButIncorrectLoginDetails = new LoginDetails(
			"wrongUsername",
			"wrong_P@ssw0rd");

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(ValidButIncorrectLoginDetails))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.information", is("Invalid Credentials")))
			.andReturn();
	}

	@Test
	void returns_BadStatusAndMessage_WithInvalidLoginDetails_tooLong() throws Exception {

		LoginDetails tooLong = new LoginDetails(
			"usernameIsFarTooLongSoShouldThrowABC",
			"Passw@rd15too_LongSoShouldTho3w12345");

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(tooLong))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Username")))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Password")))
			.andReturn();
	}

	@Test
	void returns_BadStatusAndMessage_WithInvalidLoginDetails_tooShort() throws Exception {

		LoginDetails tooShort = new LoginDetails(
			"test",
			"Sh@rt1");

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(tooShort))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Username")))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Password")))
			.andReturn();
	}

	@Test
	void returns_BadStatusAndMessage_WithInvalidLoginDetails_symbolsInWrongPlaces() throws Exception {

		LoginDetails wrongSymbols = new LoginDetails(
			"test@sern1e",
			"nosymbols");

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(wrongSymbols))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Username")))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Password")))
			.andReturn();
	}


	@Test
	void returns_BadStatusAndMessage_WithInvalidLoginDetails_null() throws Exception {

		LoginDetails incorrectUsernameFormatLogin = new LoginDetails(
			null,
			null);

		mockMvc.perform(post("http://localhost:8080/login")
			.content(objectMapper.writeValueAsString(incorrectUsernameFormatLogin))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Username")))
			.andExpect(jsonPath("$.[*].message", hasItem("Invalid Password")))
			.andReturn();
	}

}