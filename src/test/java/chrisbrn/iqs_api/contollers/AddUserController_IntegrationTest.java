package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.converters.DecodedTokenFromHeaderConverter;
import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.services.authentication.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles({"testDataSource"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddUserController_IntegrationTest {

	@Autowired private ObjectMapper objectMapper;
	@Autowired private MockMvc mockMvc;
	@Autowired private Jdbi jdbi;
	@Autowired private DatabaseUpdate update;

	@MockBean private DecodedTokenFromHeaderConverter converter;
	@MockBean private TokenService tokenService;
	@MockBean private DatabaseUpdate dbUpdate;
	@MockBean private DatabaseQuery dbQuery;

	private String token = "mocked";

	private UserIn adminIn = new UserIn();
	private UserIn employeeIn = new UserIn();
	private UserIn candidateIn = new UserIn();
	private UserIn noRoleIn = new UserIn();

	private UserDB adminDB = new UserDB();

	private DecodedToken adminToken;
	private DecodedToken employeeToken;
	private DecodedToken candidateToken;
	private DecodedToken noRoleToken;

	@BeforeAll
	public void beforeAll() {

		this.adminIn.setUsername("testAdmin");
		this.adminIn.setPassword("test_P@ssw0rd");
		this.adminIn.setRole(Role.ADMIN.name());
		this.adminIn.setEmail("a@a.com");

		this.employeeIn.setUsername("testEmployeeIn");
		this.employeeIn.setPassword("test_P@ssw0rd");
		this.employeeIn.setRole(Role.EMPLOYEE.name());

		this.candidateIn.setUsername("testCandidateIn");
		this.candidateIn.setPassword("test_P@ssw0rd");
		this.candidateIn.setRole(Role.CANDIDATE.name());

		this.noRoleIn.setUsername("testCandidateIn");
		this.noRoleIn.setPassword("test_P@ssw0rd");
		this.noRoleIn.setRole(Role.NO_ROLE.name());

		this.adminDB.setUsername("testAdmin");
		this.adminDB.setPassword("test_P@ssw0rd");
		this.adminDB.setRole(Role.ADMIN.name());
		this.adminDB.setEmail("a@a.com");

		// Tokens
		this.adminToken = new DecodedToken("testAdmin", Role.ADMIN, "a@a.com");
		this.employeeToken = new DecodedToken("testEmployeeDB", Role.EMPLOYEE, "a@a.com");
		this.candidateToken = new DecodedToken("testCandidateDB", Role.CANDIDATE, "a@a.com");
		this.noRoleToken = new DecodedToken("testNoRoleDB", Role.NO_ROLE, "a@a.com");
	}

	private void performSuccessfulAddUser(UserIn requestBody) throws Exception {

		mockMvc.perform(post("http://localhost:8080/admin/add-user")
			.header("token", token)
			.content(objectMapper.writeValueAsString(requestBody))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.added_user", is(notNullValue())))
			.andReturn();
	}

	private void performUnSuccessfulAddUser_LacksPermission(UserIn requestBody) throws Exception {

		mockMvc.perform(post("http://localhost:8080/admin/add-user")
			.header("token", token)
			.content(objectMapper.writeValueAsString(requestBody))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.information", is("user lacks required permissions, please contract the administration team if this is unexpected")))
			.andReturn();
	}

	@Test
	void loggedInAdminCanAddAnyUser() throws Exception {

		when(converter.convert(Mockito.anyString())).thenReturn(adminToken);
		when(dbQuery.storedUserMatchesTokenUser(adminToken)).thenReturn(true);
		when(dbUpdate.addUser(Mockito.any(UserIn.class))).thenReturn(true);

		performSuccessfulAddUser(candidateIn);
		performSuccessfulAddUser(employeeIn);
		performSuccessfulAddUser(adminIn);
		performUnSuccessfulAddUser_LacksPermission(noRoleIn);
	}

	@Test
	void loggedInEmployeeCanOnlyAddACandidate() throws Exception {

		when(converter.convert(token)).thenReturn(employeeToken);
		when(dbUpdate.addUser(Mockito.any(UserIn.class))).thenReturn(true);

		performSuccessfulAddUser(candidateIn);
		performUnSuccessfulAddUser_LacksPermission(employeeIn);
		performUnSuccessfulAddUser_LacksPermission(adminIn);
		performUnSuccessfulAddUser_LacksPermission(noRoleIn);
	}

	@Test
	void loggedInCandidateCanNotAddAUser() throws Exception {

		when(converter.convert(token)).thenReturn(candidateToken);

		performUnSuccessfulAddUser_LacksPermission(candidateIn);
		performUnSuccessfulAddUser_LacksPermission(employeeIn);
		performUnSuccessfulAddUser_LacksPermission(adminIn);
		performUnSuccessfulAddUser_LacksPermission(noRoleIn);
	}

	@Test
	void loggedInNO_ROLECanNotAddAUser() throws Exception {

		when(converter.convert(token)).thenReturn(noRoleToken);

		performUnSuccessfulAddUser_LacksPermission(candidateIn);
		performUnSuccessfulAddUser_LacksPermission(employeeIn);
		performUnSuccessfulAddUser_LacksPermission(adminIn);
		performUnSuccessfulAddUser_LacksPermission(noRoleIn);
	}

	@Test
	void addingDuplicateUserReturns_BadStatusAndMessage() throws Exception {

		when(converter.convert(token)).thenReturn(adminToken);
		when(dbUpdate.addUser(Mockito.any(UserIn.class))).thenReturn(false);

		mockMvc.perform(post("http://localhost:8080/admin/add-user")
			.header("token", token)
			.content(objectMapper.writeValueAsString(candidateIn))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.information", is("failed to add user - username is already taken")))
			.andReturn();
	}

	@Test
	void tokenDatabaseMisMatch() throws Exception {

		UserIn whatWeHave = new UserIn();
		whatWeHave.setUsername("test");
		whatWeHave.setPassword("test");
		whatWeHave.setRole("EMPLOYEE");
		whatWeHave.setEmail("a@a.com");
		update.addUser(whatWeHave);

		DecodedToken whoseLoggedIn = new DecodedToken("test_username", Role.ADMIN, "a@a.com");

		when(converter.convert(token)).thenReturn(whoseLoggedIn);

		mockMvc.perform(post("http://localhost:8080/admin/add-user")
			.header("token", token)
			.content(objectMapper.writeValueAsString(adminIn))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.information", is("please log in")));
	}
}