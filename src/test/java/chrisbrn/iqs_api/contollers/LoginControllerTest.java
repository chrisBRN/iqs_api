package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.config.IntegrationTestConfig;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ActiveProfiles("testDataSource")
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Jdbi jdbi;

//	@Test
//	void failsBeforeDBIfPasswordDoesNotMeetPatternRequirements() throws Exception {
//
//		mockMvc.perform(post("http://localhost:8080/login")
//			.param("username","username")
//			.param("password","password"))
//			.andExpect(status().isBadRequest())
////			.andExpect(content().json("{'Error message':'Username Does Not Match Requirements'}"));
//			.andExpect(jsonPath("$.Error").value("Password Does Not Match Requirements"));
//	}
//"message\":\"Username Does Not Match Requirements\"
//	"test_username",
//			"test_P@ssw0rd"

	@Test
	void successfulLoginReceivesToken() {


	}

	@Test
	void unsuccessfulLoginReceivesForbidden() {


	}

//	ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials") :
//			ResponseEntity.status(HttpStatus.OK).body(tkService.generateToken(user.get()));
}