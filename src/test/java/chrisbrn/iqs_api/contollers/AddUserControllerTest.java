package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.converters.DecodedTokenFromHeaderConverter;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.models.in.UserIn;
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
class AddUserControllerTest {

	@Autowired private ObjectMapper objectMapper;
	@Autowired private MockMvc mockMvc;
	@Autowired private Jdbi jdbi;

	@MockBean private DecodedTokenFromHeaderConverter converter;
	@MockBean private DatabaseUpdate dbUpdate;
	@MockBean private DatabaseQuery dbQuery;

	private String token = "mocked";

	private UserIn adminIn = new UserIn();
	private UserIn employeeIn = new UserIn();
	private UserIn candidateIn = new UserIn();

	private DecodedToken adminDB;
	private DecodedToken employeeDB;
	private DecodedToken candidateDB;

	@BeforeAll
	public void beforeAll() {

		// Request Bodies
		this.candidateIn.setUsername("testCandidateIn");
		this.candidateIn.setPassword("test_P@ssw0rd");
		this.candidateIn.setRole(Role.CANDIDATE.name());

		this.employeeIn.setUsername("testEmployeeIn");
		this.employeeIn.setPassword("test_P@ssw0rd");
		this.employeeIn.setRole(Role.EMPLOYEE.name());

		this.adminIn.setUsername("testAdminIn");
		this.adminIn.setPassword("test_P@ssw0rd");
		this.adminIn.setRole(Role.ADMIN.name());

		// Tokens
		this.adminDB = new DecodedToken("testAdminDB", Role.ADMIN, "a@a.com");
		this.employeeDB = new DecodedToken("testEmployeeDB", Role.EMPLOYEE, "a@a.com");
		this.candidateDB = new DecodedToken("testCandidateDB", Role.CANDIDATE, "a@a.com");
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

		when(converter.convert(token)).thenReturn(adminDB);
		when(dbQuery.storedRoleMatchesTokenRole(adminDB)).thenReturn(true);
		when(dbUpdate.addUser(Mockito.any())).thenReturn(true);

		performSuccessfulAddUser(candidateIn);
		performSuccessfulAddUser(employeeIn);
		performSuccessfulAddUser(adminIn);
	}

	@Test
	void loggedInEmployeeCanOnlyAddACandidate() throws Exception {

		when(converter.convert(token)).thenReturn(employeeDB);
		when(dbQuery.storedRoleMatchesTokenRole(employeeDB)).thenReturn(true);
		when(dbUpdate.addUser(Mockito.any())).thenReturn(true);

		performSuccessfulAddUser(candidateIn);
		performUnSuccessfulAddUser_LacksPermission(employeeIn);
		performUnSuccessfulAddUser_LacksPermission(adminIn);
	}

	@Test
	void loggedInCandidateCanNotAddAUser() throws Exception {

		when(converter.convert(token)).thenReturn(candidateDB);
		when(dbQuery.storedRoleMatchesTokenRole(candidateDB)).thenReturn(true);
		when(dbUpdate.addUser(Mockito.any())).thenReturn(true);

		performUnSuccessfulAddUser_LacksPermission(candidateIn);
		performUnSuccessfulAddUser_LacksPermission(employeeIn);
		performUnSuccessfulAddUser_LacksPermission(adminIn);
	}

	@Test
	void addingDuplicateUserReturns_BadStatusAndMessage() throws Exception {

		when(converter.convert(token)).thenReturn(adminDB);
		when(dbUpdate.addUser(Mockito.any())).thenReturn(false);

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

		when(converter.convert(token)).thenReturn(adminDB);
		when(dbQuery.storedRoleMatchesTokenRole(adminDB)).thenReturn(false);

		mockMvc.perform(post("http://localhost:8080/admin/add-user")
			.header("token", token)
			.content(objectMapper.writeValueAsString(adminIn))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.information", is("please log in")));

	}
}