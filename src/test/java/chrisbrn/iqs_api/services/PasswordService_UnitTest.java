package chrisbrn.iqs_api.services;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordService_UnitTest {

	final PasswordService pw = new PasswordService();

	@Test
	void producesPasswordThatCanBeMatched() {
		String password = pw.hash("test");
		assertTrue(pw.passwordMatches("test", password));
	}

	@Test
	void producesReturnsFalseWhenPasswordDoesNotMatch() {
		String password = pw.hash("not_test");
		assertFalse(pw.passwordMatches("test", password));
	}

	private List<String> generateAnArrayOfRandomStringsOfLength() {

		List<String> generated = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			generated.add(pw.generate(12));
		}

		return generated;
	}

	@Test
	void generatesARandomEnoughStringForOurSigner() {
		for (int i = 0; i < 1000; i++) {
			List<String> generated = generateAnArrayOfRandomStringsOfLength();
			Set<String> set = new HashSet<>(generated);
			assertFalse(set.size() < generated.size());
		}
	}
}