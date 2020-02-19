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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource", "testLoginDetails"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginControllerTest {

	@Autowired private ObjectMapper objectMapper;
	@Autowired private MockMvc mockMvc;
	@Autowired private Jdbi jdbi;
	@Autowired private DatabaseUpdate dbUpdate;

	private String endpoint = "http://localhost:8080/login";

	@BeforeAll
	public void beforeAll() {
		UserIn fakeCorrectDetailsUser = new UserIn();
		fakeCorrectDetailsUser.setUsername("testUsername");
		fakeCorrectDetailsUser.setPassword("test_P@ssw0rd");
		dbUpdate.addUser(fakeCorrectDetailsUser);
	}

	@Test
	void returns_OkStatusAndToken_WithCorrectLoginDetails() throws Exception {

		LoginDetails correctLogin = new LoginDetails(
			"testUsername",
			"test_P@ssw0rd");

		mockMvc.perform(post(endpoint)
			.content(objectMapper.writeValueAsString(correctLogin))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token", is(notNullValue())))
			.andReturn();
	}

	@Test
	void returns_BadStatus_WithValidButIncorrectLoginDetails() throws Exception {

		LoginDetails validButIncorrectLoginDetails = new LoginDetails(
			"wrongUsername",
			"wrong_P@ssw0rd");

		mockMvc.perform(post(endpoint)
			.content(objectMapper.writeValueAsString(validButIncorrectLoginDetails))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.information", is("invalid credentials")))
			.andReturn();
	}
}